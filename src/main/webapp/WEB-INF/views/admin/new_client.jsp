<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <jsp:include page="../stylesheet.jsp"/>
    <title>Registration page</title>

    <script type="text/javascript">
        function submitForm(){
            var object = {};
            var myForm = document.getElementById('new_client');
            formData = new FormData(myForm);
            formData.forEach(function(value, key){
                object[key] = value;
            });
            var json = JSON.stringify(object);
            console.log(json);
            $.ajax({
                type: "POST",
                url: "http://localhost:8081/api/clients",
                contentType : "application/json",
                data: json
            }).done(function(result){
                myForm.reset();
                alert('Success')
            }).fail(function(result){
                alert('Fail');
            });

            return false;


        }
    </script>

</head>
<body>
<div class="container">

    <div>
        <form name="New client" id="new_client" onsubmit="return submitForm()">
            <input id="name" name="name" type="text" >
            <input id="lastName" name="lastName" type="text">
            <input id="email" name="email" type="email">
            <input id="passport" name="passport" type="text">
            <input id="address" name="address" type="text">
            <input id="birthDate" name="birthDate" type="date">
            <input type="submit">
        </form>
    </div>

</div>
</body>
</html>
