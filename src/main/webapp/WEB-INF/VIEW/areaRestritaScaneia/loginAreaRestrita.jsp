<%@ page contentType="text/html; charset=UTF-8" %>
  <!DOCTYPE html>
  <html lang="pt-BR">

  <head>
    <meta charset="UTF-8">
    <title>Login Área Restrita</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Login/css/loginEmpresa.css">
  </head>

  <body>
    <header>
      <img id="imgHeader" src="${pageContext.request.contextPath}/Login/img/Group_65765-removebg-preview.png" alt="Logo ScaneIA">
      <nav>
        <a href="<%=request.getContextPath()%>/index.html" id="voltarTopo">Voltar</a>
      </nav>
    </header>

    <main>
      <img src="${pageContext.request.contextPath}/Login/img/IMG_300px.png" alt="" id="figEsquerda">
      <img src="${pageContext.request.contextPath}/Login/img/imagem_300x300.png" alt="" id="figDireita">
      <img src="${pageContext.request.contextPath}/Login/img/scaneia_logo_resized.png" alt="Logo scaneia" id="imgMain">
      <h1>Login Área Restrita</h1>

      <form action="<%= request.getContextPath() %>/loginAreaRestrita" method="post" id="formCadastro">
        <div class="form-group">
          <label for="email">Email: </label>
          <input type="text" name="email" id="email">
        </div>

        <div class="form-group">
          <label for="senha">Senha:</label>
          <input type="password" id="senha" name="senha" required>
        </div>
        <button type="submit">Entrar</button>
      </form>

      <% String erro=(String) request.getAttribute("mensagem"); if (erro !=null) { %>
        <p style="color:red;">
          <%= erro %>
        </p>
        <% } %>
    </main>

  </body>

  </html>