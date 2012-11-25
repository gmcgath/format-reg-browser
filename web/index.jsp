<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Format Registry Search</title>
</head>
<body>
<h1>Format Registry Search</h1>
<form name="Search" action="search" method="get">
	Name: <input type="text" name="name"><br>
	Extension: <input type="text" name="ext"><br>
	MIME type: <input type="text" name="mime"><br>
	Creator: <input type="text" name="creator"><br>
	<input type="submit" name="Search">
</form>
</body>
</html>