<%@ page import="com.scaneia.ScaneiaServlet.Model.UsuarioViewModel" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
    //carrega o usuario
    UsuarioViewModel usuario = (UsuarioViewModel) request.getAttribute("usuario");
%>
<html>
<head>
    <title>Área Restrita</title>
</head>
<body>
    <jsp:include page="areaRestritaEmpresa.jsp"/>
    <div id="campoUsuario">
        <div>
            <img src="" alt="foto do usuario">
            <h1>Nome</h1>
        </div>
        <div class="fundoForm">
            <!-- Input para informações empresariais -->
            <form action="">
                <label for="inputCargo" class="inputLabel"><b>Cargo</b></label>
                <input type="text" size="50" name="input" class="inputText" id="inputCargo" value="<%=usuario.getCargo()%>">
                <input type="image" src="${pageContext.request.contextPath}/areaRestritaAssets/saveIcon.png" class="saveIcon" alt="salvar">
            </form>

            <form action="">
                <label for="inputSetor" class="inputLabel"><b>Setor</b></label>
                <input type="text" size="50" name="input" class="inputText" id="inputSetor" value="<%=usuario.getSetor()%>">
                <input type="image" src="${pageContext.request.contextPath}/areaRestritaAssets/saveIcon.png" class="saveIcon" alt="salvar">
            </form>
        </div>

        <div class="fundoForm">
            <!-- Input para informações pessoais -->
            <form action="">
                <label for="inputCpf" class="inputLabel"><b>CPF</b></label>
                <input type="text" size="50" name="input" class="inputText" id="inputCpf" value="<%=usuario.getCpf()%>">
                <input type="image" src="${pageContext.request.contextPath}/areaRestritaAssets/saveIcon.png" class="saveIcon" alt="salvar">
            </form>

            <form action="">
                <label for="inputEmail" class="inputLabel"><b>E-mail</b></label>
                <input type="text" size="50" name="input" class="inputText" id="inputEmail" value="<%=usuario.getEmail()%>">
                <input type="image" src="${pageContext.request.contextPath}/areaRestritaAssets/saveIcon.png" class="saveIcon" alt="salvar">
            </form>
        </div>
    </div>
</body>
</html>
