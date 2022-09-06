$(document).ready(function () {
    // vlidate username
    let usernameIsValid = true;
    $("#usercheck").hide();

    $("#username").keyup(function () {
        usernameIsValid = validateUsername();
    });

    function validateUsername() {
        let usernameValue = $("#username").val();
        if (usernameValue == "") {
            $("#usercheck").html("Username is missing.");
            $("#usercheck").show();
            return false;
        } else if (usernameValue.length < 4 || usernameValue.length > 10) {
            $("#usercheck").html("Username should be from 4 to 10 characters.");
            $("#usercheck").show();
            return false;
        } else {
            $("#usercheck").hide();
            return true;
        }
    }

    // validate password
    let passwordIsValid = true;
    $("#passcheck").hide();

    $("#password").keyup(function () {
        passwordIsValid = validatePassword();
    });

    function validatePassword() {
        let passwordValue = $("#password").val();
        if (passwordValue == "") {
            $("#passcheck").html("Password is missing.");
            $("#passcheck").show();
            return false;
        } else if (passwordValue.length < 4 || passwordValue.length > 10) {
            $("#passcheck").html("Password should be from 4 to 10 characters.");
            $("#passcheck").show();
            return false;
        } else {
            $("#passcheck").hide();
            return true;
        }
    }

    // validate phone number
    let phoneIsValid = true;
    $("#phonecheck").hide();

    $("#phone-number").keyup(function () {
        phoneIsValid = validatePhoneNumber();
    });

    function validatePhoneNumber() {
        let regex = /^\+?[\d \-()]{10,18}$/;
        let phoneValue = $("#phone-number").val();
        if (!regex.test(phoneValue)) {
            $("#phonecheck").html("Phone number pattern: +12 (345) 678-90-12");
            $("#phonecheck").show();
            return false;
        } else {
            $("#phonecheck").hide();
            return true;
        }
    }

    // submit button
    $("submitbtn").click(function () {
        usernameIsValid = validateUsername();
        passwordIsValid = validatePassword();
        phoneIsValid = validatePhoneNumber();

        if (usernameIsValid && passwordIsValid && phoneIsValid) {
            $("#regform").submit();
        }
    });
});