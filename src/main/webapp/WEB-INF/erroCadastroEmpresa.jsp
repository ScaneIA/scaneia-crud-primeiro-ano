<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Erro no Cadastro da Empresa</title>
</head>
<body>
<h2>Erro ao cadastrar empresa</h2>
<p>Status: <%= request.getAttribute("status") %></p>
<p>Mensagem: <%= request.getAttribute("mensagem") %></p>
<a href="<%= request.getContextPath() %>/areaRestrita">Voltar</a>
</body>
</html>

