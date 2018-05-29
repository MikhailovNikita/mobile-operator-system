<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1">
    <link rel="stylesheet" type="text/css" href="../../resources/assets/css/bootstrap-clearmin.min.css">
    <link rel="stylesheet" type="text/css" href="../../resources/assets/css/roboto.css">
    <link rel="stylesheet" type="text/css" href="../../resources/assets/css/font-awesome.min.css">
    <title>Login page</title>
    <style></style>
</head>
<body class="cm-login">

<div class="text-center" style="padding:90px 0 30px 0;background:#fff;border-bottom:1px solid #ddd">
    <img src="../../resources/assets/img/logo.png" width="300" height="45">
</div>

<div class="col-sm-6 col-md-4 col-lg-3" style="margin:40px auto; float:none;">
    <form method="post" action="/login">
        <div class="col-xs-12">
            <div class="form-group">
                <div class="input-group">
                    <div class="input-group-addon"><i class="fa fa-fw fa-user"></i></div>
                    <input id="login_field" type="text" name="username" class="form-control" placeholder="Username">
                </div>
            </div>
            <div class="form-group">
                <div class="input-group">
                    <div class="input-group-addon"><i class="fa fa-fw fa-lock"></i></div>
                    <input id="password_field" type="password" name="password" class="form-control" placeholder="Password">
                </div>
            </div>
            <button id="login_submit" type="submit" class="btn btn-block btn-primary">Sign in</button>
            <c:if test="${param.error}">
                <div style="text-align: center; margin-top: 5px">
                    <p>Wrong username or password</p>
                </div>

            </c:if>
        </div>

    </div>
    </form>
</div>
</body>
</html>