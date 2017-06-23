<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<li>
	<fmt:formatDate value="${registro.dataPrevista}" pattern="${datePattern}" var="data"/>
	<c:choose>
		<c:when test="${param.readonly eq 'true'}">
			<label>Data Previsão: ${data}</label>
		</c:when>
		<c:otherwise>
			<label>Data Previsão <input type="text" name="dataPrevista" value="${data}"/></label> (${datePatternHint})
		</c:otherwise>
	</c:choose>
</li>