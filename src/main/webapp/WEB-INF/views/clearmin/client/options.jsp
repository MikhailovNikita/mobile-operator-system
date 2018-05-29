<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="../../../resources/assets/css/bootstrap-clearmin.min.css">
    <link rel="stylesheet" type="text/css" href="../../../resources/assets/css/roboto.css">
    <link rel="stylesheet" type="text/css" href="../../../resources/assets/css/material-design.css">
    <link rel="stylesheet" type="text/css" href="../../../resources/assets/css/small-n-flat.css">
    <link rel="stylesheet" type="text/css" href="../../../resources/assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="../../../resources/css/sweet-modal/jquery.sweet-modal.min.css"/>


    <title>NoName</title>
</head>
<body class="cm-no-transition cm-1-navbar">
<jsp:include page="client_menu.jsp"/>
<jsp:include page="client_header.jsp"/>
<div id="global">
    <div class="container-fluid cm-container-white">
        <h2 style="margin-top:0;">Tariffs for you!</h2>
    </div>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-body" id="options-container">

            </div>
        </div>
    </div>
</div>


<div id="card-pair-template" style="margin-top: 20px; display: none;">
    <div class="card" style="width: 30%;margin-left: 5%">
        <div class="card-body">
            <h4 id="title1" class="card-title">Card title</h4>
            <h6 id="subtitle1" class="card-subtitle mb-2 text-muted">Card subtitle</h6>
            <p id="desc1" class="card-text">Some quick example text to build on the card title and make up the bulk of
                the card's content.</p>
            <button id="btn1" class="btn btn-primary">Enable</button>
        </div>
    </div>
    <div class="card" style="width: 30%; margin-left: 10%">
        <div class="card-body">
            <h4 id="title2" class="card-title">Card title</h4>
            <h6 id="subtitle2" class="card-subtitle mb-2 text-muted">Card subtitle</h6>
            <p id="desc2" class="card-text">Some quick example text to build on the card title and make up the bulk of
                the card's
                content.</p>
            <button id="btn2" class="btn btn-primary">Enable</button>

        </div>
    </div>
</div>
<script src="../../../resources/assets/js/lib/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="../../../resources/css/sweet-modal/jquery.sweet-modal.min.css"/>
<script src="../../../resources/js/sweet-modal/jquery.sweet-modal.min.js"></script>
<script>

    function startOptionChange(optionId) {
        console.log('Enabling' + optionId);

        $.sweetModal({
            title: 'Choose a contract',
            content: '<div id="contractForNT">\n' +
            '    <select id="contract-selector" style="border:1px solid gray; margin:0; padding:0 .6em; height:2.3em; box-sizing:border-box;">\n' +
            '        <c:forEach items="${user.contracts}" var="contract">\n' +
            '            <option value="${contract.contractId}">${contract.number}</option>\n' +
            '        </c:forEach>\n' +
            '    </select>\n' +
            '    <button style="border:1px solid gray; margin:0; padding:0 .6em; height:2.3em; box-sizing:border-box;"\n' +
            '            onclick="enableOption($(\'#contract-selector\').val(),' +  optionId + ')">Go</button>\n' +
            '</div>'
        });
    }

    function enableOption(contractId, optionId){
        $.ajax({
            type: "PUT",
            url: "/api/contracts/" + contractId +  "/option/enable/" + optionId,
            contentType: "application/json"
        }).done(function (result) {
            $.sweetModal({
                content: result,
                icon: $.sweetModal.ICON_SUCCESS
            });

        }).fail(function (result) {
            $.sweetModal({
                content: result.responseText,
                icon: $.sweetModal.ICON_ERROR
            })
        });

    }

    $(document).ready(function () {

        $.getJSON('/api/options', function (data) {
            console.log(data);
            if (data.length === 0) {
                $('#options-container').append('<h4>No options available at the moment :(</h4>')
            } else {
                for (var i = 0; i < data.length; i += 2) {
                    var cardPair = $('#card-pair-template').clone();
                    if (i + 1 < data.length) {

                        cardPair.css('display', 'flex');
                        cardPair.find('#title1').html(data[i].name);
                        cardPair.find('#subtitle1').html(data[i].cost + '$');
                        cardPair.find('#desc1').html('Some description');
                        cardPair.find('#btn1').attr('onclick', 'startOptionChange(' + data[i].id + ')');

                        cardPair.find('#title2').html(data[i + 1].name);
                        cardPair.find('#subtitle2').html(data[i + 1].cost + '$');
                        cardPair.find('#desc2').html('Some description');
                        cardPair.find('#btn2').attr('onclick', 'startOptionChange(' + data[i + 1].id + ')');


                    } else {

                    }
                    cardPair.attr('id', '#card-pair' + (i + 2))
                    $('#options-container').append(cardPair);
                }
            }
        })
    })
</script>
<script src="../../../resources/assets/js/jquery.mousewheel.min.js"></script>
<script src="../../../resources/assets/js/jquery.cookie.min.js"></script>
<script src="../../../resources/assets/js/fastclick.min.js"></script>
<script src="../../../resources/assets/js/bootstrap.min.js"></script>
<script src="../../../resources/assets/js/clearmin.min.js"></script>

</body>
</html>


