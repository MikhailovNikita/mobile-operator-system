<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1">

    <link rel="stylesheet" type="text/css" href="../../../resources/assets/css/bootstrap-clearmin.min.css">
    <link rel="stylesheet" type="text/css" href="../../../resources/assets/css/roboto.css">
    <link rel="stylesheet" type="text/css" href="../../../resources/assets/css/material-design.css">
    <link rel="stylesheet" type="text/css" href="../../../resources/assets/css/small-n-flat.css">
    <link rel="stylesheet" type="text/css" href="../../../resources/assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="../../../resources/css/sweet-modal/jquery.sweet-modal.min.css"/>
    <script src="../../../resources/assets/js/lib/jquery-2.1.3.min.js"></script>
    <link rel="stylesheet" href="../../../resources/css/sweet-modal/jquery.sweet-modal.min.css"/>
    <script src="../../../resources/js/sweet-modal/jquery.sweet-modal.min.js"></script>

    <title>NoName manager</title>
</head>
<body class="cm-no-transition cm-1-navbar">
<jsp:include page="admin_menu.jsp"/>
<jsp:include page="admin_header.jsp"/>
<div id="global">
    <div class="container-fluid cm-container-white">
        <h2 style="margin-top:0;">Editing tariff</h2>
    </div>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-body" id="tariff-container">

            </div>
        </div>
    </div>
</div>

<script>
    function updateOptions(id) {
        console.log('Updating options for tariff ' + id);
        var options = $('#newOptions').val();
        var tariffDTO = {};
        tariffDTO.id = id;
        tariffDTO.optionIds = options;
        var json = JSON.stringify(tariffDTO);
        console.log(json);
        $.ajax({
            type: "PUT",
            url: '/api/tariffs',
            data: json,
            contentType: "application/json"
        }).done(function (result) {
            $.sweetModal({
                content: result,
                icon: $.sweetModal.ICON_SUCCESS
            });

            renderPage();
        }).fail(function (result) {
            $.sweetModal({
                content: result.responseText,
                icon: $.sweetModal.ICON_ERROR
            })
        });
    }

    function hot(id) {
        console.log('Changing hot status for ' + id);
        $.ajax({
            type: "PUT",
            url: "/api/tariffs/hot/" + id,
            contentType: "application/json",
        }).done(function (result) {
            $.sweetModal({
                content: result,
                icon: $.sweetModal.ICON_SUCCESS
            })
            renderPage();
        }).fail(function (result) {
            console.log(result);
            $.sweetModal({
                content: result.responseText,
                icon: $.sweetModal.ICON_ERROR
            })
        });
    }

    function archive(id) {
        $.sweetModal.confirm('Are you sure? There\'s no way back', function () {
            console.log('Archiving ' + id);
            $.ajax({
                type: "PUT",
                url: "/api/tariffs/archive/" + id,
                contentType: "application/json",
            }).done(function (result) {
                $.sweetModal({
                    content: result,
                    icon: $.sweetModal.ICON_SUCCESS
                });
                renderPage();
            }).fail(function (result) {
                console.log(result);
                $.sweetModal({
                    content: result.responseText,
                    icon: $.sweetModal.ICON_ERROR
                })
            });
        });

    }

    function renderPage() {

        var tc = $('#tariff-container');
        tc.empty();
        $.getJSON('/api/tariffs/' + <c:out value="${param.id}"/>, function (tariff) {
            console.log(tariff.archive);
            tc.append('<h3>Tariff: ' + tariff.name + (tariff.archive ? "[Archived]" : "") + '</h3>');
            tc.append('<hr>');
            if(tariff.archive){
                return;
            }
            tc.append(tariff.hot ? '<button class="btn btn-primary" style="display: inline-block" onclick="hot(' + tariff.id + ')">Remove from hot</button>'
                : '<button class="btn btn-primary" style="display: inline-block" onclick="hot(' + tariff.id + ')">Make hot!</button>');

            tc.append(!tariff.archive ? '<button style="margin-left: 20px" class="btn btn-primary" onclick="archive(' + tariff.id + ')">Archive</button>' : '');

            tc.append('<div class="form-check" id="checkboxList"></div>');

            $.getJSON('/api/options', function (options) {
                var form = '<form style="margin-top: 20px; width: 40%">';
                form += '<div class="form-group">\n' +
                    '    <label for="newOptions">Select new supported options:</label>\n' +
                    '    <select multiple class="form-control" id="newOptions">'

                options.forEach(function(option){
                    form += (' <option value="' +  option.id + '">' + option.name + '</option>');
                });

                form += ('</select></div></form>');

                tc.append(form);
                tc.append('<button class="btn btn-primary" onclick="updateOptions(' + tariff.id + ')">Submit</button>');
            })
        });

    }

    $(document).ready(function () {
        renderPage();
    })
</script>

<script src="../../../resources/assets/js/jquery.mousewheel.min.js"></script>
<script src="../../../resources/assets/js/jquery.cookie.min.js"></script>
<script src="../../../resources/assets/js/fastclick.min.js"></script>
<script src="../../../resources/assets/js/bootstrap.min.js"></script>
<script src="../../../resources/assets/js/clearmin.min.js"></script>

</body>
</html>
