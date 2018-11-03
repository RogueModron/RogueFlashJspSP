<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
	<c:set var="PageTitle" value="Card" />
	<title>RogueFlash - <c:out value="${PageTitle}" /></title>
	
	<%@ include file="./fragments/headContent.jspf" %>
</head>
<body>

	<%@ include file="./fragments/noscript.jspf" %>
	<%@ include file="./fragments/menu.jspf" %>
	
	<div id="appScreen">
	
		<%@ include file="./fragments/pageHeader.jspf" %>
		
		<div class="pageBody">
			<div class="centered filter margin-bottom-2x">
				<input type="hidden" id="deckId" value="<c:out value='${PageBean.deckId}' />" />
				<input type="hidden" id="cardId" value="<c:out value='${PageBean.cardId}' />" />
				
				<label class="fieldLabel left"
					for="sideA">
					Side A
				</label>
				<textarea id="sideA"
					class="field roundedAll"
					placeholder="Side A"
					rows="5" ><%--
				--%><c:out value="${PageBean.sideA}" /><%--
				--%></textarea>
				
				<label class="fieldLabel left"
					for="sideB">
					Side B
				</label>
				<textarea id="sideB"
					class="field roundedAll"
					placeholder="Side B"
					rows="5" ><%--
				--%><c:out value="${PageBean.sideB}" /><%--
				--%></textarea>
				
				<label class="fieldLabel left"
					for="notes">
					Notes
				</label>
				<textarea id="notes"
					class="field roundedAll"
					placeholder="Notes"
					rows="5" ><%--
				--%><c:out value="${PageBean.notes}" /><%--
				--%></textarea>
				
				<label class="fieldLabel left"
					for="tags">
					Tags
				</label>
				<input id="tags"
					type="text"
					class="field roundedAll"
					placeholder="Tags"
					value="<c:out value='${PageBean.tags}' />" />
					
				<label class="fieldLabel left"
					for="sideBToA">
					Side B to A
				</label>
				<input id="sideBToA"
					type="checkbox"
					class="fieldCheckBox"
					value="true"
					<c:if test="${PageBean.sideBToA}" >checked</c:if> />
			</div>
		</div>
	</div>
	
	<%@ include file="./fragments/scripts.jspf" %>
	
<script type="text/javascript">
	$(document).ready(function(){
		function updateCard() {
			$.ajax({
				data: {
					deckId: $("#deckId").val(),
					cardId: $("#cardId").val(),
					sideA: $("#sideA").val(),
					sideB: $("#sideB").val(),
					notes: $("#notes").val(),
					tags: $("#tags").val(),
					sideBToA: $("#sideBToA").is(":checked")
				},
				//dataType: "json",
				error: function(data) {
					//
				},
				method: "POST",
				success: function(data) {
					//
				},
				url: "<c:url value='/card' />"
			});
		}
		app.utils.setValueObserver(
			$("#sideA"),
			updateCard
		);
		app.utils.setValueObserver(
			$("#sideB"),
			updateCard
		);
		app.utils.setValueObserver(
			$("#notes"),
			updateCard
		);
		app.utils.setValueObserver(
			$("#tags"),
			updateCard
		);
		$("#sideBToA").click(updateCard);
		
		var appMenuOptions = {
			newDeckButton: true,
			newDeckUrl: "<c:url value='/deck/' />",
			decksButton: true,
			decksUrl: "<c:url value='/decks' />",
			newCardButton: true,
			newCardUrl: "<c:url value='/card' />",
			cardsButton: true,
			cardsUrl: "<c:url value='/cards' />",
			reviewButton: true,
			reviewUrl: "<c:url value='/review' />"
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
					document.location = "<c:url value='/cards/' />" + $("#deckId").val();
				},
				url: "<c:url value='/card/' />" + $("#cardId").val()
			});
		}
		var actionMenuOptions = {
			deleteAction: deleteCard
		};
		var actionMenuComponent = new app.components.ActionMenuComponent(actionMenuOptions);
		actionMenuComponent.setActionMenu(true);
	});
</script>
</body>
</html>