<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
	<c:set var="PageTitle" value="Cards" />
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
				<label class="filterLabel"
					for="filterText">
					Filter
				</label>
				<div class="inline">
					<input id="filterText"
						type="text"
						class="roundedLeft"
						placeholder="Filter" /><!--
				 --><button id="eraseFilter"
						class="borderNoneLeft">
						<span class="fas fa-times"></span>
					</button><!--
				 --><button id="executeFilter"
						class="borderNoneLeft roundedRight">
						<span class="fas fa-search"></span>
					</button>
				</div>
			</div>
			
			<div id="itemsList"
				style="height: 666px; overflow: auto; display: none">
				<input type="hidden" id="deckId" value="<c:out value='${deckId}' />" />
				<template id="itemTemplate">
					<div class="itemContainerWrapper">
						<input type="hidden" class="itemId" value="{cardId}" />
						<div class="floatLeft itemContainer roundedAll">
							<div class="itemTopSection roundedTop">
								<div class="itemTitleContainer roundedTop">
									<span class="inline itemTitle roundedTop">
										{sideA}
									</span>
								</div>
							</div>
							<div class="itemTopSection roundedBottom">
								<div class="itemTitleContainer roundedBottom">
									<span class="inline itemTitle roundedBottom">
										{sideB}
									</span>
								</div>
							</div>
							<div class="floatLeft itemBottomSection roundedAll">
								<div class="floatLeft itemSelectorContainer">
									<button class="itemSelector roundedAll">
										<span class="fa-stack">
											<i class="far fa-circle fa-stack-2x"></i>
											<i class="fas fa-check fa-stack-1x"></i>
										</span>
									</button>
								</div>
								<div class="floatLeft itemNotesContainer left">
									<span class="itemNotes">
										{notes}
									</span>
								</div>
								<div class="floatLeft">
									<span class="">
										{tags}
									</span>
								</div>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
				</template>
				
				<div class="centered">
					<button id="loadItems"
						class="roundedAll">
						<span class="fas fa-paw"></span>
						Load More
					</button>
				</div>
			</div>
		</div>
	</div>
	
	<%@ include file="./fragments/scripts.jspf" %>
	
<script type="text/javascript">
	$(document).ready(function() {
		var filterComponent = new app.components.FilterComponent(
			"<c:url value='/cards/search/' />" + $("#deckId").val(),
			{},
			"<c:url value='/card/' />" + $("#deckId").val(),
			function(
					templateHtml,
					item) {
				function formatSideText(text) {
					var parts = text.split("\n")
					var textToShow = parts[0];
					if (textToShow === "") {
						return "---";
					}
					return app.utils.encodeHtmlWithFormat(textToShow);
				}
				return templateHtml
					.replace("{cardId}", item.cardId)
					.replace("{sideA}", formatSideText(item.sideA))
					.replace("{sideB}", formatSideText(item.sideB))
					.replace("{notes}", app.utils.encodeHtmlWithFormat(item.notes))
					.replace("{tags}", app.utils.encodeHtml(item.tags));
			}
		);
		
		var appMenuOptions = {
			newDeckButton: true,
			newDeckUrl: "<c:url value='/deck/' />",
			editDeckButton: true,
			editDeckUrl: "<c:url value='/deck' />",
			decksButton: true,
			decksUrl: "<c:url value='/decks' />",
			newCardButton: true,
			newCardUrl: "<c:url value='/card' />",
			reviewButton: true,
			reviewUrl: "<c:url value='/review' />",
		};
		var appMenuComponent = new app.components.AppMenuComponent(appMenuOptions);
		appMenuComponent.setDeckId($("#deckId").val());
		
		function deleteCards() {
			var ids = filterComponent.getSelectedItemsId();
			if (ids.length === 0) {
				return;
			}
			$.ajax({
				//dataType: "json",
				error: function(data) {
					//
				},
				method: "DELETE",
				success: function(data) {
					filterComponent.deleteItems(ids);
					actionMenuComponent.setActionMenu(false);
				},
				url: "<c:url value='/cards/' />" + ids.join(",")
			});
		}
		var actionMenuOptions = {
			deleteAction: deleteCards
		};
		var actionMenuComponent = new app.components.ActionMenuComponent(actionMenuOptions);
		actionMenuComponent.setActionMenu(false);
		
		$(filterComponent).on("selector", function(e, data) {
			actionMenuComponent.setActionMenu(data.selected);
		});
		
		$("#executeFilter").click();
		
		$("#itemsList").show();
		function resizeItemsList() {
			var jItemsList = $("#itemsList");
			
			var windowH = $(window).height();
			var itemsListH = jItemsList.offset().top
			
			var newItemsListH = windowH - itemsListH - 70;
			if (newItemsListH < 200) {
				newItemsListH = 200;
			};
			jItemsList.height(newItemsListH);
		}
		resizeItemsList();
		$(window).resize(resizeItemsList);
	});
</script>
</body>
</html>