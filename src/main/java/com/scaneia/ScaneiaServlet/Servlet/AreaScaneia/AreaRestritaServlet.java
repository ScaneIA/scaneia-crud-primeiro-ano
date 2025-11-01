package com.scaneia.ScaneiaServlet.Servlet.AreaScaneia;

import com.scaneia.ScaneiaServlet.DAO.EmpresaDAO;
import com.scaneia.ScaneiaServlet.DAO.EnderecoEmpresaDAO;
import com.scaneia.ScaneiaServlet.Model.EmpresaModel;
import com.scaneia.ScaneiaServlet.Model.EnderecoEmpresaModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

// Página principal do CRUD de empresas.
// Ao clicar em "Ver endereços", o usuário é direcionado para o CRUD de endereços da respectiva empresa.
// Nesta página são exibidas as principais informações das empresas,
// e as ações realizadas como editar, excluir ou visualizar redirecionam para os servlets correspondentes.

// qualquer dúvida sobre o comentário acima acesse a documentação

@WebServlet("/areaRestrita")
public class AreaRestritaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // pega a sessão sem criar nova
        HttpSession session = req.getSession(false);

        // verifica se tem sessão e se é admin
        if (!(session != null && session.getAttribute("admin") != null)){
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        // instancia os DAOs necessários
        EmpresaDAO empresaDAO = new EmpresaDAO();
        EnderecoEmpresaDAO enderecoDAO = new EnderecoEmpresaDAO();

        // pega a ação a ser realizada, se for default ele lista
        String acao = req.getParameter("acao");
        if (acao == null) acao = "listar";

        // direciona para o método adequado conforme a ação
        switch (acao) {
            case "editar":
                editar(req, res);
                break;
            case "excluir":
                excluir(req, res);
                break;
            case "pesquisar":
                pesquisar(req, res, empresaDAO, enderecoDAO);
                break;
            case "verEnderecos":
                verEnderecos(req, res);
                break;
            default:
                listar(req, res, empresaDAO, enderecoDAO);
        }
    }

    // lista todas as empresas
    private void listar(HttpServletRequest req, HttpServletResponse res,
                        EmpresaDAO empresaDAO, EnderecoEmpresaDAO enderecoDAO)
            throws ServletException, IOException {

        List<EmpresaModel> empresas = empresaDAO.buscar();
        req.setAttribute("empresas", empresas);

        // envia para o JSP da área restrita
        req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaScaneia/areaRestritaScaneia.jsp")
                .forward(req, res);
    }

    // pesquisa empresas por nome
    private void pesquisar(HttpServletRequest req, HttpServletResponse res,
                           EmpresaDAO empresaDAO, EnderecoEmpresaDAO enderecoDAO)
            throws ServletException, IOException {

        String nome = req.getParameter("nome");
        EmpresaModel emp = new EmpresaModel(nome, "", "", "");
        List<EmpresaModel> empresas = empresaDAO.buscarPorNome(emp);
        List<EnderecoEmpresaModel> enderecos = enderecoDAO.buscar();

        req.setAttribute("empresas", empresas);
        req.setAttribute("enderecos", enderecos);

        // encaminha para a mesma página com resultados da pesquisa
        req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaScaneia/areaRestritaScaneia.jsp")
                .forward(req, res);
    }

    // carrega os dados de uma empresa para edição
    private void editar(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        EmpresaDAO empresaDAO = new EmpresaDAO();
        EmpresaModel empresa = empresaDAO.buscarId(id);

        req.setAttribute("empresa", empresa);

        // encaminha para o JSP de edição
        req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaScaneia/editarEmpresa.jsp")
                .forward(req, res);
    }

    // exclui uma empresa
    private void excluir(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        EmpresaDAO empresaDAO = new EmpresaDAO();

        EmpresaModel emp = new EmpresaModel("", "", "", "");
        emp.setId(id);

        empresaDAO.deletar(emp);

        // redireciona para a lista de empresas
        res.sendRedirect("areaRestrita");
    }

    // exibe os endereços de uma empresa específica
    private void verEnderecos(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        int idEmpresa = Integer.parseInt(req.getParameter("idEmpresa"));
        EnderecoEmpresaDAO enderecoDAO = new EnderecoEmpresaDAO();
        List<EnderecoEmpresaModel> enderecos = enderecoDAO.buscarPorIdEmpresa(idEmpresa);

        req.setAttribute("enderecos", enderecos);

        // encaminha para o JSP de endereços da empresa
        req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaScaneia/enderecosEmpresa.jsp")
                .forward(req, res);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // bloqueia acesso se não houver sessão válida
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("acessoAreaRestrita") == null) {
            res.sendRedirect(req.getContextPath() + "/loginAreaRestrita.jsp");
            return;
        }

        // pega os parâmetros do formulário de atualização
        EmpresaDAO empresaDAO = new EmpresaDAO();
        int id = Integer.parseInt(req.getParameter("id"));
        String nome = req.getParameter("nome");
        String cnpj = req.getParameter("cnpj");
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        // cria objeto da empresa e atualiza via DAO
        EmpresaModel empresa = new EmpresaModel(id, nome, cnpj, senha, email);
        empresaDAO.atualizar(empresa);

        // redireciona para a área restrita
        res.sendRedirect("areaRestrita");
    }
}
