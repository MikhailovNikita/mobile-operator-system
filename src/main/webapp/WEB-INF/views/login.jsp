<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

    <jsp:include page="stylesheet.jsp"/>
</head>
<body>
<div class="container">
    <div class="card" style="position: absolute; top:40%; left:50%;
                                transform: translate(-50%,-50%); width: 300px">
        <article class="card-body">
            <h4 class="card-title mb-4 mt-1">
                Sign in
            </h4>
            <form role="form" method="POST" action="/login">
                <div class="form-group">
                    <label>Your email</label>
                    <input id="login_field" name="username" class="form-control" placeholder="you@example.com" type="email">
                </div>
                <div class="form-group">
                    <a class="float-right" href="/password_reset">Forgot?</a>
                    <label>Your password</label>
                    <input id="password_field" name="password" class="form-control" placeholder="******" type="password">
                </div>
                <input type="hidden"
                       name="${_csrf.parameterName}"
                       value="${_csrf.token}"/>
                <div class="form-group">
                    <button id="login_submit" type="submit" class="btn btn-dark btn-block">Sign in</button>
                </div>
                <div class="form-group">
                    <a style="color: red">${errorMessage}</a>
                    <c:out value='${sessionScope.token}'/>
                </div>

            </form>
        </article>
    </div>
</div>
</body>
</html>