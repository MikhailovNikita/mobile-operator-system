<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../../../resources/css/materialize.css">
    <link rel="stylesheet" type="text/css" href="../../../resources/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="../../../resources/css/bootstrap-grid.css">
    <link rel="stylesheet" type="text/css" href="../../resources/scss/_icons.scss">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <title>Registration page</title>

</head>
<body>
<div class="container">
    <div class="card" style="width: 500px; height: 700px; margin:0 auto">
        <h5 class="card-header">New client</h5>
        <div class="card-body" style="margin: 10%">
            <spring:form method="post" modelAttribute="userDTO" action="new_client">
                <div class="form-group">
                    <label>First name</label>
                    <spring:input placeholder="Ivan" path="name"/>
                    <spring:errors path="name" cssClass="error"/>
                </div>

                <div class="form-group">
                    <label>Last name:</label>
                    <spring:input placeholder="Ivanov" path="lastName"/>
                    <spring:errors path="lastName" cssClass="error"/>
                </div>
                <div class="form-group">
                    <label>Birth Date:</label>
                    <spring:input placeholder="20/01/2018" path="birthDate"/>
                    <spring:errors path="birthDate" cssClass="error"/>
                </div>


                <div class="form-group">
                    <label>Email</label>
                    <spring:input placeholder="client@example.com" path="email"/>
                    <spring:errors path="email" cssClass="error"/>
                </div>

                <div class="form-group">
                    <label>Passport</label>
                    <spring:input placeholder="1234567890" path="passport"/>
                    <spring:errors path="passport" cssClass="error"/>
                </div>

                <div class="form-group">
                    <label> Address: </label>
                    <spring:input placeholder="city" path="address"/>
                    <spring:errors path="address" cssClass="error"/>
                </div>

                <div class="form-group">
                    <spring:button class="btn btn-dark">Register</spring:button>
                </div>
            </spring:form>
        </div>

    </div>

</div>
</body>
</html>
