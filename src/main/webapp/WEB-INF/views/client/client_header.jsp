<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<link rel="stylesheet" type="text/css" href="../../resources/css/bootstrap.css">
<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<!-- A grey horizontal navbar that becomes vertical on small screens -->
<nav class="navbar navbar-expand-sm bg-light navbar-light" style="width: 80%; margin: 30px auto 0; border-radius: 7px">


    <a class="navbar-brand" href="/"><span class="fa fa-home"></span>Ecare</a>
    <ul class="navbar-nav mr-auto">
        <li class="nav-item">
            <a class="nav-link" href="/client">Acc</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/tariffs">Tariffs</a>
        </li>
    </ul>

    <sec:authorize access="isAnonymous()">
        <ul class="navbar-nav ml-0">
            <li><a href="/login"><span class="fa fa-sign-in"></span> Login</a></li>
        </ul>
    </sec:authorize>

    <sec:authorize access="hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')">
        <sec:authentication var="user" property="principal"/>
        <ul class="nav-item ml-1 dropdown">
            <a class="nav-link dropdown-toggle" style="display:none" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Account
            </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="#">${user.username}</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="#">Another action</a>

                <a class="dropdown-item" href="#">Something else here</a>
            </div>
        </ul>


        </ul>
    </sec:authorize>




</nav>