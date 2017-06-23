<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/view/parts/header.jsp"/>
<header>
	<h1>Registro &gt; Editar &gt; ${registro.tipoRegistro.descricao}</h1>
</header>
	
<section>
	<jsp:include page="/WEB-INF/view/parts/message.jsp"/>

	<c:url value="/services?op=regedit&id=${registro.id}" var="formAction"/>
	<form method="post" action="${formAction}">
		<ul>
			<li>
				<label>ID ${registro.id}</label>
			</li>
			<li>
				<label>UF <input type="text" name="uf" value="${registro.cidade.uf.uf}" id="uf"/></label>
			</li>
			<li>
				<label>Cidade <input type="text" name="cidade" value="${registro.cidade.cidade}"/></label>
			</li>
			<c:choose>
				<c:when test="${registro.tipoRegistro.id == 1}"><%-- previsão chuva --%>
					<jsp:include page="./form-prediction-date.jsp"/>
					<jsp:include page="./form-probability.jsp"/>
					<jsp:include page="./form-intensity.jsp"/>
				</c:when>
				<c:when test="${registro.tipoRegistro.id == 2}"><%-- registro chuva --%>
					<jsp:include page="./form-register-date.jsp"/>
					<jsp:include page="./form-intensity.jsp"/>
				</c:when>
				<c:when test="${registro.tipoRegistro.id == 3}"><%-- previsão temperatura --%>
					<jsp:include page="./form-prediction-date.jsp"/>
					<jsp:include page="./form-probability.jsp"/>
					<jsp:include page="./form-temperature.jsp"/>
				</c:when>
				<c:when test="${registro.tipoRegistro.id == 4}"><%-- registro temperatura --%>
					<jsp:include page="./form-register-date.jsp"/>
					<jsp:include page="./form-temperature.jsp"/>
				</c:when>
				<c:otherwise>
					Tipo de registro inválido.
				</c:otherwise>
			</c:choose>
			<li>
				<label>Data de cadastro:</label>
				<fmt:formatDate pattern="${datePattern}" value="${registro.dataRegistro}"/> -
				${registro.registrante.nome}
			</li>
			<c:if test="${not empty registro.dataUltimaEdicao}">
				<li>
					<label>Última Edição:</label>
					<fmt:formatDate pattern="${datePattern}" value="${registro.dataUltimaEdicao}"/> -
					${registro.ultimoEditor.nome}
				</li>
			</c:if>
		</ul>
		<footer>
			<c:url value="/services?op=reg" var="cancelURL"/>
			<button type="submit">Confirmar</button>
			<button type="button" onclick="location.href='${cancelURL}'">Cancelar</button>
		</footer>
	</form>
</section>

<style type="text/css">
	section ul {width: 500px; margin: 0; padding: 0}
	section li {list-style-type: none; padding: 10px 0; border-bottom: solid 1px #ddd}
	section footer {margin-top: 25px}
</style>

<script type="text/javascript">
	(function() {
		document.getElementById("uf").focus();
	})();
</script>

<jsp:include page="/WEB-INF/view/parts/footer.jsp"/>