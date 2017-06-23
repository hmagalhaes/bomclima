<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/view/parts/header.jsp"/>

<header>
	<h1>Usuário &gt; Alterar Senha</h1>
</header>

<section>
	<jsp:include page="/WEB-INF/view/parts/message.jsp"/>

	<c:url value="/services?op=userpass&id=${usuario.id}" var="formAction"/>
	<form method="post" action="${formAction}" id="passwordform">
		<ul>
			<li>
				<label>ID <span>${usuario.id}</span></label>
			</li>
			<li>
				<label>Login <span>${usuario.login}</span></label>
			</li>
			<li>
				<label>Senha <input type="password" name="senha" id="senha"/></label>
			</li>
			<li>
				<label>Repita a senha <input type="password" id="senha2"/></label>
			</li>
		</ul>
		<footer>
			<c:url value="/services?op=useredit&id=${usuario.id}" var="cancelURL"/>
			<button type="submit">Confirmar</button>
			<button type="button" onclick="location.href='${cancelURL}'">Cancelar</button>
		</footer>
	</form> 
</section>

<jsp:include page="/WEB-INF/view/parts/footer.jsp"/>
<script type="text/javascript">
	(function() {
		var form = document.getElementById("passwordform");
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