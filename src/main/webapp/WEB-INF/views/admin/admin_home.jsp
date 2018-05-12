<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../../../resources/css/materialize.css">
    <link rel="stylesheet" type="text/css" href="../../../resources/css/bootstrap.css">
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <title>Admin home page</title>
</head>
<body>
<jsp:include page="admin_header.jsp"/>
<div class="container" style="margin-top: 65px">
    <div class="jumbotron">
        <h3>Admin home page</h3>
        <div>
            <h4>Clients</h4>
            <a href="/admin/new_client" class="btn btn-info" style="text-transform: none" role="button">
                <span class="fa fa-male"></span> New client
            </a>
            <a href="/admin/new_contract" class="btn btn-info" style="text-transform: none" role="button">
                <span class="fa fa-file"></span>
                New contract
            </a>
            <a href="/admin/client_search" class="btn btn-info" style="text-transform: none" role="button">
                <span class="fa fa-search"></span> Find client
            </a>
            <a href="/admin/clients" class="btn btn-info" style="text-transform: none" role="button">
                <span class="fa fa-male"></span><span class="fa fa-male"></span><span class="fa fa-male"></span>
                Clients
            </a>
        </div>
        <div>
            <h4>Tariffs</h4>
            <a href="/admin/new_tariff" class="btn btn-info" style="text-transform: none" role="button">
                <span class="fa fa-plus"></span> New tariff
            </a>
            <a href="/admin/delete_tariff" class="btn btn-info" style="text-transform: none" role="button">
                <span class="fa fa-minus"></span> Delete tariff
            </a>
            <a href="/admin/edit_tariff" class="btn btn-info" style="text-transform: none" role="button">
                <span class="fa fa-edit"></span> Edit options
            </a>
        </div>
        <div>
            <h4>Options</h4>
            <a href="/admin/new_option" class="btn btn-info" style="text-transform: none" role="button">
                <span class="fa fa-plus"></span> New option
            </a>
            <a href="/admin/forbid_options" class="btn btn-info" style="text-transform: none" role="button">
                <span class="fa fa-times"></span> Forbid options
            </a>
            <a href="/admin/required_options" class="btn btn-info" style="text-transform: none" role="button">
                <span class="fa fa-arrow-right"></span> Required options
            </a>
            <a href="/admin/options_rules" class="btn btn-info" style="text-transform: none" role="button">
                <span class="fa fa-exchange"></span> All rules
            </a>
        </div>
        <div>
            <h4>Search</h4>
            <form method="POST" action="/admin/client_search">
                <div class="input-group">
                    <input class="form-control form-control-lg" type="text" name="number" placeholder="+79124567890"/>
                    <span>
                        <button class="btn btn btn-info" style="text-transform: none" role="button">
                            <span class="fa fa-search"></span> Search
                        </button>
                    </span>
                </div>

            </form>
        </div>

    </div>

</div>

</body>
</html>
