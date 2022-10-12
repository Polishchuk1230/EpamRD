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
                <a class="nav-link" href="product">Items <span class="sr-only">(current)</span></a>
            </li>
        </ul>
    </div>
</nav>

<div style="width: 300px; height: 200px;">
    <jsp:include page="/jsp/auth.jsp" />
</div>


<!-- filter section -->
<div class="row m-3">
    <form action="product" method="get" class="col-6 form-inline">
    	<div class="input-group mb-3">
            <label for="days-per-page" class="input-group-text">Items per page</label>
            <input name="items-per-page" type="number" min="1" max="20" id="days-per-page" class="form-control">
        </div>
        
        <div class="input-group mb-3">
  			<span class="input-group-text" id="filterByLabel">Name</span>
  			<input name="name" type="text" class="form-control" aria-label="Filter by name" aria-describedby="filterByLabel">
		</div>
    
    	<div class="input-group">
    		<div class="form-check">
    			<label for="minPrice" class="form-label">Min price <span>0</span></label>
				<input type="range" class="form-range" id="minPrice" name="minPrice" min="0" max="100" value="0" onchange="document.querySelector('label[for=minPrice] > span').innerText=this.value">
    		</div>
    		<div class="form-check">
    			<label for="maxPrice" class="form-label">Max price <span>0</span></label>
				<input type="range" class="form-range" id="maxPrice" name="maxPrice" min="0" max="100" value="0" onchange="document.querySelector('label[for=maxPrice] > span').innerText=this.value">
    		</div>
    	</div>
    	
    	<div>
    		<h6>Categories: </h6>
    		<div class="form-check" style="display: inline-block;">
            	<input name="category" value="DVD" class="form-check-input" type="checkbox">
            	<label class="form-check-label" for="gridCheck1">
            	    DVD
            	</label>
        	</div>
        	<div class="form-check" style="display: inline-block;">
        	    <input name="category" value="CD" class="form-check-input" type="checkbox">
        	    <label class="form-check-label" for="gridCheck1">
        	        CD
        	    </label>
        	</div>
    	</div>
        
        <div>
        	<h6>Sorting:</h6>
        	<select class="form-select form-select-sm" aria-label=".form-select-sm example" name="sort">
 				<option value="price">Price</option>
 				<option value="name">Name</option>
			</select>
			<div class="form-check form-switch">
  				<input class="form-check-input" type="checkbox" id="reverse" name="reverse" value="true">
  				<label class="form-check-label" for="reverse">Reverse sorting</label>
  			</div>
        </div>
        
        <button type="submit" class="btn btn-primary my-1">Filter</button>
    </form>
</div>

<!-- content section -->
<div class="mt-3">
    <c:forEach var="product" items="${products}">
        <div class="card">
            <h5 class="card-header">${product.name}</h5>
            <div class="card-body">
            <c:if test="${not empty product.image}">
            	<img src="img?file=${product.image}" />
            </c:if>
                <h5 class="card-title">(${product.category}) ${product.supplier.name}</h5>
                <p class="card-text">${product.description}</p>
                <a href="#" class="btn btn-primary">Buy ${product.price} $</a>
            </div>
        </div>
    </c:forEach>
</div>

<c:set var="page" value="${pageDto.currentPage < 1 ? 1 : pageDto.currentPage}" />
<c:set var="minpage" value="${page - 1 > 0 ? page - 1 : 1}" />

<nav aria-label="page-navigation">
  <ul class="pagination pagination-lg">
      <c:forEach var="i" begin="${minpage}" end="${page + 1}" step="1">
          <c:choose>
              <c:when test="${i == page or i > pageDto.totalPages}">
                  <li class="page-item disabled">
                      <a class="page-link" href="#" tabindex="-1">${i}</a>
                  </li>
              </c:when>
              <c:otherwise>
                  <li class="page-item"><a class="page-link" href="${pageDto.urlPrefix}&page=${i}">${i}</a></li>
              </c:otherwise>
          </c:choose>
      </c:forEach>
  </ul>
</nav>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>