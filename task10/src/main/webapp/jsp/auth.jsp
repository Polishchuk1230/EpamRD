<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:choose>
    <c:when test="${empty sessionScope.user}">

        <form action="${pageContext.request.contextPath}/auth" method="post" id="authform">
            <p class="input-group">
                <label for="username" class="input-group-text"><fmt:message key="auth.username"/></label>
                <input name="username" id="usernameauth" type="text" class="form-control">
            </p>
            <p class="input-group">
                <label for="password" class="input-group-text"><fmt:message key="auth.password"/></label>
                <input name="password" id="passwordauth" type="password" class="form-control">
            </p>
            <p style="display: flex;flex-direction: row-reverse">
                <fmt:message key="auth.log.in" var="fmtLogin"/>
                <input type="submit" class="btn btn-lg btn-primary" value="${fmtLogin}"></input>
            </p>
        </form>

    </c:when>
    <c:when test="${not empty sessionScope.user}">
        <c:if test="${not empty user.avatar}">
            <img src="${pageContext.request.contextPath}/img?file=${user.avatar}">
        </c:if>
        <form action="${pageContext.request.contextPath}/logout" method="get" id="logoutform">
            <fmt:message key="auth.log.out" var="fmtLogout"/>
            <input type="submit" value="${fmtLogout}" class="btn btn-lg btn-primary">
        </form>
    </c:when>
</c:choose>