<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8"  %>
<html>
<head>
    <title>New option</title>
    <jsp:include page="../stylesheet.jsp"/>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <script type="text/javascript">
        function submitForm(){
            var object = {};
            var myForm = document.getElementById('new_option');
            formData = new FormData(myForm);
            formData.forEach(function(value, key){
                object[key] = value;
            });
            var json = JSON.stringify(object);
            console.log(json);
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            var headers = [];
            headers[header] = token;
            $.ajax({
                type: "POST",
                url: "http://localhost:8081/api/options",
                contentType : "application/json",
                headers: headers,
                data: json
            }).done(function(result){
                myForm.reset();
                $('#popupMessage').html(result);
            }).fail(function(result){
                $('#popupMessage').html(result.responseText);
            });

            $('#popupModal').modal('show');
            return false;


        }
    </script>
</head>
<body>
<jsp:include page="admin_header.jsp"/>
<div id="main" class="container" style="margin-top: 65px">

    <div>
        <form name="New option" id="new_option" onsubmit="return submitForm()">
            <input id="name" name="name" type="text" >
            <input id="cost" name="cost" type="number">
            <input id="accessCost" name="accessCost" type="number">
            <input type="submit">
        </form>
    </div>

    <jsp:include page="../resultPopup.jsp"/>
</div>


</body>
</html>