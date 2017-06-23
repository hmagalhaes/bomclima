<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/view/parts/header.jsp"/>

<header>
	<h1>Usuário &gt; Editar</h1>
</header>

<section>
	<jsp:include page="/WEB-INF/view/parts/message.jsp"/>

	<c:url value="/services?op=useredit&id=${usuario.id}" var="formAction"/>
	<form method="post" action="${formAction}">
		<ul>
			<li>
				<label>ID <span>${usuario.id}</span></label>
			</li>
			<li>
				<label>Login <input type="text" name="login" value="${usuario.login}"/></label>
			</li>
			<li>
				<label>Nome <input type="text" name="nome" value="${usuario.nome}"/></label>
			</li>
			<li>
				<c:set value="${usuario.ativo ? ' checked=checked' : ''}" var="checked"/>
				<label><input type="radio" name="ativo" value="true" ${checked}/> Ativo</label>

				<c:set value="${not usuario.ativo ? ' checked=checked' : ''}" var="checked"/>
				<label><input type="radio" name="ativo" value="false" ${checked}/> Inativo</label>
			</li>
			<li>
				<c:url value="/services?op=userpass&id=${usuario.id}" var="passwordURL"/>
				<label>Senha <a href="${passwordURL}">Alterar Senha</a></label>
			</li>
		</ul>
		<footer>
			<c:url value="/services?op=user" var="cancelURL"/>
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

<jsp:include page="/WEB-INF/view/parts/footer.jsp"/>