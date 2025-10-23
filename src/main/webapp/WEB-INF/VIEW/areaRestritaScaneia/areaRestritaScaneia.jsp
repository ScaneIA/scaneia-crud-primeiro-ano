
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, com.scaneia.ScaneiaServlet.Model.*" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Área Restrita - Empresas</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/areaRestritaAssets/style.css">
</head>

<body>

<header>
  <img id="imgHeader" src="<%= request.getContextPath() %>/areaRestritaAssets/LogoCompleta.png" alt="Logo do ScaneIA">
  <nav>
    <a href="<%= request.getContextPath() %>/logout" id="aHeader">Sair</a>
  </nav>
</header>

<main>
  <div id="caixaFundo">

    <!-- Campo de Filtro e Pesquisa -->
    <div id="campoFiltro">
      <div>
        <form method="get" action="<%= request.getContextPath() %>/areaRestrita/pesquisarEmpresa" id="formPesquisar">
        <input type="text" name="nome" placeholder="Pesquisar por nome" id="inputFiltroNome" size="40" maxlength="50">
          <input type="hidden" name="acao" value="pesquisar">
          <input type="image" alt="Pesquisar" src="<%= request.getContextPath() %>/areaRestritaAssets/pesquisa.png" id="imgEnviar">
        </form>
      </div>
      <div>
        <h2>Empresas</h2>
      </div>
      <div>
        <button onclick="abrirCadastroEmpresa()" type="button" id="addUserButton">Adicionar empresa</button>
      </div>
    </div>

    <!-- Tabela de Empresas -->

    <table>
      <tr id="headerTabela">
        <th>Nome</th>
        <th>CNPJ</th>
        <th>Email</th>
        <th>Data Criação</th>
        <th>Endereços</th>
        <th>Ações</th>
      </tr>

      <%
        List<EmpresaModel> empresas = (List<EmpresaModel>) request.getAttribute("empresas");
        if (empresas != null && !empresas.isEmpty()) {
          for (EmpresaModel e : empresas) {
      %>
      <tr>
        <td><a href="<%= request.getContextPath() %>/areaRH?idEmpresa=<%= e.getId() %>" class="aTable"><%= e.getNome() %></a></td>
        <td><%= e.getCnpj() %></td>
        <td><%= e.getEmail() %></td>
        <td><%= e.getDataCriacao() %></td>
        <td><a href="areaRestrita?acao=verEnderecos&idEmpresa=<%= e.getId() %>" class="aTable">Ver Endereços</a></td>
        <td>
          <div class="acoes">
            <a href="javascript:void(0);" class="btn editar"
               onclick="abrirModal('<%= e.getId() %>', '<%= e.getNome() %>', '<%= e.getCnpj() %>', '<%= e.getEmail() %>')">Editar</a>
            |
            <a href="<%= request.getContextPath() %>/areaRestrita/excluirEmpresa?id=<%= e.getId() %>"
               class="btn excluir"
               onclick="return confirm('Deseja realmente excluir esta empresa?')">Excluir</a>

          </div>
        </td>
      </tr>
      <%
        }
      } else {
      %>
      <tr>
        <td colspan="6">Nenhuma empresa encontrada.</td>
      </tr>
      <% } %>
    </table>
  </div>
</main>

<!-- Formulário de Adicionar Empresa -->
<div id="campoAddUser" style="display:none;">
  <form action="<%= request.getContextPath() %>/areaRestrita/cadastroEmpresa" method="post" id="formAddUser">
    <div>
      <label for="nome">Nome: </label>
      <input type="text" name="nome" id="nome" required>
    </div>
    <div>
      <label for="cnpj">CNPJ: </label>
      <input type="text" name="cnpj" id="cnpj" required>
    </div>
    <div>
      <label for="email">Email: </label>
      <input type="email" name="email" id="email" required>
    </div>
    <button type="submit">Cadastrar</button>
  </form>
</div>


<div id="modalEditar" class="modal" style="display:none;">
  <div class="modal-content">
    <span class="close" onclick="fecharModal()">&times;</span>
    <h2>Editar Empresa</h2>
    <form id="formEditarEmpresa" method="post" action="<%= request.getContextPath() %>/areaRestrita/alterarEmpresa">
      <input type="hidden" name="id" id="editarId">
      <div>
        <label for="editarNome">Nome:</label>
        <input type="text" name="nome" id="editarNome">
      </div>
      <div>
        <label for="editarCnpj">CNPJ:</label>
        <input type="text" name="cnpj" id="editarCnpj">
      </div>
      <div>
        <label for="editarEmail">Email:</label>
        <input type="email" name="email" id="editarEmail">
      </div>
      <button type="submit">Salvar Alterações</button>
    </form>
  </div>
</div>

<script>
  // Abre ou fecha o formulário de cadastro
  function abrirCadastroEmpresa() {
    const campo = document.querySelector("#campoAddUser");
    campo.style.display = campo.style.display === "block" ? "none" : "block";
  }

  //edição
  function abrirModal(id, nome, cnpj, email) {
    document.getElementById('editarId').value = id;
    document.getElementById('editarNome').value = nome;
    document.getElementById('editarCnpj').value = cnpj;
    document.getElementById('editarEmail').value = email;
    document.getElementById('modalEditar').style.display = 'flex';
  }

  function fecharModal() {
    document.getElementById('modalEditar').style.display = 'none';
  }
</script>

</body>
</html>
<style>
  /* Modal container - cobre toda a tela */
  .modal {
    display: none;
    position: fixed;
    z-index: 1000;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    overflow: auto;
    background-color: rgba(0,0,0,0.5);
    justify-content: center;
    align-items: center;
    display: flex;
  }

  /* Conteúdo do modal */
  .modal-content {
    background-color: #fff;
    padding: 20px;
    border-radius: 10px;
    width: 400px; /* largura fixa, pode ajustar */
    max-width: 90%;
    box-shadow: 0 5px 15px rgba(0,0,0,0.3);
    position: relative;
  }

  /* Botão de fechar (x) */
  .modal .close {
    position: absolute;
    top: 10px;
    right: 15px;
    font-size: 25px;
    font-weight: bold;
    cursor: pointer;
  }

</style>
