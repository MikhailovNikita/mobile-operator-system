<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8"  %>
<html>
<head>
    <title>Your contracts</title>
    <link rel="stylesheet" type="text/css" href="../../resources/css/materialize.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/bootstrap.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        function showContract(id) {
            var form = document.createElement('form');
            document.body.appendChild(form);
            form.method = 'post';
            form.action = '/client/show_contract';
            var input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'contractId';
            input.value = id;
            form.appendChild(input);

            form.submit();
        }
    </script>
</head>
<body>
<jsp:include page="client_header.jsp"/>

<div class="container" style="margin-top: 65px">

    <table class="table table-hover" id="contractsTable">
        <thead class="thead-light">
        <tr>
            <th scope="col">#</th>
            <th scope="col">Number</th>
            <th scope="col">Tariff</th>
            <th scope="col">Cost</th>
            <th scope="col">Status</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${contractsList}" var="contract" varStatus="loop">
            <tr onclick="showContract('${contract.contractId}')"  style="cursor: pointer;">
                <th scope="col">${loop.index + 1}</th>
                <td>${contract.number}</td>
                <td>${contract.tariffName}</td>
                <td>${contract.tariffDTO.cost}</td>
                <td>
                    <c:if test="${contract.blockedByUser && !contract.blockedByAdmin}">
                        <p style="color: red">Blocked by user</p>
                    </c:if>
                    <c:if test="${contract.blockedByAdmin}">
                        <p style="color: red">Blocked by operator</p>
                    </c:if>
                    <c:if test="${!contract.blockedByUser && !contract.blockedByAdmin}">
                        <p style="color: green">Active</p>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
