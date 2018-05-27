<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tariffs</title>
</head>
<body>
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
