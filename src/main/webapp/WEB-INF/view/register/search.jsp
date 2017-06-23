<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/view/parts/header.jsp"/>

<header>
	<h1>Registro</h1>
</header>

<section>
	<jsp:include page="/WEB-INF/view/parts/message.jsp"/>

	<c:url value="/services" var="searchAction"/>
	<form method="get" action="${searchAction}">
		<input type="hidden" name="op" value="reg"/>

		<h3>Buscar Registros</h3>
		<ul>
			<li>
				<label>Data 
					<fmt:formatDate pattern="${datePattern}" value="${searchParams.dataRegistro1}" var="dataRegistro1"/>
					<fmt:formatDate pattern="${datePattern}" value="${searchParams.dataRegistro2}" var="dataRegistro2"/>
					<input type="text" name="dataRegistro1" value="${dataRegistro1}"/> -
					<input type="text" name="dataRegistro2" value="${dataRegistro2}"/>
				</label>
			</li>
			<li><label>Cidade <input type="text" name="cidade" value="${searchParams.cidade}"/></label></li>
			<li><label>UF <input type="text" name="uf" value="${searchParams.uf}"/></label></li>
		</ul>
		<footer>
			<button type="submit">Buscar</button>
		</footer>
	</form>
	
	<button id="addButton">Incluir Registro</button>
	
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>Data Registro</th>
				<th>Registrante</th>
				<th>Local</th>
				<th>Tipo</th>
				<th>Editar</th>
				<th>Remover</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${registroList}" var="registro">
				<c:url value="/services?op=regedit&id=${registro.id}" var="editURL"/>
				<c:url value="/services?op=regdel&id=${registro.id}" var="delURL"/>
				<tr>
					<td>${registro.id}</td>
					<td><fmt:formatDate pattern="${datePattern}" value="${registro.dataRegistro}"/></td>
					<td>${registro.registrante.nome}</td>
					<td>${registro.cidade.cidade}/${registro.cidade.uf.uf}</td>
					<td>${registro.tipoRegistro.descricao}</td>
					<td><a href="${editURL}">Editar</a></td>
					<td><a href="${delURL}">Remover</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</section>

<style type="text/css">
	section form {background: #eee; padding: 10px 5px; display: table}
	section form ul {list-style-type: none; padding: 0; margin: 0}
	section form li {float: left; padding-right: 10px; padding-bottom: 10px}
	section form footer {clear: both; padding-top: 10px}
	section #addButton {margin-top: 30px}
	section table {margin-top: 14px}
</style>

<c:url value="/services?op=reginsertgat" var="addURL"/>
<script type="text/javascript">
	var btn = document.getElementById("addButton");
	btn.onclick = function() {
		location.href = "${addURL}";
	};
</script>

<jsp:include page="/WEB-INF/view/parts/footer.jsp"/>
