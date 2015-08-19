
<table align="center">
	<%
			String errorMessage = (String) session.getAttribute("errorMessage");
			session.removeAttribute("errorMessage");
			if (errorMessage != null && !"".equals(errorMessage)){
		%>
	<tr style="color: red">
		<td>Error:</td>
		<td><%=errorMessage %></td>
	</tr>
	<%
			}
		%>
</table>