<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty error}">
	<p class="error">${error}</p>
</c:if>
<c:if test="${not empty param.error}">
	<p class="error">${param.error}</p>
</c:if>
<c:if test="${not empty success}">
	<p class="success">${success}</p>
</c:if>
<c:if test="${not empty param.success}">
	<p class="success">${param.success}</p>
</c:if>