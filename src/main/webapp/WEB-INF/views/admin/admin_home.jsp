<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>

    <%--<jsp:include page="../util/stylesheet.jsp"/>--%>
    <title>Admin home page</title>

    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <style>

        #sidebar-wrapper {
            position: fixed;
            width: 200px;
            height: 100%;
            margin-left: -200px;
            overflow-y: auto;
            background: #343a40;
        }

        #page-content-wrapper {
            width: 100%;
            position: absolute;
            padding: 15px;
            margin-right: -200px;
        }

        /* Sidebar Styles */
        .sidebar-nav {
            position: absolute;
            top: 0;
            width: 200px;
            margin: 0;
            padding: 0;
        }

        .sidebar-nav li {
            text-indent: 20px;
            line-height: 40px;
        }

        .sidebar-nav li a {
            cursor: pointer;
            display: block;
            color: white !important;
        }

        .sidebar-nav li a:hover {
            color: #fff;
            background: rgba(255, 255, 255, 0.2);
        }

        .sidebar-nav > .sidebar-brand a:hover {
            color: #fff;
            background: none;
        }

        #wrapper {
            margin-top: 55px;
            padding-left: 0;
        }

        #wrapper {
            padding-left: 200px;
        }

        #page-content-wrapper {
            padding: 20px;
            position: relative;
        }

        #page-content-wrapper {
            position: relative;
            margin-right: 0;
        }

    </style>
</head>
<body>
<jsp:include page="admin_header.jsp"/>
<div id="wrapper">

    <div id="sidebar-wrapper">
        <ul class="sidebar-nav">
            <li>
                <a data-toggle="collapse" data-target="#newUserCollapse" role="button"
                   data-parent="#wrapper">
                    <span class="fa fa-user"></span>New user
                </a>
            </li>
            <li>
                <a data-toggle="collapse" data-target="#searchUserCollapse" role="button"
                   data-parent="#wrapper">
                    <span class="fa fa-search"></span>New user
                </a>
            </li>
            <li>
                <a data-toggle="collapse" data-target="#newContractCollapse" role="button"
                   data-parent="#wrapper">
                    New contract
                </a>
            </li>

            <li>
                <a data-toggle="collapse" data-target="#newOptionCollapse" role="button"
                   data-parent="#wrapper">
                    New supportedOptions
                </a>
            </li>

            <li>
                <a data-toggle="collapse" data-target="#newTariffCollapse" role="button"
                   data-parent="#wrapper">
                    New tariff
                </a>
            </li>
        </ul>
    </div>
    <!-- /#sidebar-wrapper -->

    <!-- Page Content -->
    <div id="page-content-wrapper" style="margin: 10px; background-color: lightgrey">
        <div class="collapse show" id="newUserCollapse">
            <jsp:include page="../clearmin/form_templates/new_client_form.jsp"/>
        </div>
        <div class="collapse" id="newContractCollapse">
            <jsp:include page="../clearmin/form_templates/new_contract_form.jsp"/>
        </div>
        <div class="collapse" id="newOptionCollapse">
            <jsp:include page="../clearmin/form_templates/new_option_form.jsp"/>
        </div>
        <div class="collapse" id="newTariffCollapse">
            <jsp:include page="../clearmin/form_templates/new_tariff_form.jsp"/>
        </div>
        <div class="collapse">
            <jsp:include page="user_search.jsp"/>
        </div>
    </div>

    <script>
        $('#newContractCollapse').on('shown.bs.collapse', function () {
            console.log('Refreshing new contract form');
            initNewContractForm();
        });
        $('#newTariffCollapse').on('shown.bs.collapse', function () {
            console.log('Refreshing new tariff form');
            initNewTariffForm();
        });
        function toggleDropdown(){
            var x = $('#products');
            if (x.style.display === "none") {
                x.style.display = "block";
            } else {
                x.style.display = "none";
            }
        }
    </script>
    <link rel="stylesheet" href="../../resources/css/sweet-modal/jquery.sweet-modal.min.css"/>
    <script src="../../resources/js/sweet-modal/jquery.sweet-modal.min.js"></script>
</div>


<%--<jsp:include page="admin_header.jsp"/>--%>

<%--<div class="jumbotron">--%>
<%--<h3>Admin home page</h3>--%>
<%--<div>--%>
<%--<h4>Clients</h4>--%>
<%--<a href="/admin/new_client" class="btn btn-info" style="text-transform: none" role="button">--%>
<%--<span class="fa fa-male"></span> New client--%>
<%--</a>--%>
<%--<a href="/admin/new_contract" class="btn btn-info" style="text-transform: none" role="button">--%>
<%--<span class="fa fa-file"></span>--%>
<%--New contract--%>
<%--</a>--%>
<%--<a href="/admin/client_search" class="btn btn-info" style="text-transform: none" role="button">--%>
<%--<span class="fa fa-search"></span> Find client--%>
<%--</a>--%>
<%--<a href="/admin/clients" class="btn btn-info" style="text-transform: none" role="button">--%>
<%--<span class="fa fa-male"></span><span class="fa fa-male"></span><span class="fa fa-male"></span>--%>
<%--Clients--%>
<%--</a>--%>
<%--</div>--%>
<%--<div>--%>
<%--<h4>Tariffs</h4>--%>
<%--<a href="/admin/new_tariff" class="btn btn-info" style="text-transform: none" role="button">--%>
<%--<span class="fa fa-plus"></span> New tariff--%>
<%--</a>--%>
<%--<a href="/admin/delete_tariff" class="btn btn-info" style="text-transform: none" role="button">--%>
<%--<span class="fa fa-minus"></span> Delete tariff--%>
<%--</a>--%>
<%--<a href="/admin/edit_tariff" class="btn btn-info" style="text-transform: none" role="button">--%>
<%--<span class="fa fa-edit"></span> Edit options--%>
<%--</a>--%>
<%--</div>--%>
<%--<div>--%>
<%--<h4>Options</h4>--%>
<%--<a href="/admin/new_option" class="btn btn-info" style="text-transform: none" role="button">--%>
<%--<span class="fa fa-plus"></span> New supportedOptions--%>
<%--</a>--%>
<%--<a href="/admin/forbid_options" class="btn btn-info" style="text-transform: none" role="button">--%>
<%--<span class="fa fa-times"></span> Forbid options--%>
<%--</a>--%>
<%--<a href="/admin/required_options" class="btn btn-info" style="text-transform: none" role="button">--%>
<%--<span class="fa fa-arrow-right"></span> Required options--%>
<%--</a>--%>
<%--<a href="/admin/options_rules" class="btn btn-info" style="text-transform: none" role="button">--%>
<%--<span class="fa fa-exchange"></span> All rules--%>
<%--</a>--%>
<%--</div>--%>
<%--<div>--%>
<%--<h4>Search</h4>--%>
<%--<form method="POST" action="/admin/client_search">--%>
<%--<div class="input-group">--%>
<%--<input class="form-control form-control-lg" type="text" name="number" placeholder="+79124567890"/>--%>
<%--<span>--%>
<%--<button class="btn btn btn-info" style="text-transform: none" role="button">--%>
<%--<span class="fa fa-search"></span> Search--%>
<%--</button>--%>
<%--</span>--%>
<%--</div>--%>

<%--</form>--%>
<%--</div>--%>

<%--</div>--%>


</body>
</html>
