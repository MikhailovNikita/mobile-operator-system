<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form method="POST" action="/admin/delete_tariff">
        <select name="tariffId">
            <c:forEach var="item" items="${tariffList}">
                <supportedOptions value="${item.id}">${item.name}</supportedOptions>
                <p>${item.cost}</p>
            </c:forEach>
        </select>
        <input type="submit" value="Delete tariff"/>
        <p>${resultMessage}</p>
    </form>
</body>
</html>
