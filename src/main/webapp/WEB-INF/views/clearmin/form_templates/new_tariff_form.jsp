<script type="text/javascript">
    function initNewTariffForm() {
        $('#checkboxList').empty();
        $('#checkboxList').append('<label>Supported options:</label><br>')

        $.getJSON('/api/options', function (data) {
            $(data).each(function () {
                var tariffOption = "<input id=\"supportedOptions\" type=\"checkbox\" value=\"" + this.id + "\"  name=\"supportedOptions\">" + this.name + "</supportedOptions>";
                $('#checkboxList').append(tariffOption);
                $('#checkboxList').append('<br>');
            });
            $('#checkboxList').append(" <button type=\"submit\" class=\"btn btn-primary\">Submit</button>");
        })
    }
</script>

<script type="text/javascript">
    function submitNewTariff() {
        var object = {};
        object.optionIds = [];
        var myForm = document.getElementById('new_tariff');
        var formData = new FormData(myForm);
        formData.forEach(function (value, key) {
            if (key === 'supportedOptions') {
                object.optionIds.push(value);
            } else {
                object[key] = value;
            }

        });
        var json = JSON.stringify(object);
        console.log(json);
        $.ajax({
            type: "POST",
            url: "/api/tariffs",
            contentType: "application/json",
            data: json
        }).done(function (result) {
            myForm.reset();
            $.sweetModal({
                content: result,
                icon: $.sweetModal.ICON_SUCCESS
            })
        }).fail(function (result) {
            console.log(result);
            $.sweetModal({
                content: result.responseText,
                icon: $.sweetModal.ICON_ERROR
            })
        });

        return false;


    }
</script>

<form name="New tariff" id="new_tariff" onsubmit="return submitNewTariff()">
    <div class="form-group">
        <label for="name">Title</label>
        <input class="form-control" id="name" name="name" type="text">
    </div>
    <div class="form-group">
        <label for="cost">Cost</label>
        <input class="form-control" id="cost" name="cost" type="number">
    </div>
    <div class="form-check" id="checkboxList">

    </div>
</form>
