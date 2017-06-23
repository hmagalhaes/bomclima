<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<li>
	<fmt:formatNumber value="${registro.temperaturaEmCelcius}" var="temperatura"/>
	<label>Temperatura em Celcius:
		<c:choose>
			<c:when test="${param.readonly eq 'true'}">
				${temperatura}
			</c:when>
			<c:otherwise>
				<input type="text" name="temperaturaEmCelcius" value="${temperatura}"/>
			</c:otherwise>
		</c:choose>	
	</label>
</li>