<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

</head>
<body>

<form method="POST" action="forbid_options">
    <select name="firstId">
        <c:forEach var="item" items="${optionsList}">
            <option value="${item.id}">${item.name}</option>
        </c:forEach>
    </select>
    <select name="secondId">
        <c:forEach var="item" items="${optionsList}">
            <option value="${item.id}">${item.name}</option>
        </c:forEach>
    </select>
    <%--<input id="optionOne" type="text" name="firstId" placeholder="First option"><br/>--%>
    <%--<input id="optionTwo" type="text" name="secondId" placeholder="Second option"><br/>--%>
    <input type="submit" value="Forbid options"/>
    <p>${resultMessage}</p>
</form>



</body>
</html>
