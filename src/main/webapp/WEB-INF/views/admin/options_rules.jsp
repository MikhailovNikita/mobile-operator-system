<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="../../resources/css/materialize.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/bootstrap.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        function deleteFRule(id1, id2) {
            var x = confirm('Are you sure?');
            if (x === true) {
                $.ajax({
                    type: 'POST',
                    url: '/admin/delete_forbid_rule',
                    data: {
                        firstOption: id1,
                        secondOption: id2
                    },
                    success: function () {
                        location.reload();
                    }
                });
            }
        }

        function deleteRRule(id1, id2) {
            var x = confirm('Are you sure?');
            if (x === true) {
                $.ajax({
                    type: 'POST',
                    url: '/admin/delete_require_rule',
                    data: {
                        firstOption: id1,
                        secondOption: id2
                    },
                    success: function () {
                        location.reload();
                    }
                });
            }
        }
    </script>
</head>
<body>
<jsp:include page="admin_header.jsp"/>

<div class="container" style="margin-top: 65px">
    <div class="jumbotron">
        <div>
            <h5>Forbidding rules</h5>
            <table class="table table-hover">
                <tbody>
                <c:forEach items="${forbidRules}" var="rule">
                    <tr>
                        <td>${rule.key.name}</td>
                        <td>${rule.value.name}</td>
                        <td>
                            <button class="btn btn-danger" onclick="deleteFRule(${rule.key.id}, ${rule.value.id})">
                                Delete
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div>
            <h5>"Required" rules</h5>
            <table class="table table-hover">
                <tbody>
                <c:forEach items="${requireRules}" var="rule">
                    <tr>
                        <td>${rule.key.name}</td>
                        <td>${rule.value.name}</td>
                        <td>
                            <button class="btn btn-danger" onclick="deleteRRule(${rule.key.id}, ${rule.value.id})">
                                Delete
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </div>
</div>
</body>
</html>
