<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New tariff page</title>
</head>
<body>
<div class="container">
            <spring:form method="post" modelAttribute="tariffDTO" action="new_tariff">
                <div class="form-group">
                    <label>Name: </label>
                    <spring:input path="name"/>
                    <spring:errors path="name" cssClass="error"/><br/>
                </div>

                <div class="form-group">
                    <label>Cost: </label>
                    <spring:input path="cost"/>
                    <spring:errors path="cost" cssClass="error"/> <br/>
                </div>

                <label>Options: </label><br>
                <spring:checkboxes path="optionIds" items="${optionDTOList}" itemLabel="name" itemValue="id"/>

                <spring:button>Add</spring:button>

            </spring:form>
</div>

</body>
</html>
