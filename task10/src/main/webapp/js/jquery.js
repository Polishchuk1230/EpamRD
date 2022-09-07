$(document).ready(function () {
    // username
    let usernameIsValid = true;
    let usernameErrorElem = $("#usercheck");
    usernameErrorElem.hide();

    // phone number
    let phoneIsValid = true;
    let phoneErrorElem = $("#phonecheck");
    phoneErrorElem.hide();

    // password
    let passwordIsValid = true;
    let passErrorElem = $("#passcheck");
    passErrorElem.hide();
    let passRepeatErrorElem = $("#passrepeatcheck");
    passRepeatErrorElem.hide();

    // submit button
    $("#submitbtn").click(function () {
        passwordElem = $("#password");
        passwordIsValid = validate(passwordElem.val(), /^\w{4,10}$/, passErrorElem);
        usernameIsValid = validate($("#username").val(), /^[a-zA-Z_]{4,10}$/, usernameErrorElem, "Wrong username");
        phoneIsValid = validate($("#phone-number").val(), /^\+?[\d \-()]{10,18}$/, phoneErrorElem, "Phone number pattern: +12 (345) 678-90-12");

        let checkPassword = (currentPassword) => {
            if ($("#password-repeat").val() != currentPassword) {
                passRepeatErrorElem.show();
                return false;
            }
            passRepeatErrorElem.hide();
            return true;
        };

        passRepeatErrorElem.hide();
        if (usernameIsValid && passwordIsValid && phoneIsValid && checkPassword(passwordElem.val())) {
            $("#regform").submit();
        }
    });
});

// function to validate html-element value with regex
function validate(value, regex, errorElem, errorMessage) {
    if (!value) {
        errorElem.html("Value is missing.");
        errorElem.show();
        return false;
    }

    if (!regex.test(value)) {
        errorElem.html(errorMessage ? errorMessage : "Value is wrong");
        errorElem.show();
        return false;
    }

    errorElem.hide();
    return true;
}