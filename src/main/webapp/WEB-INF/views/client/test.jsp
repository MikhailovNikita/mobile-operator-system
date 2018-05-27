<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1">
    <link rel="stylesheet" type="text/css" href="../../resources/assets/css/bootstrap-clearmin.min.css">
    <link rel="stylesheet" type="text/css" href="../../resources/assets/css/roboto.css">
    <link rel="stylesheet" type="text/css" href="../../resources/assets/css/material-design.css">
    <link rel="stylesheet" type="text/css" href="../../resources/assets/css/small-n-flat.css">
    <link rel="stylesheet" type="text/css" href="../../resources/assets/css/font-awesome.min.css">
    <title>Clearmin Page</title>
</head>
<body class="cm-no-transition cm-1-navbar">
<div id="cm-menu">
    <nav class="cm-navbar cm-navbar-primary">
        <div class="cm-flex"><div class="cm-logo"></div></div>
        <div class="btn btn-primary md-menu-white" data-toggle="cm-menu"></div>
    </nav>
    <div id="cm-menu-content">
        <div id="cm-menu-items-wrapper">
            <div id="cm-menu-scroller">
                <ul class="cm-menu-items">
                    <li class="active"><a href="#" class="sf-house">This page is active</a></li>
                    <li><a href="#" class="sf-">This page is not active?</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>
<header id="cm-header">
    <nav class="cm-navbar cm-navbar-primary">
        <div class="btn btn-primary md-menu-white hidden-md hidden-lg" data-toggle="cm-menu"></div>
        <div class="cm-flex"><h1>Put your title here</h1></div>
    </nav>
</header>
<div id="global">
    <div class="container-fluid cm-container-white">
        <h2 style="margin-top:0;">Add new client</h2>
    </div>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-body">
                <jsp:include page="../admin/new_client_form.jsp"/>
                <link rel="stylesheet" href="../../resources/css/sweet-modal/jquery.sweet-modal.min.css"/>
                <script src="../../resources/js/sweet-modal/jquery.sweet-modal.min.js"></script>
            </div>
        </div>
    </div>
</div>

<script src="../../resources/assets/js/lib/jquery-2.1.3.min.js"></script>
<script src="../../resources/assets/js/jquery.mousewheel.min.js"></script>
<script src="../../resources/assets/js/jquery.cookie.min.js"></script>
<script src="../../resources/assets/js/fastclick.min.js"></script>
<script src="../../resources/assets/js/bootstrap.min.js"></script>
<script src="../../resources/assets/js/clearmin.min.js"></script>
</body>
</html>