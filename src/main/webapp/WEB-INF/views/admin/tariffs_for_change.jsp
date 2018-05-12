<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="../../resources/css/materialize.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/bootstrap.css">
</head>
<body>
    <jsp:include page="admin_header.jsp"/>
    <div class="container" style="margin-top: 65px">
        <div class="jumbotron">
            <h5>Tariffs for change</h5>
            <c:forEach var="i" begin="0" step="3" end="${tariffs.size()}">
                <div class="card-deck">
                    <c:forEach var="j" begin="0" end="2">
                        <c:if test="${i + j < tariffs.size()}">
                            <div class="card" style="width: 18rem;">
                                <div class="card-header">${tariffs.get(i + j).name}</div>
                                <div class="card-body">
                                    <p class="card-text">Cost: ${tariffs.get(i + j).cost}</p>
                                    <p class="card-text">Some description</p>
                                    <form action="/admin/change_tariff" method="POST">
                                        <input type="hidden" name="contractId" value="${contractId}">
                                        <input type="hidden" name="tariffId" value="${tariffs.get(i + j).id}">
                                        <button type="submit" class="btn btn-primary">Activate</button>
                                    </form>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
            </c:forEach>

        </div>
    </div>
</body>
</html>
