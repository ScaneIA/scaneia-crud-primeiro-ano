<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Login Área Restrita</title>
</head>
<body>
<h1>Login Área Restrita</h1>

<form action="<%= request.getContextPath() %>/loginAreaRestrita" method="post">
  <label for="senha">Senha:</label>
  <input type="password" id="senha" name="senha" required>
  <button type="submit">Entrar</button>
</form>

<%
  String erro = (String) request.getAttribute("erro");
  if (erro != null) {
%>
<p style="color:red;"><%= erro %></p>
<% } %>
</body>
</html>


