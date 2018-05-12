<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="../../../resources/css/materialize.css">
    <link rel="stylesheet" type="text/css" href="../../../resources/css/bootstrap.css">
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<div class="container">
    <jsp:include page="admin_header.jsp"/>

    <div class="card" style="position: absolute; top:40%; left:50%; width: 400px;
                transform: translate(-50%,-50%);">
        <div class="card-body" style="margin: 10%">
            <form action="/admin/client_search" method="POST">
                <input type="text" name="number" placeholder="+79123456789">
                <button type="submit" class="btn btn-primary" style="text-transform: none" role="button">
                    <span class="fa fa-search"></span> Search
                </button>
            </form>
            <p style="color: red">${errorMessage}</p>
        </div>
    </div>
</div>

</body>
</html>
