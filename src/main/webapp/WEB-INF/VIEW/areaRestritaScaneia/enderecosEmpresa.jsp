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

<header>
  <img id="imgHeader" src="<%= request.getContextPath() %>/areaRestritaAssets/LogoCompleta.png" alt="Logo do ScaneIA">
  <nav>
    <a href="<%= request.getContextPath() %>/logout" id="aHeader">Sair</a>
  </nav>
</header>

<main>
  <div id="caixaFundo">
    <div id="campoFiltro">
      <div><h2>Endereços da Empresa</h2></div>
      <div>
        <button onclick="abrirCadastroEndereco()" type="button" id="addUserButton">Adicionar Endereço</button>
      </div>
      <div>
        <a href="<%= request.getContextPath() %>/areaRestrita">
          <button id="voltarButton">Voltar</button>
        </a>
      </div>
    </div>
    <%
      List<EnderecoEmpresaModel> enderecos = (List<EnderecoEmpresaModel>) request.getAttribute("enderecos");
      if (enderecos != null && !enderecos.isEmpty()) {
    %>
    <table>
      <tr id="headerTabela">
        <th>Rua</th>
        <th>Número</th>
        <th>Cidade</th>
        <th>Estado</th>
        <th>Bairro</th>
        <th>Complemento</th>
        <th>CEP</th>
        <th>Ações</th>
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
        <td>
          <div class="acoes">
            <a href="javascript:void(0);" class="btn editar"
               onclick="abrirModalEndereco('<%= end.getId() %>', '<%= end.getRua() %>', '<%= end.getNumero() %>', '<%= end.getCidade() %>', '<%= end.getEstado() %>', '<%= end.getBairro() %>', '<%= end.getComplemento() %>', '<%= end.getCep() %>')">
              Editar
            </a>
          </div>
        </td>
      </tr>
      <% } %>
    </table>
    <% } else { %>
    <p>Nenhum endereço encontrado para esta empresa.</p>
    <% } %>
  </div>
</main>

<!-- Formulário de Adicionar Endereço -->
<div id="campoAddUser" style="display:none;">
  <form action="<%= request.getContextPath() %>/areaRestrita/cadastroEndereco" method="post" id="formAddEndereco">
    <input type="hidden" name="idEmpresa" value="<%= request.getParameter("idEmpresa") %>">
    <div><label for="rua">Rua:</label><input type="text" name="rua" id="rua" required></div>
    <div><label for="numero">Número:</label><input type="text" name="numero" id="numero" required></div>
    <div><label for="cidade">Cidade:</label><input type="text" name="cidade" id="cidade" required></div>
    <div><label for="estado">Estado:</label><input type="text" name="estado" id="estado" required></div>
    <div><label for="bairro">Bairro:</label><input type="text" name="bairro" id="bairro"></div>
    <div><label for="complemento">Complemento:</label><input type="text" name="complemento" id="complemento"></div>
    <div><label for="cep">CEP:</label><input type="text" name="cep" id="cep" required></div>
    <button type="submit">Cadastrar</button>
  </form>
</div>

<!-- Modal de Edição -->
<div id="modalEditarEndereco" class="modal" style="display:none;">
  <div class="modal-content">
    <span class="close" onclick="fecharModalEndereco()">&times;</span>
    <h2>Editar Endereço</h2>
    <form id="formEditarEndereco" method="post" action="<%= request.getContextPath() %>/areaRestrita/alterarEndereco">
      <input type="hidden" name="id" id="editarEnderecoId">
      <div><label for="editarRua">Rua:</label><input type="text" name="rua" id="editarRua"></div>
      <div><label for="editarNumero">Número:</label><input type="text" name="numero" id="editarNumero"></div>
      <div><label for="editarCidade">Cidade:</label><input type="text" name="cidade" id="editarCidade"></div>
      <div><label for="editarEstado">Estado:</label><input type="text" name="estado" id="editarEstado"></div>
      <div><label for="editarBairro">Bairro:</label><input type="text" name="bairro" id="editarBairro"></div>
      <div><label for="editarComplemento">Complemento:</label><input type="text" name="complemento" id="editarComplemento"></div>
      <div><label for="editarCep">CEP:</label><input type="text" name="cep" id="editarCep"></div>
      <button type="submit">Salvar Alterações</button>
    </form>
  </div>
</div>

<script>
  function abrirModalEndereco(id, rua, numero, cidade, estado, bairro, complemento, cep) {
    document.getElementById('editarEnderecoId').value = id;
    document.getElementById('editarRua').value = rua;
    document.getElementById('editarNumero').value = numero;
    document.getElementById('editarCidade').value = cidade;
    document.getElementById('editarEstado').value = estado;
    document.getElementById('editarBairro').value = bairro;
    document.getElementById('editarComplemento').value = complemento;
    document.getElementById('editarCep').value = cep;
    document.getElementById('modalEditarEndereco').style.display = 'flex';
  }

  function fecharModalEndereco() {
    document.getElementById('modalEditarEndereco').style.display = 'none';
  }

  function abrirCadastroEndereco() {
    const campo = document.querySelector("#campoAddUser");
    campo.style.display = campo.style.display === "block" ? "none" : "block";
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