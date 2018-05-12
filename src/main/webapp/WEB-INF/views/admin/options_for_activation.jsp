<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="../../../resources/css/materialize.css">
    <link rel="stylesheet" type="text/css" href="../../../resources/css/bootstrap.css">
    <script>
        function activateOption(contractId, optionId) {
            $.ajax({
                type: 'POST',
                url: '/admin/activate_option',
                data: {
                    contractId: contractId,
                    optionId: optionId
                }
            });

            $.ajax({
                type: 'POST',
                url: '/admin/show_contract',
                data: {
                    contractId: contractId
                },
                success: function () {
                    location.reload();
                }
            })
        }

    </script>
</head>
<body>
<jsp:include page="admin_header.jsp"/>

<div class="container" style="margin-top: 65px">
    <div class="jumbotron">
        <c:if test="${options.size() < 1}">
            <h4>No options available :(</h4>
        </c:if>
        <c:forEach var="i" begin="0" step="3" end="${options.size()}">
            <div class="card-deck">
                <c:forEach var="j" begin="0" end="2">
                    <c:if test="${i + j < options.size()}">
                        <div class="card" style="width: 18rem;">
                            <div class="card-header">${options.get(i + j).name}</div>
                            <div class="card-body">
                                <p class="card-text">Cost: ${options.get(i + j).cost}</p>
                                <p class="card-text">Cost: ${options.get(i + j).accessCost}</p>
                                <p class="card-text">Some description</p>
                                <button type="submit" class="btn btn-primary"
                                        onclick="activateOption(${contractId}, ${options.get(i + j).id})">
                                    Activate
                                </button>
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
