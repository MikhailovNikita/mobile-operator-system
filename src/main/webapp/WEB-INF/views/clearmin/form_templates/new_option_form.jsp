<meta charset="utf-8"/>
<script type="text/javascript">
    function submitNewOption() {
        var object = {};
        var myForm = document.getElementById('new_option');
        var formData = new FormData(myForm);
        formData.forEach(function (value, key) {
            object[key] = value;
        });
        var json = JSON.stringify(object);
        console.log(json);
        $.ajax({
            type: "POST",
            url: "/api/options",
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

<form name="New supportedOptions" id="new_option" onsubmit="return submitNewOption()">
    <div class="form-group">
        <label for="name">Title</label>
        <input select class="form-control" id="name" name="name" type="text">
    </div>
    <div class="form-group">
        <label for="cost">Cost</label>
        <input select class="form-control" id="cost" name="cost" type="number">
    </div>
    <div class="form-group">
        <label for="accessCost">Access cost</label>
        <input select class="form-control" id="accessCost" name="accessCost" type="number">
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>