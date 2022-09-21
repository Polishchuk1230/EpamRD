<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" uri="/myTags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <meta charset="UTF-8">
    <title>Registration</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="index.html">Logo</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item active">
                <a class="nav-link" href="index.html">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="reg">Registration <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="items.html">Items</a>
            </li>
        </ul>
    </div>
</nav>

<div class="row">
    <div class="col"></div>
    <div class="col-md-6 col-sm-12">
        <!-- Registration form -->
        <h2>Registration form</h2>
        <form action="#" method="post" id="regform">
            <p id="usercheck" class="px-3 mb-1 bg-warning text-dark">Username is missing</p>
            <p class="input-group">
                <label for="username" class="input-group-text">Username</label>
                <input name="username" id="username" type="text" class="form-control" value="${username}">
            </p>
            <p class="input-group">
                <label for="name" class="input-group-text">Name</label>
                <input name="name" id="name" type="text" class="form-control" value="${name}">
            </p>
            <p class="input-group">
                <label for="surname" class="input-group-text">Surname</label>
                <input name="surname" id="surname" type="text" class="form-control" value="${surname}">
            </p>
            <p id="emailcheck" class="px-3 mb-1 bg-warning text-dark">Email is missing</p>
            <p class="input-group">
                <label for="email" class="input-group-text">Email</label>
                <input name="email" id="email" type="text" class="form-control" value="${email}">
            </p>
            <p id="passcheck" class="px-3 mb-1 bg-warning text-dark">Password is missing</p>
            <p class="input-group">
                <label for="password" class="input-group-text">Password</label>
                <input name="password" id="password" type="text" class="form-control">
            </p>
            <p id="passrepeatcheck" class="px-3 mb-1 bg-warning text-dark">Passwords are different!</p>
            <p class="input-group">
                <label for="password-repeat" class="input-group-text">Password repeat</label>
                <input name="password-repeat" id="password-repeat" type="text" class="form-control">
            </p>
            <p class="form-check form-switch">
              <input class="form-check-input" type="checkbox" id="subscriptionAll" name="subscriptionAll" checked>
              <label class="form-check-label" for="subscriptionAll">All news</label>
            </p>
            <p class="form-check form-switch">
              <input class="form-check-input" type="checkbox" id="subscriptionME" name="subscriptionME" checked>
              <label class="form-check-label" for="subscriptionME">Main events</label>
            </p>
            <p style="display: flex;flex-direction: row-reverse">
                <input type="button" id="submitbtn" value="Register" class="btn btn-lg btn-primary">
            </p>


            <p class="input-group">
                <label for="captcha" class="input-group-text">Captcha</label>
                <input name="captcha" id="captcha" type="text" class="form-control">
                <my:captcha></my:captcha>
            </p>
        </form>

    </div>
    <div class="col"></div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script src="js/plainjs.js"></script>
</body>
</html>