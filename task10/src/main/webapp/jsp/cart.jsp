<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" uri="/myTags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <meta charset="UTF-8">
    <title>Items</title>
</head>
<body>
<!-- Navigation bar -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="index.html">Logo</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item active">
                <a class="nav-link" href="index.jsp">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="reg">Registration</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="product">Items</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="cart">Cart <span class="sr-only">(current)</span></a>
            </li>
        </ul>
    </div>
</nav>

<div style="width: 300px; height: 200px;">
    <jsp:include page="/jsp/auth.jsp" />
</div>


<!-- content section -->
<div class="mt-3">
    <c:forEach var="product" items="${cart.keySet()}">
        <div class="card">
            <h5 class="card-header">${product.name}</h5>
            <div class="card-body">
            <c:if test="${not empty product.image}">
            	<img src="img?file=${product.image}" />
            </c:if>
                <h5 class="card-title">(${product.category}) ${product.supplier.name}</h5>
                <p class="card-text">${product.description}</p>

                <a href="#" class="btn btn-primary" onclick="setNewNumber({ productId: ${product.id}, newNumber: Number(prompt('How many?')) })">${product.price}$ x ${cart.get(product)}</a>
                <a href="#" class="btn btn-danger" onclick="removeFromCart(${product.id})">Remove</a>
            </div>

        </div>
    </c:forEach>
</div>
<c:choose>
    <c:when test="${not empty cart.keySet()}">
       <a href="#" class="btn btn-success" onclick="makeOrder()">Make order ${fullPrice}$</a>
    </c:when>
    <c:otherwise>
       Your cart is empty.
    </c:otherwise>
</c:choose>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script src="js/jsonRequests.js"></script>
</body>
</html>