<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, com.scaneia.ScaneiaServlet.Model.*" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Endereços da Empresa</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/areaRestritaAssets/style.css">
</head>
<body>
<h1>Endereço da Empresa</h1>
<a href="<%= request.getContextPath() %>/areaRestrita"><button>Voltar</button></a>

<%
  List<EnderecoEmpresaModel> enderecos = (List<EnderecoEmpresaModel>) request.getAttribute("enderecos");
  if (enderecos != null && !enderecos.isEmpty()) {
%>
<table>
  <tr>
    <th>Rua</th><th>Número</th><th>Cidade</th><th>Estado</th>
    <th>Bairro</th><th>Complemento</th><th>CEP</th>
  </tr>
  <% for (EnderecoEmpresaModel end : enderecos) { %>
  <tr>
    <td><%= end.getRua() %></td>
    <td><%= end.getNumero() %></td>
    <td><%= end.getCidade() %></td>
    <td><%= end.getEstado() %></td>
    <td><%= end.getBairro() %></td>
    <td><%= end.getComplemento() %></td>
    <td><%= end.getCep() %></td>
  </tr>
  <% } %>
</table>
<% } else { %>
<p>Nenhum endereço encontrado para esta empresa.</p>
<% } %>
</body>
</html>

