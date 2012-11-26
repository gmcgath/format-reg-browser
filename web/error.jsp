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
Object errorInfo = request.getAttribute("errorinfo");
if (errorInfo != null) {
    if (errorInfo instanceof Throwable) {
        Throwable e = (Throwable) errorInfo;
        out.println ("<b>" + e.getClass().getName() + "</b><br>&nbsp;<br>");
        String msg = e.getMessage ();
        if (msg != null) {
            out.println (msg + "<br>");
        }
        StackTraceElement[] dump = e.getStackTrace();;
        for (StackTraceElement elem : dump) {
            out.print (elem.toString() + "<br>");
        }
        
        Throwable cause = e.getCause();
        if (cause != null) {
            out.println ("<br>&nbsp;<br>Caused by:<br>");
            out.println ("<b>" + cause.getClass().getName() + "</b><br>&nbsp;<br>");
            msg = cause.getMessage ();
            if (msg != null) {
                out.println (msg + "<br>");
            }
            dump = cause.getStackTrace();;
            for (StackTraceElement elem : dump) {
                out.print (elem.toString() + "<br>");
            }
        }
    }
    else {
	    out.print ("<br>&nbsp;<br>" + errorInfo.toString());
    }
}
%>

<p><a href="index.jsp">Return to search form</a></p>

<%@include file="footer.html" %>

</body>
</html>