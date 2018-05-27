<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Client home page</title>
    <jsp:include page="../util/stylesheet.jsp"/>
    <script>
        $(document).ready(function () {
            $(function () {
                $.getJSON('/api/contracts/my', function (response) {
                    console.log(response);

                    var table = $('<table id="table-body" class="table table-striped" cellspacing="0" cellpadding="0"/>'),
                        thead = $('<thead/>'),
                        th = $('<th/>'),
                        tbody = $('<tbody/>'),
                        tr = $('<tr/>'),
                        td = $('<td/>');

                    var thisTR = tr.clone();
                    thisTR.append(th.clone().html("Number"));
                    thisTR.append(th.clone().html("Montly payment"));
                    thead.append(thisTR);
                    table.append(thead);
                    $('#table-container').html(table);

                    $('#table-body').dataTable({
                        "data": response,
                        "bInfo": false,
                        "aoColumnDefs": [
                            { "bSearchable": false, "aTargets": [ 1 ] }
                        ],
                        "columns": [
                            {"data": "number"},
                            {"data": "monthlyPayment"}
                        ]
                    })
                });
            });
        })
    </script>

</head>
<body style="background-color: #336b87">
<jsp:include page="client_header.jsp"/>
<div class="container" style="margin-top: 65px">
    <div class="jumbotron">
        <h5>Your contracts:</h5>
        <div id="table-container"></div>
    </div>
</div>
</body>
</html>
