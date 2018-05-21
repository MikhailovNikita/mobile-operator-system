<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Testing rest</title>
    <jsp:include page="stylesheet.jsp"/>
    <script>
        $(document).ready(function() {

            $(function(){
                $.getJSON('/api/options', function(response) {
                    console.log(response);
                    //Define & initialize vars
                    var table = $('<table id="table-body" class="table table-striped table-bordered" cellspacing="0" cellpadding="3" border="1"/>'),
                        thead = $('<thead/>'),
                        th = $('<th/>'),
                        tbody = $('<tbody/>'),
                        tr = $('<tr/>'),
                        td = $('<td/>');

                    //create header
                    var thisTR = tr.clone();
                    thisTR.append(th.clone().html("ID"));
                    thisTR.append(th.clone().html("Name"));
                    thisTR.append(th.clone().html("Cost"));
                    thisTR.append(th.clone().html("Access cost"));
                    thead.append(thisTR);
                    table.append(thead);
                    $('#table').html( table );

                    $('#table-body').DataTable({
                        "data": response,
                        "columns" : [
                            { "data" : "id" },
                            { "data" : "name" },
                            { "data" : "cost" },
                            { "data" : "accessCost" }
                        ]
                    })
                });
            });


        });

    </script>
</head>
<body>
Hello!
<div id="table"></div>
</body>
</html>
