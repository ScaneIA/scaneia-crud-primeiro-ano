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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/areaRestritaAssets/style.css">
    <title>Área Restrita</title>
</head>
<body>
    <header>
        <img id="imgHeader" src="${pageContext.request.contextPath}/areaRestritaAssets/LogoCompleta.svg" alt="Logo do ScaneIA">
        <nav>
            <a href="#" id="aHeader">Sair</a>
        </nav>
    </header>
    <main>
        <div id="caixaFundo">
            <div id="campoFiltro">
                    <div>
                        <select name="filtroCargo" id="filtroCargo">
                        <option value="" selected hidden="">Escolha um cargo</option>
                        <option value="todos">Todos os cargos</option>
                        <option value="operario">Operario</option>
                        <option value="chefeDeArea">Chefe de Área</option>
                        <option value="RH">Recursos Humanos</option>
                        <option value="diretor">Diretor</option>
                    </select>
                    </div>
                    <div>
                        <form action="filtro/nome">
                            <input type="text">
                            <button type="submit">Enviar</button>
                        </form>
                    </div>
                    <div>
                        <button>Adicionar funcionário</button>
                    </div>
                </div>
            <table>
                <tr>
                    <th>NOME</th>
                    <th>CARGO</th>
                    <th>SETOR</th>
                    <th>CPF</th>
                    <th>REGISTRO</th>
                </tr>
                <%
                    for (int index = 0; index < usuarios.size(); index++){
                        UsuarioViewModel usuario = usuarios.get(index);
                %>
                <tr>
                    <td>
                        <a href="EditarFuncionario?id=<%=usuario.getId()%>"><%=usuario.getNome()%></a>
                    </td>
                    <td>
                        <a href="EditarFuncionario?id=<%=usuario.getId()%>"><%=usuario.getCargo()%></a>
                    </td>
                    <td>
                        <a href="EditarFuncionario?id=<%=usuario.getId()%>"><%=usuario.getSetor()%></a>
                    </td>
                    <td>
                        <a href="EditarFuncionario?id=<%=usuario.getId()%>"><%=usuario.getCpf()%></a>
                    </td>
                    <td>
                        <a href="EditarFuncionario?id=<%=usuario.getId()%>"><%=usuario.getRegistro()%></a>
                    </td>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
    </main>

    <script>
        document.querySelector('#filtroCargo').addEventListener("change", function(){
            const escolha = this.value;
            const contextPath = window.location.pathname.split('/')[1]
            window.location.href = '/' + contextPath + '/areaRH/filtro?cargo=' + this.value
        })
    </script>
</body>
</html>