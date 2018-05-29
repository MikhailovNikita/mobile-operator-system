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

    <title>NoName manager</title>
</head>
<body class="cm-no-transition cm-1-navbar">
<jsp:include page="admin_menu.jsp"/>
<jsp:include page="admin_header.jsp"/>
<div id="global">
    <div class="container-fluid cm-container-white">
        <h2 style="margin-top:0;">Contract page</h2>
    </div>
    <div class="container-fluid" id="contract-page">
        <div class="panel panel-default">
            <div class="panel-body" id="contract-container">

            </div>
        </div>
    </div>
</div>


<script src="../../../resources/assets/js/lib/jquery-2.1.3.min.js"></script>

<link rel="stylesheet" href="../../../resources/css/sweet-modal/jquery.sweet-modal.min.css"/>
<script src="../../../resources/js/sweet-modal/jquery.sweet-modal.min.js"></script>
<script>
    $(document).ready(function () {
        renderPage();
    });

    function renderPage() {
        $('#contract-container').empty();
        $('#contract-container').append('<h3 id="contract_number">Number: </h3>\n' +
            '                <span id="contract_status"></span>\n' +
            '                <a id="contract_date" class="text-muted">Created on: </a><br>\n' +
            '                <h4 id="contract_payment"></h4>\n' +
            '                <h4 id="contract_tariff">Tariff: </h4>\n' +
            '\n' +
            '                <table class="table table-hover" style="width: 50%">\n' +
            '                    <caption>Enabled options:</caption>\n' +
            '                    <thead>\n' +
            '                    <tr>\n' +
            '                        <th>Name</th>\n' +
            '                        <th>Cost</th>\n' +
            '                        <th>Access cost</th>\n' +
            '                        <th style="width: 10%">Action</th>\n' +
            '                    </tr>\n' +
            '                    </thead>\n' +
            '                    <tbody id="options_table_body">\n' +
            '\n' +
            '                    </tbody>\n' +
            '                </table>\n' +
            '                <h5 id="contract-cost">Total cost: </h5>\n' +
            '                <hr>\n' +
            '                <h4>Edit contract:</h4>\n' +
            '                <form onsubmit="return changeTariff()">\n' +
            '                    <div class="form-group">\n' +
            '                        <label for="tariffs">Tariff</label>\n' +
            '                        <select class="form-control" id="tariffs" style="width: 25%">\n' +
            '                        </select>\n' +
            '                        <button type="submit" style="margin-top: 5px" class="btn btn-primary">Change</button>\n' +
            '                    </div>\n' +
            '                </form>\n' +
            '\n' +
            '                <form onsubmit="return enableOption()">\n' +
            '                    <div class="form-group">\n' +
            '                        <label for="tariffs">Option</label>\n' +
            '                        <select class="form-control" id="options" style="width: 25%">\n' +
            '                        </select>\n' +
            '                        <button type="submit" style="margin-top: 5px" class="btn btn-primary">Enable</button>\n' +
            '                    </div>\n' +
            '                </form>');


        $.getJSON('/api/contracts/number/' + '${contract.number}', function response(result) {
            $('#contract_number').append(result.number);
            if (!result.blockedByAdmin && !result.blockedByUser) {
                $('#contract_status').css('color', 'green');
                $('#contract_status').append('Active');
            } else {
                $('#contract_status').css('color', 'red');
                if (result.blockedByAdmin) {
                    $('#contract_status').append('Blocked by operator');
                } else {
                    $('#contract_status').append('Blocked by user');
                }
            }

            var date = new Date(result.conclusionDate)
            $('#contract_date').append(date.getDay() + "/" + date.getMonth() + "/" + date.getFullYear());


            $('#contract_tariff').append(result.tariffDTO.name);
            $('#contract-cost').append(result.monthlyPayment + '$/month')
            console.log(result);

            result.options.forEach(function (option) {
                $('#options_table_body').append(" <tr>\n" +
                    "                <td>" + option.name + "</td>\n" +
                    "                <td>" + option.cost + "</td>\n" +
                    "                <td>" + option.accessCost + "</td>\n" +
                    "\n" +
                    "                <th style=\"width: 10%\">" + "<button class=\"btn btn-danger\" onclick='disableOption(" + option.id + ")'>Disable</button>" + "</th>\n" +
                    "                    </tr>");

            })

            if (!result.blockedByAdmin) {
                $('#contract-container').append('<button id="block_button" onclick="changeBlockStatus(' + <c:out value="${contract.contractId}"/> +',true)" class="btn btn-danger">Block</button>')
            } else {
                $('#contract-container').append('<button id="block_button" onclick="changeBlockStatus(' + <c:out value="${contract.contractId}"/> +',false)" class="btn btn-danger">Unblock</button>')
            }

            $.getJSON('/api/tariffs/active', function (data) {
                $('#tariffs').empty();
                $(data).each(function () {
                    if (this.id !== result.tariffDTO.id) {
                        var tariffOption = "<option value=\"" + this.id + "\">" + this.name + "</option>";
                        $('#tariffs').append(tariffOption);
                    }

                })
            });

            $.getJSON('/api/options', function (data) {
                $('#options').empty();
                $(data).each(function () {
                    var tariffOption = "<option value=\"" + this.id + "\">" + this.name + "</option>";
                    $('#options').append(tariffOption);
                })
            });

        });
    }

    function changeTariff() {
        $.ajax({
            type: "PUT",
            url: "/api/contracts/" + <c:out value="${contract.contractId}"/> +"/tariff/" + $('#tariffs').val(),
            contentType: "application/json",
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

        return false;
    }

    function enableOption() {
        $.ajax({
            type: "PUT",
            url: "/api/contracts/" + <c:out value="${contract.contractId}"/> +"/option/enable/" + $('#options').val(),
            contentType: "application/json",
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

        return false;
    }

    function disableOption(optionId) {
        $.ajax({
            type: "PUT",
            url: "/api/contracts/" + <c:out value="${contract.contractId}"/> +"/option/disable/" + optionId,
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

        return false;
    }

    //type == true -> blocking, otherwise unblocking
    function changeBlockStatus(id, type) {
        $.ajax({
            type: "PUT",
            url: "/api/contracts/admin/" + (type ? 'block/' : 'unblock/') + id,
        }).done(function (result) {
            $.sweetModal({
                content: result,
                icon: $.sweetModal.ICON_SUCCESS
            })
            renderPage();
        }).fail(function (result) {
            console.log(result.responseText);
            $.sweetModal({
                content: result.responseText,
                icon: $.sweetModal.ICON_ERROR
            })
        })
    }
</script>
<script src="../../../resources/assets/js/jquery.mousewheel.min.js"></script>
<script src="../../../resources/assets/js/jquery.cookie.min.js"></script>
<script src="../../../resources/assets/js/fastclick.min.js"></script>
<script src="../../../resources/assets/js/bootstrap.min.js"></script>
<script src="../../../resources/assets/js/clearmin.min.js"></script>

</body>
</html>

