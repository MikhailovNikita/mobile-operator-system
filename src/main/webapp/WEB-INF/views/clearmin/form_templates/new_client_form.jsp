
<form name="New client" id="new_client" onsubmit="return submitNewUser()">
    <div class="row">
            <div class="form-group col-xs-6">
                <label for="name">First name</label>
                <input id="name" class="form-control" name="name" type="text" placeholder="John" required>
            </div>
            <div class="form-group col-xs-6">
                <label for="lastName">Last name</label>
                <input id="lastName" class="form-control" name="lastName" placeholder="Smith" type="text" required>
            </div>

    </div>

    <div class="row">
        <div class="form-group col-xs-6">
            <label for="email">Email</label>
            <input id="email" class="form-control" name="email" type="email" placeholder="johnsmith@gmail.com" required>
        </div>
        <div class="form-group col-xs-6">
            <label for="passport">Passport number</label>
            <input id="passport" class="form-control" name="passport" placeholder="1234567890" type="text" required>
        </div>
    </div>
    <div class="row">
        <div class="form-group col-xs-6">
            <label for="address">Address</label>
            <input id="address" class="form-control" name="address" placeholder="Russia, Moscow" type="text" required>
        </div>
        <div class="form-group col-xs-6">
            <label for="birthDate">Birth date</label>
            <input id="birthDate" class="form-control" name="birthDate" type="date" required>
        </div>
    </div>


    <button type="submit" class="btn btn-primary">Submit</button>

    <script type="text/javascript">
        function submitNewUser() {
            var object = {};
            var myForm = document.getElementById('new_client');
            var formData = new FormData(myForm);
            formData.forEach(function (value, key) {
                object[key] = value;
            });
            if(!validateAndResponse(object)) return false;
            var json = JSON.stringify(object);
            console.log(json);
            $.ajax({
                type: "POST",
                url: "/api/clients",
                contentType: "application/json",
                // headers: headers,
                data: json
            }).done(function (result) {
                myForm.reset();
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

        //validates input and tells user if something's wrong
        function validateAndResponse(client){
            if(!(/^[a-zA-Z]+$/.test(client.name))){

                $.sweetModal({
                    content: 'Name must contain only english letters',
                    icon: $.sweetModal.ICON_WARNING
                });
                return false;
            }

            if(!(/^[a-zA-Z]+$/.test(client.lastName))){

                $.sweetModal({
                    content: 'Last name must contain only english letters',
                    icon: $.sweetModal.ICON_WARNING
                });
                return false;
            }

            var passportRegexp = new RegExp('^[0-9]+$');
            if(!passportRegexp.test(client.passport) || client.passport.length !== 10){
                $.sweetModal({
                    content: 'Passport number is a 10-digit number without spaces',
                    icon: $.sweetModal.ICON_WARNING
                });
                return false;
            }

            if(client.address.length < 5){
                $.sweetModal({
                    content: 'Address is not correct',
                    icon: $.sweetModal.ICON_WARNING
                });
                return false;
            }


            if(client.birthDate === null){
                $.sweetModal({
                    content: 'Please, set a birth date',
                    icon: $.sweetModal.ICON_WARNING
                });
                return false;
            }

            return true;
        }
    </script>

</form>
