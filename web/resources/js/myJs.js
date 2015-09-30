/**
 * Created by Владислав on 09.08.2015.
 */

$(document).ready(function () {
    var editMarkButtonId = "editUserMark";
    var endDate = document.getElementById("endDate");

    $('input[name="inputDate"]').daterangepicker({
        singleDatePicker: true,
        showDropdowns: true,
        locale: {
            format: 'DD.MM.YYYY'
        },
        startDate: moment()
    });

    $('input[name="duration"]').daterangepicker({

        locale: {
            format: 'DD.MM.YYYY'
        },

        startDate: moment(),
        endDate: moment().add(120, 'days')
    });
});

var _validFileExtensions = [".jpg", ".jpeg", ".bmp", ".gif", ".png"];

function validatePhoto() {
    var input = document.getElementById("photo");
    if (input.type == "file") {
        var sFileName = input.value;
        if (sFileName.length > 0) {
            var blnValid = false;
            for (var j = 0; j < _validFileExtensions.length; j++) {
                var sCurExtension = _validFileExtensions[j];
                if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
                    input.className = "btn btn-default";
                    document.getElementById("inputPhoto").className = "form-group";
                    blnValid = true;
                    break;
                }
            }
            if (!blnValid) {
                input.className = "btn btn-danger";
                document.getElementById("inputPhoto").className = "form-group has-error";
                return false;
            }
        }
    }
    return true;
}

function validate() {

    var firstName = document.registerForm.firstName.value;
    var secondName = document.registerForm.secondName.value;
    var middleName = document.registerForm.middleName.value;
    var login = document.registerForm.login.value;
    var password = document.registerForm.password.value;
    var phone = document.registerForm.phone.value;
    var email = document.registerForm.email.value;
    var card = document.registerForm.card.value;
    var photo = document.registerForm.photo;

    //first name validation
    if (firstName == "") {
        var firstName = document.getElementById("firstName").className = "form-group has-error";
        document.registerForm.firstName.focus();
        return false;
    } else {
        var regex = /^[A-ZА-Я][a-zа-яA-ZА-Я-]{2,30}$/;
        if (!regex.test(firstName)) {
            document.getElementById("firstName").className = "form-group has-error";
            return false;
        }
        document.getElementById("firstName").className = "form-group";
    }

    //second name validation
    if (secondName == "") {
        document.getElementById("secondName").className = "form-group has-error"
        document.registerForm.secondName.focus();
        return false;
    } else {
        var regex = /^[A-ZА-Я][a-zа-яA-ZА-Я-]{2,30}$/;
        if (!regex.test(secondName)) {
            document.getElementById("secondName").className = "form-group has-error";
            return false;
        }
        document.getElementById("secondName").className = "form-group";
    }

    //middle name validation
    if (middleName != "") {
        var regex = /^[A-ZА-Я][a-zа-яA-ZА-Я-]{2,30}$/;
        if (!regex.test(middleName)) {
            document.getElementById("middleName").className = "form-group has-error";
            return false;
        }
    }

    //login validation
    if (login == "") {
        document.getElementById("login").className = "form-group has-error"
        document.registerForm.login.focus();
        return false;
    } else {
        var regex = /^[a-zA-Z0-9_-]{3,16}$/;
        if (!regex.test(login)) {
            document.getElementById("login").className = "form-group has-error";
            return false;
        }
        document.getElementById("login").className = "form-group";
    }

    //password validation
    if (password == "") {
        document.getElementById("password").className = "form-group has-error"
        document.registerForm.password.focus();
        return false;
    } else {
        var regex = /^\w{3,14}$/;
        if (!regex.test(password)) {
            document.getElementById("password").className = "form-group has-error";
            return false;
        }
        document.getElementById("password").className = "form-group";
    }

    //email validation
    if (email == "") {
        document.getElementById("email").className = "form-group has-error"
        document.registerForm.email.focus();
        return false;
    } else {
        var regex = /^\w.+@\w+[.][a-z]+$/;
        if (!regex.test(email)) {
            document.getElementById("email").className = "form-group has-error";
            return false;
        }
        document.getElementById("email").className = "form-group";
    }

    //email validation
    if (phone == "") {
        document.getElementById("phone").className = "form-group has-error"
        document.registerForm.phone.focus();
        return false;
    } else {
        var regex = /^(\+38)?[0](50|63|66|67|68|91|92|93|94|95|96|97|98|99)\d{7}$/;
        if (!regex.test(phone)) {
            document.getElementById("phone").className = "form-group has-error";
            return false;
        }
        document.getElementById("phone").className = "form-group";
    }

    //email validation
    if (card == "") {
        document.getElementById("card").className = "form-group has-error"
        document.registerForm.card.focus();
        return false;
    } else {
        var regex = /^[А-ЯA-Z]{2}\d{8}$/;
        if (!regex.test(card)) {
            document.getElementById("card").className = "form-group has-error";
            return false;
        }
        document.getElementById("card").className = "form-group";
    }

    //email validation
    if (photo.type == "file") {
        var sFileName = photo.value;
        if (sFileName.length > 0) {
            var blnValid = false;
            for (var j = 0; j < _validFileExtensions.length; j++) {
                var sCurExtension = _validFileExtensions[j];
                if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
                    photo.className = "btn btn-default";
                    document.getElementById("inputPhoto").className = "form-group";
                    blnValid = true;
                    break;
                }
            }
            if (!blnValid) {
                photo.className = "btn btn-danger";
                document.getElementById("inputPhoto").className = "form-group has-error";
                return false;
            }
        }
    }

    return true;
}