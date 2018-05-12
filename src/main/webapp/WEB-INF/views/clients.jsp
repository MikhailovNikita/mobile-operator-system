<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"  %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="../../resources/css/materialize.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/bootstrap.css">
</head>
<body>
<jsp:include page="admin/admin_header.jsp"/>
<div class="container" style="margin-top: 65px">
    <div class="jumbotron">
        <c:forEach items="${clientsList}" var="client" varStatus="loop">

            <table class="table table-hover">
                <tr class="table-primary">
                    <td colspan="4"><b>
                            ${client.name} ${client.lastName} </b></td>
                </tr>
                <c:forEach items="${client.contracts}" var="contract" varStatus="loop">
                    <tr>
                        <td>${contract.number}</td>
                        <td>${contract.tariffName}</td>
                        <td>${contract.conclusionDate}</td>
                        <td>${contract.blockedByAdmin || contract.blockedByUser}</td>
                    </tr>
                </c:forEach>
            </table>

        </c:forEach>
    </div>
</div>

</body>
</html>
