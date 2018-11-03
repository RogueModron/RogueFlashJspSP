<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
	<c:set var="PageTitle" value="Decks" />
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
				<template id="itemTemplate">
					<div class="itemContainerWrapper">
						<input type="hidden" class="itemId" value="{deckId}" />
						<div class="floatLeft itemContainer roundedAll">
							<div class="floatLeft itemTopSection roundedAll">
								<div class="floatLeft itemTitleContainerShort roundedLeft">
									<span class="floatLeft inline itemTitle roundedLeft">
										{description}
									</span>
								</div>
								<div class="floatLeft itemBadgeContainer roundedRight">
									<span class="itemBadge roundedRight">{numberOfSides}</span>
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
			"<c:url value='/decks/search' />",
			{},
			"<c:url value='/review' />",
			function(
					templateHtml,
					item) {
				function formatDescriptionText(text) {
					if (text === "") {
						return "---";
					}
					return app.utils.encodeHtml(text);
				}
				return templateHtml
					.replace("{deckId}", item.deckId)
					.replace("{description}", formatDescriptionText(item.description))
					.replace("{notes}", app.utils.encodeHtmlWithFormat(item.notes))
					.replace("{numberOfSides}", item.numberOfSides);
			}
		);
		
		var appMenuOptions = {
			newDeckButton: true,
			newDeckUrl: "<c:url value='/deck/' />"
		};
		var appMenuComponent = new app.components.AppMenuComponent(appMenuOptions);
		
		function deleteDecks() {
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
				url: "<c:url value='/decks/' />" + ids.join(",")
			});
		}
		var actionMenuOptions = {
			deleteAction: deleteDecks
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