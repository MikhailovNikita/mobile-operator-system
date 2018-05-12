<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form method="POST" action="required_options">
    <select name="firstId">
        <c:forEach var="item" items="${optionsList}">
            <option value="${item.id}" label="Option">${item.name}</option>
        </c:forEach>
    </select>
    <select name="secondId">
        <c:forEach var="item" items="${optionsList}">
            <option value="${item.id}" label="Required option">${item.name}</option>
        </c:forEach>
    </select>
    <input type="submit" value="Make option required"/>
    <p>${resultMessage}</p>
</form>

</body>
</html>
