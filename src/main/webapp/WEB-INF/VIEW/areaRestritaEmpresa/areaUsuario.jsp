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
            <form action="alterarCargo" method="post">
                <label for="inputCargo" class="inputLabel"><b>Cargo</b></label>
                <select name="cargo">
                    <option selected><%=usuario.getCargo()%></option>
                    <option value="Diretor">Diretor</option>
                    <option value="Chefe de área">Chefe de área</option>
                    <option value="RH">RH</option>
                    <option value="Colaborador">Operario</option>
                </select>
                <input type="text" hidden value="<%=usuario.getId()%>" name="idUsuario">
                <input type="image" src="${pageContext.request.contextPath}/areaRestritaAssets/saveIcon.png" class="saveIcon" alt="salvar">
            </form>

            <form action="alterarSetor" method="post">
                <label for="inputSetor" class="inputLabel"><b>Setor</b></label>
                <input type="text" size="50" name="setor" class="inputText" id="inputSetor" value="<%=usuario.getSetor()%>">
                <input type="text" hidden value="<%=usuario.getId()%>" name="idUsuario">
                <input type="image" src="${pageContext.request.contextPath}/areaRestritaAssets/saveIcon.png" class="saveIcon" alt="salvar">
            </form>
        </div>

        <div class="fundoForm">
            <!-- Input para informações pessoais -->
            <form action="alterarCpf" method="post">
                <label for="inputCpf" class="inputLabel"><b>CPF</b></label>
                <input type="text" size="50" name="cpf" class="inputText" id="inputCpf" value="<%=usuario.getCpf()%>">
                <input type="text" hidden value="<%=usuario.getId()%>" name="idUsuario">
                <input type="image" src="${pageContext.request.contextPath}/areaRestritaAssets/saveIcon.png" class="saveIcon" alt="salvar">
            </form>

            <form action="" method="post">
                <label for="inputEmail" class="inputLabel"><b>E-mail</b></label>
                <input type="text" size="50" name="input" class="inputText" id="inputEmail" value="<%=usuario.getEmail()%>">
                <input type="text" hidden value="<%=usuario.getId()%>" name="idUsuario">
                <input type="image" src="${pageContext.request.contextPath}/areaRestritaAssets/saveIcon.png" class="saveIcon" alt="salvar">
            </form>
        </div>
    </div>
</body>
</html>
