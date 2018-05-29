<script type="text/javascript">
    function submitNewContract() {
        var object = {};
        var myForm = document.getElementById('new_contract');
        var formData = new FormData(myForm);
        formData.forEach(function (value, key) {
            object[key] = value;
        });
        var tariffDTO = {};
        tariffDTO.id = $('#tariffs').val();
        object['tariffDTO'] = tariffDTO;
        var json = JSON.stringify(object);
        console.log(json);
        $.ajax({
            type: "POST",
            url: "/api/contracts",
            contentType: "application/json",
            data: json
        }).done(function (result) {
            myForm.reset();
            $.sweetModal({
                content: result,
                icon: $.sweetModal.ICON_SUCCESS
            })
        }).fail(function (result) {
            console.log(result.responseText);
            $.sweetModal({
                content: result.responseText,
                icon: $.sweetModal.ICON_ERROR
            })
        });

        return false;


    }
</script>

<script type="text/javascript">
    function initNewContractForm() {
        $.getJSON('/api/tariffs/active', function (data) {
            $('#tariffs').empty();
            $(data).each(function () {
                var tariffOption = "<option value=\"" + this.id + "\">" + this.name + "</option>";
                $('#tariffs').append(tariffOption);
            })
        })
    }
</script>

<form name="New contract" id="new_contract" onsubmit="return submitNewContract()">
    <div class="form-group">
        <label for="ownersPassport">Client's passport</label>
        <input class="form-control" id="ownersPassport" name="ownersPassport" placeholder="1234567890" type="text">
    </div>

    <div class="form-group">
        <label for="number">Phone number</label>
        <input class="form-control" id="number" name="number" placeholder="+7(900)9001234" type="tel">
    </div>
    <div class="form-group">
        <label for="tariffs">Tariff</label>
        <select class="form-control" id="tariffs">
        </select>
        <button style="margin-top: 20px" type="submit" class="btn btn-primary">Submit</button>
    </div>

</form>
