<%@ page import="java.util.List" %>
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
        <img id="imgHeader" src="${pageContext.request.contextPath}/areaRestritaAssets/LogoCompleta.png" alt="Logo do ScaneIA">
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
                        <form action="<%=request.getContextPath()%>/areaRH/nome" id="formPesquisar">
                            <input type="text" name="nome" placeholder="Pesquisar por Nome" id="inputFiltroNome" size="50" maxlength="40">
                            <input type="image" alt="Enviar" src="${pageContext.request.contextPath}/areaRestritaAssets/pesquisa.png" id="imgEnviar">
                        </form>
                    </div>
                    <div>
                        <button onclick="adicionarUsuario()" type="button" id="addUserButton">Adicionar funcionário</button>
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
                        <a href="<%=request.getContextPath()%>/areaRH/EditarFuncionario?id=<%=usuario.getId()%>" class="aTable"><%=usuario.getNome()%></a>
                    </td>
                    <td>
                        <a href="<%=request.getContextPath()%>/areaRH/EditarFuncionario?id=<%=usuario.getId()%>" class="aTable"><%=usuario.getCargo()%></a>
                    </td>
                    <td>
                        <a href="<%=request.getContextPath()%>/areaRH/EditarFuncionario?id=<%=usuario.getId()%>" class="aTable"><%=usuario.getSetor()%></a>
                    </td>
                    <td>
                        <a href="<%=request.getContextPath()%>/areaRH/EditarFuncionario?id=<%=usuario.getId()%>" class="aTable"><%=usuario.getCpf()%></a>
                    </td>
                    <td>
                        <a href="<%=request.getContextPath()%>/areaRH/EditarFuncionario?id=<%=usuario.getId()%>" class="aTable"><%=usuario.getRegistro()%></a>
                    </td>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
    </main>

    <div id="campoAddUser">
        <form action="areaRH/cadastroUsuario" method="post" id="formAddUser">

            <div>
                <label for="addNome">Nome: </label>
                <input type="text" name="addNome" id="addNome">
            </div>

            <div>
                <label for="addEmail">Email: </label>
                <input type="text" name="addEmail" id="addEmail">
            </div>

            <div>
                <label for="addCpf">Cpf: </label>
                <input type="text" name="addCpf" id="addCpf">
            </div>

            <div>
                <label for="idCargo">Cargo: </label>
                <select name="idCargo" id="idCargo">
                    <option value="8" selected>Colaborador</option>
                    <option value="7">RH</option>
                    <option value="6">Chefe de Área</option>
                    <option value="5">Diretor</option>
                </select>
            </div>
            <button type="submit">Adicionar</button>
        </form>
    </div>


    <script>
        // parte para o filtro
        document.querySelector('#filtroCargo').addEventListener("change", function(){
            const contextPath = window.location.pathname.split('/')[1]
            window.location.href = '/' + contextPath + '/areaRH/filtro?cargo=' + this.value
        })

        // parte para adicionar usuario
        function adicionarUsuario() {
            const campo = document.querySelector("#campoAddUser")
            if (campo.style.display === "block") {
                campo.style.display = "none";
            } else {
                campo.style.display = "block";
            }
        }
    </script>
</body>
</html>