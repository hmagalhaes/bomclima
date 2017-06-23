<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/view/parts/header.jsp"/>
<header>
	<h1>Registro &gt; Incluir</h1>
</header>
	
<section>
	<jsp:include page="/WEB-INF/view/parts/message.jsp"/>
	
	<h2>Selecione o tipo de registro</h2>
	<ul>
		<c:forEach items="${tipoList}" var="tipo">
			<li><a href="<c:url value='/services?op=reginsert&tipoId=${tipo.id}'/>">${tipo.descricao}</a>
		</c:forEach>
	</ul>
	<footer>
		<c:url value="/services?op=reg" var="cancelURL"/>
		<button type="button" onclick="location.href='${cancelURL}'">Cancelar</button>
	</footer>
</section>

<jsp:include page="/WEB-INF/view/parts/footer.jsp"/>