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

    <title>NoName manager</title>
</head>
<body class="cm-no-transition cm-1-navbar">
<jsp:include page="admin_menu.jsp"/>
<jsp:include page="admin_header.jsp"/>
<div id="global">
    <div class="container-fluid cm-container-white">
        <h2 style="margin-top:0;">Client search</h2>
    </div>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-body">
                <h3 style="margin-top:0px">Enter contract number</h3>
                <div class="input-group input-group-lg" style="width: 50%">
                    <form onsubmit="return searchUser()" id="myFor">
                        <input type="text" class="form-control" placeholder="+7(981)7656565" id="search">
                        <input type="submit" class="btn btn-primary" value="Search">

                    </form>
                    <script>
                        function searchUser() {
                            $.ajax({
                                type: "GET",
                                url: "/api/contracts/number/" + $('#search').val(),
                                contentType : "application/json",
                            }).done(function (result) {
                               console.log(result);
                               window.location.replace('/admin/show_contract?number=' + result.number);
                            }).fail(function (result) {
                                $.sweetModal({
                                    content: "Contract with such number doesn't exist",
                                    icon: $.sweetModal.ICON_ERROR
                                })

                            });

                            return false;
                        }
                    </script>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="../../../resources/assets/js/lib/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="../../../resources/css/sweet-modal/jquery.sweet-modal.min.css"/>
<script src="../../../resources/js/sweet-modal/jquery.sweet-modal.min.js"></script>

<script src="../../../resources/assets/js/jquery.mousewheel.min.js"></script>
<script src="../../../resources/assets/js/jquery.cookie.min.js"></script>
<script src="../../../resources/assets/js/fastclick.min.js"></script>
<script src="../../../resources/assets/js/bootstrap.min.js"></script>
<script src="../../../resources/assets/js/clearmin.min.js"></script>

</body>
</html>
