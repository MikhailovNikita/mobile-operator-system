<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New tariff page</title>
    <jsp:include page="../stylesheet.jsp"/>

    <script type="text/javascript">
        $(document).ready(function () {
            $.getJSON('/api/options', function (data) {
                $(data).each(function () {
                    var tariffOption = "<input id=\"option\" type=\"checkbox\" value=\"" + this.id + "\"  name=\"option\">" + this.name + "</option>";
                    $('#new_tariff').append(tariffOption);
                })
                $('#new_tariff').append("<input type=\"submit\">");
            })
        })
    </script>

    <script type="text/javascript">
        function submitForm() {
            var object = {};
            object.optionIds = [];
            var myForm = document.getElementById('new_tariff');
            var formData = new FormData(myForm);
            formData.forEach(function (value, key) {
                if(key === 'option'){
                    object.optionIds.push(value);
                }else{
                    object[key] = value;
                }

            });
            var json = JSON.stringify(object);
            console.log(json);
            $.ajax({
                type: "POST",
                url: "http://localhost:8081/api/tariffs",
                contentType: "application/json",
                data: json
            }).done(function (result) {
                myForm.reset();
                alert('Success')
            }).fail(function (result) {
                alert('Fail');
            });

            return false;


        }
    </script>
</head>
<body>
<div class="container">
    <div>
        <form name="New tariff" id="new_tariff" onsubmit="return submitForm()">
            <input id="name" name="name" type="text">
            <input id="cost" name="cost" type="number">
        </form>
    </div>
</div>

</body>
</html>
