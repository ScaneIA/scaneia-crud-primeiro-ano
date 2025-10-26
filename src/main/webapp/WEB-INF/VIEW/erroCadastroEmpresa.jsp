
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Área de Cadastro</title>
</head>
<body>
    <!-- inclui a pagina antiga -->
    <jsp:include page="../../Cadastro/cadastro.html"/>

    <!-- Coleta a mensagem de erro -->
    <%
        String mensagemErro = request.getAttribute("mensagem").toString();
    %>

    <!-- adiciona a mensagem de erro -->
    <script>
        document.getElementById('mensagemErro').textContent = "<%=mensagemErro%>"
    </script>
</body>
</html>
