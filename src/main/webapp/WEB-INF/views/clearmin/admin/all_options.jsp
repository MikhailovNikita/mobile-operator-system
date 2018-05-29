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

    <title>NoName manager</title>
</head>
<body class="cm-no-transition cm-1-navbar">
<jsp:include page="admin_menu.jsp"/>
<jsp:include page="admin_header.jsp"/>
<div id="global">
    <div class="container-fluid cm-container-white">
        <h2 style="margin-top:0;">All options</h2>
    </div>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-body" id="table-container">

            </div>
        </div>
    </div>
</div>


<script>
    $(document).ready(function(){

        $.getJSON('/api/options', function (data) {
            console.log(data);

            var content = '<table id="table-body" class="table table-striped" cellspacing="0" cellpadding="0">';
            content = content  + '<thead><tr><th>#</th><th>Title</th><th>Cost</th><th>Access cost</th></tr></thead>';
            content += '<tbody>';
            for(var i = 0; i < data.length; i++){
                content += '<tr><td>' + (i + 1) + '</td><td>' + data[i].name
                    + '</td><td>' + data[i].cost + '</td><td>' + data[i].accessCost + '</td></tr>';
            }
            content += "</tbody></table>";
            console.log(content);
            $('#table-container').append(content);
        });
    });


</script>
<script src="../../../resources/assets/js/jquery.mousewheel.min.js"></script>
<script src="../../../resources/assets/js/jquery.cookie.min.js"></script>
<script src="../../../resources/assets/js/fastclick.min.js"></script>
<script src="../../../resources/assets/js/bootstrap.min.js"></script>
<script src="../../../resources/assets/js/clearmin.min.js"></script>

</body>
</html>
