<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" type="text/css" href="../../resources/css/materialize.css">
<link rel="stylesheet" type="text/css" href="../../resources/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../../resources/css/bootstrap-grid.css">
<link rel="stylesheet" type="text/css" href="../../resources/scss/_icons.scss">
<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<!-- A grey horizontal navbar that becomes vertical on small screens -->
<nav class="navbar  fixed-top navbar-expand-sm bg-light navbar-light">
    <a class="navbar-brand" href="/"><span class="fa fa-home"></span>Ecare</a>
    <ul class="navbar-nav mr-auto">
        <li class="nav-item">
            <a class="nav-link" href="/client/contracts">Contracts</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/tariffs">Tariffs</a>
        </li>
    </ul>


    <ul class="navbar-nav ml-1">
        <li><a href="/logout"><span class="fa fa-sign-out"></span> Logout</a></li>
    </ul>
    <ul class="navbar-nav ml-0">
        <li><a href="/login"><span class="fa fa-sign-in"></span> Login</a></li>
    </ul>
</nav>