<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
	<c:set var="PageTitle" value="Deck" />
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
				
				<label class="fieldLabel left"
					for="description">
					Description
				</label>
				<input id="description"
					type="text"
					class="field roundedAll"
					placeholder="Description"
					value="<c:out value='${PageBean.description}' />" />
					
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
			</div>
		</div>
	</div>
	
	<%@ include file="./fragments/scripts.jspf" %>
	
<script type="text/javascript">
	$(document).ready(function(){
		function updateDeck() {
			$.ajax({
				data: {
					deckId: $("#deckId").val(),
					description: $("#description").val(),
					notes: $("#notes").val()
				},
				//dataType: "json",
				error: function(data) {
					//
				},
				method: "POST",
				success: function(data) {
					//
				},
				url: "<c:url value='/deck' />"
			});
		}
		app.utils.setValueObserver(
			$("#description"),
			updateDeck
		);
		app.utils.setValueObserver(
			$("#notes"),
			updateDeck
		);
		
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
		
		function deleteDeck() {
			$.ajax({
				//dataType: "json",
				error: function (data) {
					//
				},
				method: "DELETE",
				success: function (data) {
					document.location = "<c:url value='/decks' />";
				},
				url: "<c:url value='/deck/' />" + $("#deckId").val()
			});
		}
		var actionMenuOptions = {
			deleteAction: deleteDeck
		};
		var actionMenuComponent = new app.components.ActionMenuComponent(actionMenuOptions);
		actionMenuComponent.setActionMenu(true);
	});
</script>
</body>
</html>