<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8"  %>
<html>
<head>
    <title>New option</title>
    <link rel="stylesheet" type="text/css" href="../../resources/css/materialize.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/bootstrap.css">
</head>
<body>
<jsp:include page="admin_header.jsp"/>
<div class="container" style="margin-top: 65px">
    <div class="card">
        <div class="card-body" style="margin: 1%">
            <h5 class="card-title">New option</h5>
            <spring:form method="post" modelAttribute="optionDTO" action="new_option">
                <div class="form-group">
                    <label>Option's name:</label>
                    <spring:input path="name"/> <br/>
                </div>
                <div class="form-group">
                    <label>Cost: </label>
                    <spring:input path="cost" placeholder="0"/> <br/>
                </div>
                <div class="form-group">
                    <label>Access cost:</label>
                    <spring:input path="accessCost" placeholder="0"/> <br/>
                </div>

                <spring:button class="btn btn-primare">Add option</spring:button>

            </spring:form>
        </div>

    </div>

</div>

</body>
</html>