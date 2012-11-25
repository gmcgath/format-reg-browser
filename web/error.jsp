<%@ page language="java" contentType="text/html; charset=UTF-8"
    isErrorPage="true"
    session="true"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Oops</title>
<link rel="stylesheet" href="css/styles.css" type="text/css">	
</head>
<body>
<h1>A problem has happened</h1>
<%
if (exception != null) {
    out.print (exception.toString());
}
Object errorInfo = request.getAttribute("errorInfo");
if (errorInfo != null) {
    if (errorInfo instanceof StackTraceElement[]) {
        StackTraceElement[] stackDump = (StackTraceElement[]) errorInfo;
        for (StackTraceElement elem : stackDump) {
            out.print (elem.toString() + "<br>");
        }
    }
    else {
	    out.print ("<br>&nbsp;<br>" + errorInfo.toString());
    }
}
%>

<%@include file="footer.html" %>

</body>
</html>