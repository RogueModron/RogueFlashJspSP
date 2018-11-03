<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
	<c:set var="PageTitle" value="Review" />
	<title>RogueFlash - <c:out value="${PageTitle}" /></title>
	
	<%@ include file="./fragments/headContent.jspf" %>
</head>
<body>

	<%@ include file="./fragments/noscript.jspf" %>
	<%@ include file="./fragments/menu.jspf" %>
	
	<div id="appScreen">
	
		<%@ include file="./fragments/pageHeader.jspf" %>
		
		<div class="pageBody"
			style="height: 666px; overflow: auto; display: none">
			<div class="centered filter margin-bottom-2x">
				<input type="hidden" id="deckId" value="<c:out value='${deckId}' />" />
				<input type="hidden" id="cardId" value="" />
				<input type="hidden" id="cardInstanceId" value="" />
				
				<div class="margin-bottom-2x">
					<label class="fieldLabel left">
						Tags
					</label>
					<div class="panel roundedAll">
						<span id="tags"></span>
					</div>
				</div>
				
				<div class="margin-bottom-2x">
					<label class="fieldLabel left">
						Side A
					</label>
					<div class="panel roundedAll">
						<span id="sideA"></span>
					</div>
					<label class="fieldLabel left">
						Side B
					</label>
					<div class="panel roundedAll">
						<span id="sideB"></span>
					</div>
				</div>
				
				<div id="notesContainer"
					class="margin-bottom-2x">
					<label class="fieldLabel left">
						Notes
					</label>
					<div class="panel roundedAll">
						<span id="notes"></span>
					</div>
				</div>
				
				<div>
					<button id="showAnswer"
						class="roundedAll">
						<span class="fas fa-gavel"></span>
						Show Answer
					</button>
				</div>
				<div id="valueButtons">
					<button id="value0"
						class="borderNoneRight roundedLeft valueButton">
						0
					</button><!--
				 --><button id="value1"
						class="borderNoneRight valueButton">
						1
					</button><!--
				 --><button id="value2"
						class="borderNoneRight valueButton">
						2
					</button><!--
				 --><button id="value3"
						class="borderNoneRight valueButton">
						3
					</button><!--
				 --><button id="value4"
						class="roundedRight valueButton">
						4
					</button>
				</div>
			</div>
		</div>
	</div>
	
	<%@ include file="./fragments/scripts.jspf" %>
	
<script type="text/javascript">
	$(document).ready(function(){
		var appMenuOptions = {
			newDeckButton: true,
			newDeckUrl: "<c:url value='/deck/' />",
			editDeckButton: true,
			editDeckUrl: "<c:url value='/deck' />",
			decksButton: true,
			decksUrl: "<c:url value='/decks' />",
			newCardButton: true,
			newCardUrl: "<c:url value='/card' />",
			cardsButton: true,
			cardsUrl: "<c:url value='/cards' />"
		};
		var appMenuComponent = new app.components.AppMenuComponent(appMenuOptions);
		appMenuComponent.setDeckId($("#deckId").val());
		
		function deleteCard() {
			$.ajax({
				//dataType: "json",
				error: function(data) {
					//
				},
				method: "DELETE",
				success: function(data) {
					document.location = "<c:url value='/cards' />";
				},
				url: "<c:url value='/card/' />" + $("#cardId").val()
			});
		}
		var actionMenuOptions = {
			deleteAction: deleteCard
		};
		var actionMenuComponent = new app.components.ActionMenuComponent(actionMenuOptions);
		actionMenuComponent.setActionMenu(true);
		
		function getCardToReview() {
			$.ajax({
				data: {},
				//dataType: "json",
				error: function(data) {
					//
				},
				method: "GET",
				success: function(data) {
					if (typeof(data) !== "object"
							|| (typeof(data.cardId) === "undefined")) {
						document.location = "<c:url value='/decks' />";
						return;
					}
					
					$("#cardId").val(data.cardId);
					$("#cardInstanceId").val(data.cardInstanceId);
					
					var jTags = $("#tags");
					jTags.html(app.utils.encodeHtml(data.tags));
					
					var jSideA = $("#sideA");
					var jSideB = $("#sideB");
					if (data.sideAToB) {
						jSideA.html(app.utils.encodeHtmlWithFormat(data.sideA));
						jSideB.html(app.utils.encodeHtmlWithFormat(data.sideB));
					} else {
						jSideA.html(app.utils.encodeHtmlWithFormat(data.sideB));
						jSideB.html(app.utils.encodeHtmlWithFormat(data.sideA));
					}
					jSideB.css("visibility", "hidden");
					
					var jNotesContainer = $("#notesContainer");
					var jNotes = $("#notes");
					if (typeof(data.notes) === "string"
							&& data.notes !== "") {
						jNotes.html(app.utils.encodeHtmlWithFormat(data.notes));
						jNotes.css("visibility", "hidden");
						jNotesContainer.show();
					} else {
						jNotesContainer.hide();
					}
					
					$("#showAnswer").css("visibility", "visible");
					$("#valueButtons").css("visibility", "hidden");
				},
				url: "<c:url value='/review/next/' />" + $("#deckId").val()
			});
		}
		getCardToReview();
		
		$("#showAnswer").click(function() {
			var jSideB = $("#sideB");
			jSideB.css("visibility", "visible");
			var jNotes = $("#notes");
			jNotes.css("visibility", "visible");
			
			$("#showAnswer").css("visibility", "hidden");
			$("#valueButtons").css("visibility", "visible");
		});
		
		function saveReview(value) {
			$.ajax({
				data: {
					cardInstanceId: $("#cardInstanceId").val(),
					value: value 
				},
				//dataType: "json",
				error: function(data) {
					//
				},
				method: "POST",
				success: function(data) {
					getCardToReview();
				},
				url: "<c:url value='/review' />"
			});
		}
		
		$("button[id^='value']").click(function() {
			var value = $(this).attr("id").replace("value", "");
			saveReview(value);
		});
		
		$(".pageBody").show();
		function resizePageBody() {
			var jPageBody = $(".pageBody");
			
			var windowH = $(window).height();
			var pageBodyH = jPageBody.offset().top
			
			var newPageBodyH = windowH - pageBodyH - 70;
			if (newPageBodyH < 200) {
				newPageBodyH = 200;
			};
			jPageBody.height(newPageBodyH);
		}
		resizePageBody();
		$(window).resize(resizePageBody);
	});
</script>
</body>
</html>