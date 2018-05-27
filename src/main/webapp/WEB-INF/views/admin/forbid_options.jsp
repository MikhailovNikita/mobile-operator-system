<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="../util/stylesheet.jsp"/>
    <script>
        $(document).ready(function(){
            $.getJSON('/api/options', function(data){
                $(data).each(function(){
                    var option = "<option value=\"" + this.id + "\">" + this.name + "</option>";
                    $('#options1').append(option);
                    $('#options2').append(option);
                })
            })
        })
    </script>

    <script>
        function submitForm() {
            var type = $('#forbid_radio').is(':checked') ? 'forbid' : 'require';
            $.ajax({
                type: "PUT",
                url: "http://localhost:8081/api/options/" + $('#options1').val() + "/" + type + "/" + $('#options2').val()
            }).done(function (result) {
                alert(result);
            }).fail(function (result) {
                alert(result.responseText);
            });

            return false;
        }
    </script>
</head>
<body>
    <form name="New rule" id="new_rule" onsubmit="return submitForm()">
        <select id="options1">
        </select>

        <select id="options2">
        </select>

        <p><input id = "forbid_radio" name="type" type="radio" value="forbid">Forbid</p>
        <p><input id = "require_radio" name="type" type="radio" value="require">Require</p>
        <input type="submit">
    </form>
</body>
</html>
