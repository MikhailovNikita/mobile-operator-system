<script>
    //loading options for select lists
    $(document).ready(function () {
        $.getJSON('/api/options', function (data) {
            $(data).each(function () {
                var supportedOptions = "<option value=\"" + this.id + "\">" + this.name + "</option>";
                $('#options1').append(supportedOptions);
                $('#options2').append(supportedOptions);
            })
        })
    })

    //submitting new rule
    function submitNewRule() {
        var type = $('#forbid_radio').is(':checked') ? 'forbid' : 'require';
        $.ajax({
            type: "PUT",
            url: "/api/options/" + $('#options1').val() + "/" + type + "/" + $('#options2').val()
        }).done(function (result) {
            $.sweetModal({
                content: result,
                icon: $.sweetModal.ICON_SUCCESS
            })
        }).fail(function (result) {
            $.sweetModal({
                content: result.responseText,
                icon: $.sweetModal.ICON_ERROR
            })
        });

        return false;
    }
</script>

<form name="New rule" id="new_rule" onsubmit="return submitNewRule()">
        <select id="options1">
        </select>

        <select id="options2">
        </select>

    <p><input id="forbid_radio" name="type" type="radio" value="forbid" checked="checked">Forbid</p>
    <p><input id="require_radio" name="type" type="radio" value="require">Require</p>
    <input class="btn btn-primary" type="submit">
</form>

