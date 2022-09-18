// username
let usernameIsValid = true;
let usernameErrorElem = document.getElementById("usercheck");
hide(usernameErrorElem);

// email
let emailIsValid = true;
let emailErrorElem = document.getElementById("emailcheck");
hide(emailErrorElem);

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
    usernameIsValid = validate(usernameElem.value, /^\w{4,10}$/, usernameErrorElem, "Wrong username");

    let emailElem = document.getElementById("email");
    emailIsValid = validate(emailElem.value, /^\w+@[\w.]+.[a-zA-Z]{1,3}$/, emailErrorElem, "Email pattern: Your_email@xxxxxx.yyy");

    let checkPassword = (currentPassword) => {
        if (document.getElementById("password-repeat").value != currentPassword) {
            show(passRepeatErrorElem);
            return false;
        }
        hide(passRepeatErrorElem);
        return true;
    };

    hide(passRepeatErrorElem);
    if (usernameIsValid && passwordIsValid && emailIsValid && checkPassword(passwordElem.value)) {
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