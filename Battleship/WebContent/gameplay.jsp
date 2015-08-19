<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/gameplay.js">
	
</script>
</head>
<body onload="loadJsonData();">

	<div id="output" align="center"></div>
	<div id="helloName">Hi
	<%=session.getAttribute("playerName")%></div>
	<%
		if ("end".equals(session.getAttribute("gameStatus"))) {
	%>
			You have won.
	<%
		}else{
	%>
	<form id="servletgame" action="GET">
		<table>
			<tr>
				<td>Enter Enemy Name:</td>
				<td colspan="2"><input id="enemyName" name="enemyName"
					type="text" value="enemy name"></td>
			</tr>
			<tr>
				<td>Enter Coordinates[x y]:</td>
				<td><input id="targetX" name="targetX" type="text"></td>
				<td><input id="targetY" name="targetY" type="text"></td>
			</tr>
			<tr>
				<td><input id="playerName" name="playerName" type="hidden" value=<%=session.getAttribute("playerName")%>></td>
				<td colspan="2"><input type="button" value="submit" onclick="updateGameImage();"></td>
			</tr>
		</table>
	</form>
	<% } %>
</body>
</html>