<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BattleShip Game</title>
</head>
<body>
	<%session.setAttribute("gameStatus","running"); %>
	<%@include file="WEB-INF/header.jsp"%>
	<div>
		<form action="gameservlet" method="post">
			<table align="center">
				<tr>
					<td>Number of Players</td>
					<td><input id="playersCount" name="playersCount" type="text"  value="2"></td>
					<td rowspan="5"><img alt="game screen shot" src="icons/game.jpg" style="float: right" height="145px"></td>
				</tr>
				<tr>
					<td>Number of Bots</td>
					<td><input id="botsCount" name="botsCount" type="text"  value="1"></td>
				</tr>
				<tr>
					<td>PlatForm Size</td>
					<td><input id="platFormSize" name="platFormSize" type="text"></td>
				</tr>
				<tr>
					<td>Number of BattleShips</td>
					<td><input id="battleShipsCount" name="battleShipsCount" type="text"></td>
				</tr>
				<tr>
					<td colspan="2" align="right">
						<input id="requestType" name="requestType" type="hidden" value="gameinit">
						<input type="button" value="Reset">
						<input type="submit">
					</td>
				</tr>

			</table>
		</form>
	</div>

	<%@include file="/WEB-INF/errorMessage.jsp" %>
</body>
</html>