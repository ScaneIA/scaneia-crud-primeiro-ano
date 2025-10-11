
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
    <style>
        #mensagemErro{
            border: solid 3px red;
            text-align: center;
            margin: 0 auto;
            width: 30%;
        }
        #mensagemErro::after{
            content: "<%=mensagemErro%>";
        }
    </style>
</body>
</html>
