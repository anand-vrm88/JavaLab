<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Player Initialisation</title>
</head>
<body>
	<%@include file="WEB-INF/header.jsp"%>

	<%
		String playerName = (String)session.getAttribute("playerName");
		if (playerName != null && !"".equals(playerName))
	%>
	<div>
		Hello!
		<%=playerName%>
	</div>

	<div>
		<form action="gameservlet" method="post">
			<table align="center">
				<tr>
					<td>Enter BattleShip Coordinate(x1 y1 x2 y2)</td>
					<td>
						<input id="startX" name="startX" type="text" size="4"> 
						<input id="startY" name="startY" type="text" size="4"> 
						<input id="endX" name="endX" type="text" size="4"> 
						<input id="endY" name="endY" type="text" size="4">
					</td>
				</tr>
				<tr>
					<td colspan="2" align="right">
						<input type="hidden" name="requestType" value="battleshipinit">
						<input type="button" value="Reset">
						<input type="submit">
					</td>
				</tr>
			</table>
		</form>
	</div>

	<%@include file="/WEB-INF/errorMessage.jsp"%>
</body>
</html>