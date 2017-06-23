<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<li>
	<fmt:formatNumber value="${registro.probabilidadePercentual}" var="probabilidade"/>
	<c:choose>
		<c:when test="${param.readonly eq 'true'}">
			<label>Probabilidade Previsão: ${probabilidade}%</label>
		</c:when>
		<c:otherwise>
			<label>Probabilidade Previsão: <input type="text" name="probabilidadePercentual" value="${probabilidade}"/>%</label>
		</c:otherwise>
	</c:choose>
</li>
