<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, com.scaneia.ScaneiaServlet.Model.*" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Endereços da Empresa</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/areaRestritaAssets/style.css">
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

    .modal-content {
      background-color: #fff;
      padding: 20px;
      border-radius: 10px;
      width: 400px;
      max-width: 90%;
      box-shadow: 0 5px 15px rgba(0,0,0,0.3);
      position: relative;
    }

    .modal .close {
      position: absolute;
      top: 10px;
      right: 15px;
      font-size: 25px;
      font-weight: bold;
      cursor: pointer;
    }

    .erro {
      position: fixed;
      bottom: 20px;
      right: 20px;
      background-color: #ee4040;
      color: white;
      padding: 10px;
      border-radius: 5px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
      z-index: 2000;
    }
  </style>
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
      // Recupera lista de endereços vinda do servlet
      List<EnderecoEmpresaModel> enderecos = (List<EnderecoEmpresaModel>) request.getAttribute("enderecos");
      boolean temEndereco = enderecos != null && !enderecos.isEmpty();
      if (temEndereco) {
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
          <!-- Passa todos os campos para o modal de edição -->
          <a href="javascript:void(0);" class="btn editar"
             onclick="abrirModalEndereco('<%= end.getId() %>', '<%= end.getRua() %>', '<%= end.getNumero() %>', '<%= end.getCidade() %>', '<%= end.getEstado() %>', '<%= end.getBairro() %>', '<%= end.getComplemento() %>', '<%= end.getCep() %>')">
            Editar
          </a>
        </td>
      </tr>
      <% } %>
    </table>
    <% } else { %>
    <p>Nenhum endereço encontrado para esta empresa.</p>
    <% } %>
  </div>
</main>

<!-- FORMULÁRIO DE ADICIONAR ENDEREÇO -->
<div id="campoAddUser" style="display:none;">
  <form action="<%= request.getContextPath() %>/areaRestrita/cadastroEndereco" method="post" id="formAddEndereco">
    <input type="hidden" name="idEmpresa" value="<%= request.getParameter("idEmpresa") %>">

    <div>
      <label for="rua">Rua:</label>
      <input type="text" name="rua" id="rua" required>
      <span id="erroRua" class="erro" style="display:none;">Rua inválida.</span>
    </div>

    <div>
      <label for="numero">Número:</label>
      <input type="text" name="numero" id="numero" required>
      <span id="erroNumero" class="erro" style="display:none;">Número inválido.</span>
    </div>

    <div>
      <label for="cidade">Cidade:</label>
      <input type="text" name="cidade" id="cidade" required>
      <span id="erroCidade" class="erro" style="display:none;">Cidade inválida.</span>
    </div>

    <div>
      <label for="estado">Estado:</label>
      <input type="text" name="estado" id="estado" required>
      <span id="erroEstado" class="erro" style="display:none;">Estado inválido.</span>
    </div>

    <!-- Campos que estavam faltando -->
    <div>
      <label for="bairro">Bairro:</label>
      <input type="text" name="bairro" id="bairro">
    </div>

    <div>
      <label for="complemento">Complemento:</label>
      <input type="text" name="complemento" id="complemento">
    </div>

    <div>
      <label for="cep">CEP:</label>
      <input type="text" name="cep" id="cep" required>
      <span id="erroCep" class="erro" style="display:none;">CEP inválido (use 00000-000).</span>
    </div>

    <button type="submit">Cadastrar</button>
  </form>
</div>

<!-- MODAL DE EDIÇÃO -->
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

      <!-- Campos adicionados novamente -->
      <div><label for="editarBairro">Bairro:</label><input type="text" name="bairro" id="editarBairro"></div>
      <div><label for="editarComplemento">Complemento:</label><input type="text" name="complemento" id="editarComplemento"></div>

      <div><label for="editarCep">CEP:</label><input type="text" name="cep" id="editarCep"></div>

      <button type="submit">Salvar Alterações</button>
    </form>
  </div>
</div>

<script>
  // Expressões regulares para validação
  const regexCep = /^\d{5}-?\d{3}$/;
  const regexTexto = /^[A-Za-zÀ-ÖØ-öø-ÿ0-9\s.,'-]+$/;

  // Bloqueia novo cadastro se a empresa já tiver endereço
  const temEndereco = <%= temEndereco %>;
  function abrirCadastroEndereco() {
    if (temEndereco) {
      mostrarErroUnico("Esta empresa já possui um endereço cadastrado. É permitido apenas um.");
      return;
    }
    const campo = document.querySelector("#campoAddUser");
    campo.style.display = campo.style.display === "block" ? "none" : "block";
  }

  // Mostra mensagem de erro temporária
  function mostrarErroUnico(msg) {
    const erro = document.createElement('div');
    erro.className = 'erro';
    erro.textContent = msg;
    document.body.appendChild(erro);
    setTimeout(() => erro.remove(), 4000);
  }

  // Validação do formulário de adicionar
  document.getElementById("formAddEndereco").addEventListener("submit", function(event) {
    let erro = false;
    const rua = document.getElementById('rua').value.trim();
    const numero = document.getElementById('numero').value.trim();
    const cidade = document.getElementById('cidade').value.trim();
    const estado = document.getElementById('estado').value.trim();
    const cep = document.getElementById('cep').value.trim();

    if (!regexTexto.test(rua)) { document.getElementById('erroRua').style.display = 'block'; erro = true; }
    else document.getElementById('erroRua').style.display = 'none';

    if (!numero.match(/^[0-9]+$/)) { document.getElementById('erroNumero').style.display = 'block'; erro = true; }
    else document.getElementById('erroNumero').style.display = 'none';

    if (!regexTexto.test(cidade)) { document.getElementById('erroCidade').style.display = 'block'; erro = true; }
    else document.getElementById('erroCidade').style.display = 'none';

    if (!regexTexto.test(estado)) { document.getElementById('erroEstado').style.display = 'block'; erro = true; }
    else document.getElementById('erroEstado').style.display = 'none';

    if (!regexCep.test(cep)) { document.getElementById('erroCep').style.display = 'block'; erro = true; }
    else document.getElementById('erroCep').style.display = 'none';

    if (erro) event.preventDefault();
  });

  // Abre modal de edição preenchendo todos os campos
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

  // Fecha o modal
  function fecharModalEndereco() {
    document.getElementById('modalEditarEndereco').style.display = 'none';
  }

  // Validação simples no editar (CEP)
  document.getElementById("formEditarEndereco").addEventListener("submit", function(event) {
    const cep = document.getElementById('editarCep').value.trim();
    if (!regexCep.test(cep)) {
      mostrarErroUnico("CEP inválido. Use o formato 00000-000.");
      event.preventDefault();
    }
  });
</script>

</body>
</html>
