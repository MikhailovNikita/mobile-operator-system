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
            <ptions value="${item.id}">${item.name}</ptions>
        </c:forEach>
    </select>
    <select name="secondId">
        <c:forEach var="item" items="${optionsList}">
            <options value="${item.id}">${item.name}</options>
        </c:forEach>
    </select>
    <%--<input id="optionOne" type="text" name="firstId" placeholder="First supportedOptions"><br/>--%>
    <%--<input id="optionTwo" type="text" name="secondId" placeholder="Second supportedOptions"><br/>--%>
    <input type="submit" value="Forbid options"/>

    <p>${param.error}</p>
</form>



</body>
</html>
