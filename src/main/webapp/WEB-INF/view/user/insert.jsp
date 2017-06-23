<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/view/parts/header.jsp"/>

<header>
	<h1>Usuário &gt; Inserir</h1>
</header>

<section>
	<jsp:include page="/WEB-INF/view/parts/message.jsp"/>

	<c:url value="/services?op=userinsert" var="formAction"/>
	<form method="post" action="${formAction}" id="userform">
		<ul>
			<li>
				<label>Login <input type="text" name="login" value="${usuario.login}"/></label>
			</li>
			<li>
				<label>Nome <input type="text" name="nome" value="${usuario.nome}"/></label>
			</li>
			<li>
				<label>Senha <input type="password" name="senha" id="senha"/></label>
			</li>
			<li>
				<label>Repita a senha <input type="password" id="senha2"/></label>
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
<script type="text/javascript">
	(function() {
		var form = document.getElementById("userform");
		form.onsubmit = function() {
			var pass1 = document.getElementById("senha").value;
			var pass2 = document.getElementById("senha2").value;
			if (pass1 != pass2) {
				alert("As senhas não coincidem.");
				return false;
			}
			return true;
		};
	})();
</script>