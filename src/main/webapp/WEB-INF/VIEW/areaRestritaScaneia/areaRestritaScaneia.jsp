<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, com.scaneia.ScaneiaServlet.Model.*" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Área Restrita - Empresas</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/areaRestritaAssets/style.css">
</head>
<body>
<h1>Área Restrita - Empresas </h1>

<form method="get" action="areaRestrita">
  <input type="text" name="nome" placeholder="Pesquisar por nome">
  <input type="hidden" name="acao" value="pesquisar">
  <button type="submit" class="btn pesquisar">Pesquisar</button>
</form>

<h2>Empresas</h2>
<table>
  <tr>
    <th>Nome</th><th>CNPJ</th><th>Email</th>
    <th>Data Criação</th><th>Ações</th>
  </tr>
  <%
    List<EmpresaModel> empresas = (List<EmpresaModel>) request.getAttribute("empresas");
    if (empresas != null && !empresas.isEmpty()) {
      for (EmpresaModel e : empresas) {
  %>
  <tr>
    <td>
      <a href="<%= request.getContextPath() %>/areaRH?idEmpresa=<%= e.getId() %>">
        <%= e.getNome() %>
      </a>
    </td>
    <td><%= e.getCnpj() %></td>
    <td><%= e.getEmail() %></td>
    <td><%= e.getDataCriacao() %></td>
    <td>
      <a href="areaRestrita?acao=verEnderecos&idEmpresa=<%= e.getId() %>" class="btn">Endereços</a>
      <a href="areaRestrita?acao=editar&id=<%= e.getId() %>" class="btn editar">Editar</a>
      <a href="areaRestrita?acao=excluir&id=<%= e.getId() %>" class="btn excluir"
         onclick="return confirm('Excluir empresa?')">Excluir</a>
    </td>
  </tr>
  <% } } else { %>
  <tr><td colspan="6">Nenhuma empresa encontrada.</td></tr>
  <% } %>
</table>
</body>
</html>
