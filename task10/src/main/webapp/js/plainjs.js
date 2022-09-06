let button = document.getElementById("submitbtn");

button.onclick = function () {
    validateUsername();
    validatePassword();
    validatePhoneNumber();

    if (usernameIsValid && passwordIsValid && phoneIsValid) {
        console.log("send a request");
    }
}

// validate username
let usernameIsValid = true;
document.getElementById("usercheck").setAttribute("hidden", "hidden");
function validateUsername() {
    let usernameInput = document.getElementById("username");
    let usercheck = document.getElementById("usercheck");

    if (usernameInput.value == "") {
        usercheck.innerText = "Username is missing.";
        usercheck.removeAttribute("hidden");
        usernameIsValid = false;
    } else if (usernameInput.value.length < 4 || usernameInput.value.length > 10) {
        usercheck.innerText = "Username should be from 4 to 10 characters.";
        usercheck.removeAttribute("hidden");
        usernameIsValid = false;
    } else {
        usercheck.setAttribute("hidden", "hidden");
        usernameIsValid = true;
    }
}

// validate password
let passwordIsValid = true;
document.getElementById("passcheck").setAttribute("hidden", "hidden");
function validatePassword() {
    let passwordInput = document.getElementById("password");
    let passheck = document.getElementById("passcheck");

    if (passwordInput.value == "") {
        passheck.innerText = "Password is missing.";
        passheck.removeAttribute("hidden");
        passwordIsValid = false;
    } else if (passwordInput.value.length < 4 || passwordInput.value.length > 10) {
        passheck.innerText = "Password should be from 4 to 10 characters.";
        passheck.removeAttribute("hidden");
        passwordIsValid = false;
    } else {
        passheck.setAttribute("hidden", "hidden");
        passwordIsValid = true;
    }
}

// validate password
let phoneIsValid = true;
document.getElementById("phonecheck").setAttribute("hidden", "hidden");
function validatePhoneNumber() {
    let regex = /^\+?[\d \-()]{10,18}$/;
    let passwordInput = document.getElementById("phone-number");
    let phonecheck = document.getElementById("phonecheck");

    if (!regex.test(passwordInput.value)) {
        phonecheck.innerText = "Phone number pattern: +12 (345) 678-90-12";
        phonecheck.removeAttribute("hidden");
        phoneIsValid = false;
    } else {
        phonecheck.setAttribute("hidden", "hidden");
        phoneIsValid = true;
    }
}