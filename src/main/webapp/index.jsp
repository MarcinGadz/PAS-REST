<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:ui="http://java.sun.com/jsf/facelets"
      xmlns:h="http://java.sun.com/jsf/html">
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
<p>gówno</p>
<p><h:outputLink value="test.xhtml">${msg["test.xhtml"]}</h:outputLink> </p>
<ul>
<li><h:outputLink value="test.xhtml" >test.xhtml</h:outputLink></li>
</ul>
</body>
</html>