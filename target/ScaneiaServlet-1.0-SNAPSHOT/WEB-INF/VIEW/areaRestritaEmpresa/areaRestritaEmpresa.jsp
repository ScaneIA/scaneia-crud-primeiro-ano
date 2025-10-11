<%@ page import="java.awt.*" %>
<%@ page import="com.scaneia.ScaneiaServlet.Model.UsuarioModel" %>
<%@ page import="java.util.List" %>
<%@ page import="com.scaneia.ScaneiaServlet.Model.CargoModel" %>
<%@ page import="com.scaneia.ScaneiaServlet.Model.SetorModel" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
    //carrega os atributos
    List<UsuarioModel> usuarios = (List<UsuarioModel>) request.getAttribute("usuarios");
    List<CargoModel> cargos = (List<CargoModel>) request.getAttribute("cargos");
    List<SetorModel> setores = (List<SetorModel>) request.getAttribute("setores");
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
                    <th>ID</th>
                    <th>NOME</th>
                    <th>EMAIL</th>
                    <th>CPF</th>
                    <th>Cargo</th>
                    <th>Setor</th>
                    <th>Telefone</th>
                </tr>
                <%
                    for (int index = 0; index < usuarios.size(); index++){
                        UsuarioModel usuario = usuarios.get(index);
                        CargoModel cargo = cargos.get(index);
                        SetorModel setor = setores.get(index);
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