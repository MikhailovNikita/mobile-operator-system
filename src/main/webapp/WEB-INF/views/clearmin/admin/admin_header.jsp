<header id="cm-header">
    <nav class="cm-navbar cm-navbar-primary">
        <div class="btn btn-primary md-menu-white hidden-md hidden-lg" data-toggle="cm-menu"></div>
        <div class="cm-flex" style="cursor: pointer;" onclick="home()">
            <h1 href="">Home</h1>
        </div>
        <div class="pull-right">
            <div id="cm-logout-btn" class="btn btn-primary fa fa-sign-out" onclick="logout()"></div>
        </div>
    </nav>

    <script>
        function logout(){
            window.location.replace('/logout');
        }

        function home(){
            window.location.replace('/admin/new_client');
        }
    </script>
</header>
