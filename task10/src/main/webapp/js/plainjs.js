// username
let usernameIsValid = true;
let usernameErrorElem = document.getElementById("usercheck");
hide(usernameErrorElem);

// phone number
let phoneIsValid = true;
let phoneErrorElem = document.getElementById("phonecheck");
hide(phoneErrorElem);

// password
let passwordIsValid = true;
let passErrorElem = document.getElementById("passcheck");
hide(passErrorElem);
let passRepeatErrorElem = document.getElementById("passrepeatcheck");
hide(passRepeatErrorElem);

// process the submit button's clicks
let button = document.getElementById("submitbtn");
button.onclick = function () {
    let passwordElem = document.getElementById("password");
    passwordIsValid = validate(passwordElem.value, /^\w{4,10}$/, passErrorElem);

    let usernameElem = document.getElementById("username");
    usernameIsValid = validate(usernameElem.value, /^[a-zA-Z_]{4,10}$/, usernameErrorElem, "Wrong username");

    let phoneElem = document.getElementById("phone-number");
    phoneIsValid = validate(phoneElem.value, /^\+?[\d \-()]{10,18}$/, phoneErrorElem, "Phone number pattern: +12 (345) 678-90-12");

    let checkPassword = (currentPassword) => {
        if (document.getElementById("password-repeat").value != currentPassword) {
            show(passRepeatErrorElem);
            return false;
        }
        hide(passRepeatErrorElem);
        return true;
    };

    hide(passRepeatErrorElem);
    if (usernameIsValid && passwordIsValid && phoneIsValid && checkPassword(passwordElem.value)) {
        document.getElementById("regform").submit();
    }
}

// function to validate html-element value with regex
function validate(value, regex, errorElem, errorMessage) {
console.log(value);
    if (!value) {
        errorElem.innerText = "Value is missing.";
        show(errorElem);
        return false;
    }

    if (!regex.test(value)) {
        errorElem.innerText = errorMessage ? errorMessage : "Value is wrong.";
        show(errorElem);
        return false;
    }

    hide(errorElem);
    return true;
}

function hide(elem) {
    elem.setAttribute("hidden", "hidden");
}

function show(elem) {
    elem.removeAttribute("hidden");
}