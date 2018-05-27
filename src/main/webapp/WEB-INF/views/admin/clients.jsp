<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="../util/stylesheet.jsp"/>
    <script>
        $(document).ready(function () {

            $(function () {
                $.getJSON('/api/contracts', function (response) {
                    console.log(response);
                    //Define & initialize vars
                    var table = $('<table id="table-body" class="table table-striped table-bordered" cellspacing="0" cellpadding="3" border="1"/>'),
                        thead = $('<thead/>'),
                        th = $('<th/>')
                    tbody = $('<tbody/>'),
                        tr = $('<tr/>'),
                        td = $('<td/>');
                    //create header
                    var thisTR = tr.clone();
                    thisTR.append(th.clone().html("Phone number"));
                    thisTR.append(th.clone().html("Tariff"));
                    thisTR.append(th.clone().html("Creation date"));
                    thisTR.append(th.clone().html("Status"));
                    thead.append(thisTR);
                    table.append(thead);
                    // //create rows in tbody
                    // $.each(response, function(row, data) {
                    //     thisTR.append(td.clone().html(data.name));
                    //     thisTR.append(td.clone().html(data.email));
                    //     thisTR.append(td.clone().html(data.address));
                    //     thisTR.append(td.clone().html(data.birthDate));
                    //
                    //     tbody.append( thisTR );
                    // });
                    //append tbody to table
                    table.append(tbody);
                    //append table to DOM
                    $('#table').html(table);

                    $('#table-body').DataTable({
                        "data": response,
                        "bInfo": false,
                        "aoColumnDefs": [
                            {"bSearchable": false, "aTargets": [1]}
                        ],
                        "columns": [
                            {"data": "number"},
                            {"data": "monthlyPayment"}
                        ]
                    });
                });
            });


        });
    </script>
</head>
<body>
<%--<jsp:include page="admin_header.jsp"/>--%>

<div id="table"></div>

</body>
</html>
