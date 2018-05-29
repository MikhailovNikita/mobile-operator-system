<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1">

    <link rel="stylesheet" type="text/css" href="../../../resources/assets/css/bootstrap-clearmin.min.css">
    <link rel="stylesheet" type="text/css" href="../../../resources/assets/css/roboto.css">
    <link rel="stylesheet" type="text/css" href="../../../resources/assets/css/material-design.css">
    <link rel="stylesheet" type="text/css" href="../../../resources/assets/css/small-n-flat.css">
    <link rel="stylesheet" type="text/css" href="../../../resources/assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="../../../resources/css/sweet-modal/jquery.sweet-modal.min.css"/>
    <script src="../../../resources/assets/js/lib/jquery-2.1.3.min.js"></script>
    <link rel="stylesheet" href="../../../resources/css/sweet-modal/jquery.sweet-modal.min.css"/>
    <script src="../../../resources/js/sweet-modal/jquery.sweet-modal.min.js"></script>

    <title>Options rules</title>
</head>
<body class="cm-no-transition cm-1-navbar">
<jsp:include page="admin_menu.jsp"/>
<jsp:include page="admin_header.jsp"/>
<div id="global">
    <div class="container-fluid cm-container-white">
        <h2 style="margin-top:0;">All rules</h2>
    </div>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-body" id="table-container">
                <label>Forbidding rules</label>
                <c:if test="${forbiddingRules.size() != 0}">
                    <table id="table-body" class="table table-striped" cellspacing="0" cellpadding="0">
                        <thead><tr><th>Option 1</th><th>Option 2</th><th>Action</th></tr></thead>
                        <tbody>
                        <c:forEach items="${forbiddingRules}" var="pair">
                            <tr><td>${pair.key.name}</td><td>${pair.value.name}</td><td><button class="btn btn-danget" onclick="deleteFR(${pair.key.id}, ${pair.value.id})">Delete</button></td></tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>

                <c:if test="${forbiddingRules.size() == 0}">
                    <h4>No rules!</h4>
                </c:if>

                <label>Requiring rules</label>
                <c:if test="${requiringRules.size() != 0}">
                    <table id="table-body2" class="table table-striped" cellspacing="0" cellpadding="0">
                        <thead><tr><th>Option 1</th><th>Option 2</th><th>Action</th></tr></thead>
                        <tbody>
                        <c:forEach items="${requiringRules}" var="pair">
                            <tr><td>${pair.key.name}</td><td>${pair.value.name}</td><td><button class="btn btn-danget" onclick="deleteRR(${pair.key.id}, ${pair.value.id})">Delete</button></td></tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>


                <c:if test="${requiringRules.size() == 0}">
                    <h4>No rules!</h4>
                </c:if>
            </div>
        </div>
    </div>
</div>


<script>
    function deleteFR(id1, id2){
        $.ajax({
            type: "DELETE",
            url: '/api/options/' + id1 + '/forbid/' + id2
        }).done(function (result) {
            $.sweetModal({
                content: result,
                icon: $.sweetModal.ICON_SUCCESS,

                buttons: [
                    {
                        label: 'OK',
                        classes: 'greenB',
                        action: function () {
                            window.location.reload();
                        }
                    }
                ]
            })
        }).fail(function (result) {
            console.log(result.responseText);
            $.sweetModal({
                content: result.responseText,
                icon: $.sweetModal.ICON_ERROR
            })
        });
    }

    function deleteRR(id1, id2){
        $.ajax({
            type: "DELETE",
            url: '/api/options/' + id1 + '/require/' + id2
        }).done(function (result) {
            $.sweetModal({
                content: result,
                icon: $.sweetModal.ICON_SUCCESS,

                buttons: [
                    {
                        label: 'OK',
                        classes: 'greenB',
                        action: function () {
                            window.location.reload();
                        }
                    }
                ]
            })
        }).fail(function (result) {
            console.log(result.responseText);
            $.sweetModal({
                content: result.responseText,
                icon: $.sweetModal.ICON_ERROR
            })
        });
    }
</script>
<script src="../../../resources/assets/js/jquery.mousewheel.min.js"></script>
<script src="../../../resources/assets/js/jquery.cookie.min.js"></script>
<script src="../../../resources/assets/js/fastclick.min.js"></script>
<script src="../../../resources/assets/js/bootstrap.min.js"></script>
<script src="../../../resources/assets/js/clearmin.min.js"></script>

</body>
</html>
