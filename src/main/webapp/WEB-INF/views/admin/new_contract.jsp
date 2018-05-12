<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>New contract page</title>
    <link rel="stylesheet" type="text/css" href="../../../resources/css/materialize.css">
    <link rel="stylesheet" type="text/css" href="../../../resources/css/bootstrap.css">
</head>
<body>
<jsp:include page="admin_header.jsp"/>
<div class="container">
    <div>
        <div class="card" style="margin: 10%">
            <h5 class="card-header">New contract</h5>
            <div class="card-body" style="margin: 1%">

                <spring:form method="post" modelAttribute="contractDTO" action="new_contract">

                    <div class="form-group">
                        <label>Client's passport:</label>
                        <spring:input path="ownersPassport"/> <br>
                        <spring:errors path="ownersPassport" cssClass="error"/> <br>
                    </div>

                    <div class="form-group">
                        <label>Number: </label>
                        <spring:input path="number"/> <br>
                        <spring:errors path="number" cssClass="error"/> <br>
                    </div>

                    <div class="form-group">
                        <label>Tariff:</label><br>
                        <spring:select class="form-control" path="tariffDTO.id">
                            <spring:options items="${tariffs}" itemValue="id" itemLabel="name"/>
                        </spring:select> <br/>
                    </div>

                    <spring:button>Add contract</spring:button>

                </spring:form>
            </div>
        </div>
    </div>

</div>

</body>
</html>
