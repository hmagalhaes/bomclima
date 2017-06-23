<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/view/parts/header.jsp"/>
<header>
	<h1>Login</h1>
</header>
	
<section>
	<jsp:include page="/WEB-INF/view/parts/message.jsp"/>

	<c:url value="/services?op=login" var="formAction"/>
	<form method="post" action="${formAction}">
		<ul>
			<li>
				<label>Usuario <input type="text" name="login" value="${login}" id="login"/></label>
			</li>
			<li>
				<label>Senha <input type="password" name="password"/></label>
			</li>
		</ul>
		<footer>
			<button type="submit">Confirmar</button>
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
		document.getElementById("login").focus();
	})();
</script>

<jsp:include page="/WEB-INF/view/parts/footer.jsp"/>