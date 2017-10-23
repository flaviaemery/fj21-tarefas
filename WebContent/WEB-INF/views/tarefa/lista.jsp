<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript"
	src="resources/jquery-3.2.1.js"></script>
</head>

<!-- Começo da página com o import do Javascript -->
<body>
	<script type="text/javascript">
		function finalizaAgora(id) {
			$.post("finalizaTarefa", {'id' : id},
			function(resposta) {
				$("#tarefa_"+id).html(resposta);
			});
		}
	</script>

	<a href="novaTarefa">Criar nova tarefa</a>
	<br /> <br />
		
	<table>
	<!-- Resto da página com a tabela -->
	<tr>
		<th>Id</th>
		<th>Descrição</th>
		<th>Finalizado?</th>
		<th>Data de finalização</th>
	</tr>
	<c:forEach items="${tarefas}" var="tarefa">
	<tr id="tarefa_${tarefa.id}">
		<td>${tarefa.id}</td>
		<td>${tarefa.descricao}</td>
		
		<c:if test="${tarefa.finalizado eq true}">
			<td>Finalizado</td>
		</c:if>
		
		<c:if test="${tarefa.finalizado eq false}">
			<td>
				<a href="#"
				onClick="finalizaAgora(${tarefa.id})">
				Finalizar
				</a>
			</td>
		</c:if>
		
		<td>
			<fmt:formatDate 
			value="${tarefa.dataFinalizacao.time}"
			pattern="dd/MM/yyyy"/>
		</td>
		
	</tr>
	</c:forEach>
	</table>
</body>
</html>