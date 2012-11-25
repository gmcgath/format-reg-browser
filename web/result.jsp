<%@ page language="java" contentType="text/html; charset=UTF-8"
    errorPage="error.jsp"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/tags/Result.tld" prefix="res" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search results</title>
</head>
<body>

<h1>Search results</h1>
<div>
<h2>Results for PRONOM</h2>
<res:oneresult repository="PRONOM"> 
<%= (pageContext.getAttribute("representation") != null) ?
        pageContext.getAttribute("representation") + "<br>&nbsp;<br>\n" :
        "" %>
</res:oneresult>
</div>

<div>
<h2>Results for UDFR</h2>
<res:oneresult repository="UDFR"> 
<%= (pageContext.getAttribute("representation") != null) ?
        pageContext.getAttribute("representation") + "<br>&nbsp;<br>\n" :
        "" %>
</res:oneresult>
</div>

<div>
<h2>Results for DBPedia</h2>
<res:oneresult repository="DBPEDIA"> 
<%= (pageContext.getAttribute("representation") != null) ?
        pageContext.getAttribute("representation") + "<br>&nbsp;<br>\n" :
        "" %>
</res:oneresult>
</div>

<p>
<b><a href="index.jsp">Search again</a></b>
</p>

</body>
</html>