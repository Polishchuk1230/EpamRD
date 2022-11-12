<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<select name="locale" id="cars" onchange="changeLocale(this.value)">
  <c:forEach var="tempLocale" items="${pageContext.request.locales}">
    <option value="${tempLocale}" ${pageContext.request.locale eq tempLocale ? "selected" : ""}>${tempLocale}</option>
  </c:forEach>
</select>

<script>
function changeLocale(locale) {
  window.open("${pageContext.request.contextPath}?locale=" + locale, "_self");
}
</script>