<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
	<title>RogueFlash - Error</title>
	
	<%@ include file="./fragments/headContent.jspf" %>
</head>
<body>
	<div id="appScreen">
		<div class="pageHeader">
			<span class="pageTitle">
				RogueFlash - Error
			</span>
		</div>
		
		<div class="pageBody">
			<div class="centered filter margin-bottom-2x">
				<p style="transform: translate(50px) rotate(-15deg);"> 
					Error! :P
				</p>
				<p style="transform: translate(-50px) rotate(10deg);">
					<span class="fas fa-ambulance"></span>
					<a href="<c:url value='/' />"
						class="navigatorButton roundedAll" >
						Start Again!
					</a>
					<span class="fas fa-ambulance"></span>
				</p>
			</div>
		</div>
	</div>
</body>

</html>