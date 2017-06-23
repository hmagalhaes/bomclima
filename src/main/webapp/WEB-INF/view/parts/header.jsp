<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.viewLocaleCode}" scope="request"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8"/>
	<title>Bom Clima - SI de suporte a previsão e monitoramento de clima</title>
	<style type="text/css">
		* {font-size: inherit}
		html {font-size: 12pt}
		h1  {font-size: 140%}
		a {color: blue}
		table {width: 100%}
		th, td {text-align: left; padding: 5px 10px; border-bottom: solid 1px #ddd}
		th {font-weight: bold}
		body {margin: 0; margin-bottom: 50px; padding: 0}
		body > header {background: #48486c; width: 100%; padding: 5px 0; display: table; color: #efefef}
		body > header h1 {font-size: 110%; padding: 0; margin: 0}
		body > header ul {list-style-type: none; padding: 0; margin: 10px 0 0 0}
		body > header li {display: block; padding-right: 15px; float: left}
		body > header a {color: white}
		body > header .profile {float: right; text-align: right} 
		
		.error {color: #b34444; padding: 5px}
		.error:before {content: "! "}
		.success {color: #577757; padding: 5px}
		.success:before {content: "! "}
		
		.container {width: 800px; margin: 0 auto}
		
		main.container {padding-top: 10px}
	</style>
</head>
<body>
	<header>
		<div class="container">
			<h1>Bom Clima</h1>
			<div class="profile">
				<c:if test="${not empty sessionScope.currentUser}">
					Olá, ${sessionScope.currentUser.nome}<br/>
					<a href="<c:url value='/services?op=logout'/>">Fechar Sessão</a>
				</c:if>
			</div>
			<nav>
				<ul>
					<li><a href="<c:url value='/services?op=user'/>">Usuários</a></li>
					<li><a href="<c:url value='/services?op=reg'/>">Registros e Previsões</a></li>
				</ul>
			</nav>
		</div>
	</header>
	<main class="container">
	