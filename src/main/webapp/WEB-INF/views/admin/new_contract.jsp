<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>New contract page</title>
    <jsp:include page="../stylesheet.jsp"/>
    <script type="text/javascript">
        function submitForm() {
            var object = {};
            var myForm = document.getElementById('new_contract');
            formData = new FormData(myForm);
            formData.forEach(function (value, key) {
                if(key === 'tariff'){
                    var tariffDTO = {};
                    tariffDTO.id = value;
                    object['tariffDTO'] = tariffDTO;
                }else{
                    object[key] = value;
                }

            });
            var json = JSON.stringify(object);
            console.log(json);
            $.ajax({
                type: "POST",
                url: "http://localhost:8081/api/contracts",
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

    <script type="text/javascript">
        $(document).ready(function () {
            $.getJSON('/api/tariffs', function (data){
                $(data).each(function(){
                    var tariffOption = "<option value=\"" + this.id + "\">" + this.name + "</option>";
                    $('#tariffs').append(tariffOption);
                })
            })
        })
    </script>
</head>
<body>
<div class="container">
    <div>
        <form name="New contract" id="new_contract" onsubmit="return submitForm()">
            <input id="ownersPassport" name="ownersPassport" type="text">
            <input id="number" name="number" type="tel" pattern="[\+]\d{2}[\(]\d{2}[\)]\d{4}[\-]\d{4}">
            <select id="tariffs">
            </select>
            <input type="submit">
        </form>
    </div>

</div>

</body>
</html>
