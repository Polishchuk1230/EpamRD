<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${empty sessionScope.user}">

        <form action="${pageContext.request.contextPath}/auth" method="post" id="authform">
            <p class="input-group">
                <label for="username" class="input-group-text">Username</label>
                <input name="username" id="usernameauth" type="text" class="form-control">
            </p>
            <p class="input-group">
                <label for="password" class="input-group-text">Password</label>
                <input name="password" id="passwordauth" type="password" class="form-control">
            </p>
            <p style="display: flex;flex-direction: row-reverse">
                <input type="submit" value="Log In" class="btn btn-lg btn-primary">
            </p>
        </form>

    </c:when>
    <c:when test="${not empty sessionScope.user}">
        <c:if test="${not empty user.avatar}">
            <img src="${pageContext.request.contextPath}/img?file=${user.avatar}">
        </c:if>
        <form action="${pageContext.request.contextPath}/logout" method="get" id="logoutform">
            <input type="submit" value="Logout" class="btn btn-lg btn-primary">
        </form>
    </c:when>
</c:choose>