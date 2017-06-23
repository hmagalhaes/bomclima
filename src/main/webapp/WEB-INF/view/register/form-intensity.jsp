<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<li>
	<c:choose>
		<c:when test="${param.readonly eq 'true'}">
			<label>Intensidade: ${registro.intensidade.descricao}</label>
		</c:when>
		<c:otherwise>
			<label>Intensidade:
				<select name="intensidade">
					<c:choose>
						<c:when test="${empty registro.intensidade}">
							<option value="" selected="selected">Escolha</option>
						</c:when>
						<c:otherwise>
							<option value="">Escolha</option>	
						</c:otherwise>
					</c:choose>
					<c:forEach items="${intensidadeList}" var="intensidade">
						<c:choose>
							<c:when test="${intensidade eq registro.intensidade}">
								<option value="${intensidade.code}" selected="selected">${intensidade.descricao}</option>
							</c:when>
							<c:otherwise>
								<option value="${intensidade.code}">${intensidade.descricao}</option>	
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</label>
		</c:otherwise>
	</c:choose>
</li>