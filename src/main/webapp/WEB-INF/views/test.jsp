<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Testing rest</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        $( document ).ready(function() {

            $(function(){
                $.getJSON('/api/options', function(response) {
                    console.log(response);
                    //Define & initialize vars
                    var table = $('<table cellspacing="0" cellpadding="3" border="1"/>'),
                        tbody = $('<tbody/>'),
                        tr = $('<tr/>'),
                        td = $('<td/>');
                    //create rows in tbody
                    $.each(response, function(row, data) {
                        var thisTR = tr.clone();
                        console.log(row);
                        console.log(data);
                        thisTR.append(td.clone().html(data.id));
                        thisTR.append(td.clone().html(data.name));
                        thisTR.append(td.clone().html(data.cost));
                        thisTR.append(td.clone().html(data.accessCost));

                        tbody.append( thisTR );
                    });
                    //append tbody to table
                    table.append( tbody );
                    //append table to DOM
                    $('#table').html( table );
                });
            });


        });
    </script>
</head>
<body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<div id="table"></div>
</body>
</html>
