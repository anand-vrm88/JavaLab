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

	<div>
		<form action="gameservlet">
			<table align="center">
				<tr>
					<td>Player Name</td>
					<td><input id="playerName" name="playerName" type="text"></td>
				</tr>
				<tr>
					<td colspan="2" align="right">
						<input id="requestType" name="requestType" type="hidden" value="playerinit">
						<input type="button" value="Reset">
						<input type="submit">
					</td>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>