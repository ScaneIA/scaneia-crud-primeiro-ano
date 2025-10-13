<%@ page import="java.awt.*" %>
<%@ page import="com.scaneia.ScaneiaServlet.Model.UsuarioModel" %>
<%@ page import="java.util.List" %>
<%@ page import="com.scaneia.ScaneiaServlet.Model.CargoModel" %>
<%@ page import="com.scaneia.ScaneiaServlet.Model.SetorModel" %>
<%@ page import="com.scaneia.ScaneiaServlet.Model.UsuarioViewModel" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
    //carrega os atributos
    List<UsuarioViewModel> usuarios = (List<UsuarioViewModel>) request.getAttribute("usuarios");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style.css">
    <title>Área Restrita</title>
</head>
<body>
    <header>
        <img id="imgHeader" src="logoHeader.png" alt="Logo do ScaneIA">
        <nav>
            <a href="#" id="aHeader">Sair</a>
        </nav>
    </header>
    <main>
        <div id="caixaFundo">
            <table>
                <tr>
                    <th>NOME</th>
                    <th>CARGO</th>
                    <th>SETOR</th>
                    <th>CPF</th>
                    <th>REGISTRO</th>
                    <th>Telefone</th>
                </tr>
                <%
                    for (int index = 0; index < usuarios.size(); index++){
                        UsuarioModel usuario = usuarios.get(index);
                        CargoModel cargo = cargos.get(index);
                        SetorModel setor = setores.get(index); //arrumar depois
                %>
                <tr>
                    <td>
                        <a href="EditarFuncionario?id=<%=usuario.getId()%>"><%=usuario.getId()%></a>
                    </td>
                    <td>
                        <a href="EditarFuncionario?id=<%=usuario.getId()%>"><%=usuario.getNome()%></a>
                    </td>
                    <td>
                        <a href="EditarFuncionario?id=<%=usuario.getId()%>"><%=cargo.getNome()%></a>
                    </td>
                    <td>
                        <a href="EditarFuncionario?id=<%=usuario.getId()%>"><%=setor.getNome()%></a>
                    </td>
                    <td>
                        <a href="EditarFuncionario?id=<%=usuario.getId()%>"><%=usuario.getCpf()%></a>
                    </td>
                    <td>
                        <a href="EditarFuncionario?id=<%=usuario.getId()%>"><%=usuario.getEmail()%></a>
                    </td>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
    </main>
</body>
</html>