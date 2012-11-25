<%@ page language="java" contentType="text/html; charset=UTF-8"
    session="true"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/styles.css" type="text/css">	
<title>Format Registry Search</title>
</head>
<body>
<h1>Format Registry Search</h1>
<% 
String msg = (String) request.getAttribute("topmessage");
if (msg != null) {
    out.println("<p class=\"topmessage\">");
    out.println(msg);
    out.println("</p>");
}

if (session.getAttribute("maxresp") == null) {
    session.setAttribute("maxresp", 20);
}
String maxresp = session.getAttribute("maxresp").toString();
%>
<form class="searchform" name="Search" action="search" method="get">
	<label>Name:</label> <input type="text" name="name">
	<label>Extension:</label> <input type="text" name="ext"><br>
	<label>MIME type:</label> <input type="text" name="mime"><br>
	<label>Creator:</label> <input type="text" name="creator"><br>
	<label>Maximum responses per repository:</label> 
	<input type="text" name="maxresp" value="<%=maxresp%>"><br>
    &nbsp;<br>
	<input  type="submit" name="Search">
</form>
<%@include file="footer.html" %>
</body>
</html>
