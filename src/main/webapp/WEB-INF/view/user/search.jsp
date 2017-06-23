<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/view/parts/header.jsp"/>

<header>
	<h1>Usuário</h1>
</header>
	
<section>

	<jsp:include page="/WEB-INF/view/parts/message.jsp"/>

	<c:url value="/services" var="searchAction"/>
	<form method="get" action="${searchAction}">
		<input type="hidden" name="op" value="user"/>

		<h3>Buscar Usuários</h3>
		<ul>
			<li>
				<label>ID <input type="text" name="id" value="${searchParams.id}"/></label>
			</li>
			<li>
				<label>Login <input type="text" name="login" value="${searchParams.login}"/></label>
			</li>
			<li>
				<label>Nome <input type="text" name="nome" value="${searchParams.nome}"/></label>
			</li>
			<li>
				<c:set value="${searchParams.ativo eq true ? 'checked=checked' : ''}" var="checked"/>
				<label><input type="radio" name="ativo" value="true" ${checked}/> Ativo</label>

				<c:set value="${searchParams.ativo eq false ? 'checked=checked' : ''}" var="checked"/>
				<label><input type="radio" name="ativo" value="false" ${checked}/> Inativo</label>
			</li>	
		</ul>
		<footer>
			<button type="submit">Buscar</button>
		</footer>
	</form>
	
	<button id="addButton">Incluir Usuário</button>
	
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>Login</th>
				<th>Nome</th>
				<th>Ativo</th>
				<th>Editar</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${usuarioList}" var="usuario">
				<c:url value="/services?op=useredit&id=${usuario.id}" var="editURL"/>
				<tr>
					<td>${usuario.id}</td>
					<td>${usuario.login}</td>
					<td>${usuario.nome}</td>
					<td>${usuario.ativo ? 'Sim' : 'Não'}</td>
					<td><a href="${editURL}">Editar</a></td>
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

<c:url value="/services?op=userinsert" var="addURL"/>
<script type="text/javascript">
	var btn = document.getElementById("addButton");
	btn.onclick = function() {
		location.href = "${addURL}";
	};
</script>

<jsp:include page="/WEB-INF/view/parts/footer.jsp"/>