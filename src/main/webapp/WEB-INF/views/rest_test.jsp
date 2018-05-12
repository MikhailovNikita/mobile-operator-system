<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Testing rest</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        $( document ).ready(function() {
            var json = {
                "content": {
                    "title": "Schema",
                    "cellValues": [
                        [
                            "c1",
                            "count"
                        ],
                        [
                            "DoubleType",
                            "LongType"
                        ]
                    ],
                    "id": 3,
                    "name": "Histogram",
                    "type": "table"
                }
            };
            //Define & initialize vars
            var table = $('<table cellspacing="0" cellpadding="3" border="1"/>'),
                tbody = $('<tbody/>'),
                tr = $('<tr/>'),
                td = $('<td/>');
            //create rows in tbody
            $.each(json.content.cellValues, function(i,v) {
                var thisTR = tr.clone();
                $.each(v, function(j,u) {
                    thisTR.append( td.clone().html( u ) );
                });
                tbody.append( thisTR );
            });
            //append tbody to table
            table.append( tbody );
            //append table to DOM
            $('#table').html( table );
        });
    </script>
</head>
<body>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <div id="table"></div>
</body>
</html>
