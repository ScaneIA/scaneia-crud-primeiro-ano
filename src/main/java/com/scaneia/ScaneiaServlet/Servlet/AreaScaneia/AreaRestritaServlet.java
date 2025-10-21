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

@WebServlet("/areaRestrita")
public class AreaRestritaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        // Verifica se tem sessao
        HttpSession session = req.getSession(false);

        if (!(session != null && session.getAttribute("admin") != null)){
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }



        //entrega o servlet
        EmpresaDAO empresaDAO = new EmpresaDAO();
        EnderecoEmpresaDAO enderecoDAO = new EnderecoEmpresaDAO();

        String acao = req.getParameter("acao");
        if (acao == null) acao = "listar";

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

    private void listar(HttpServletRequest req, HttpServletResponse res,
                        EmpresaDAO empresaDAO, EnderecoEmpresaDAO enderecoDAO)
            throws ServletException, IOException {

        List<EmpresaModel> empresas = empresaDAO.buscar();
        req.setAttribute("empresas", empresas);
        req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaScaneia/areaRestritaScaneia.jsp")
                .forward(req, res);
    }

    private void pesquisar(HttpServletRequest req, HttpServletResponse res,
                           EmpresaDAO empresaDAO, EnderecoEmpresaDAO enderecoDAO)
            throws ServletException, IOException {

        String nome = req.getParameter("nome");
        EmpresaModel emp = new EmpresaModel(nome, "", "", "");
        List<EmpresaModel> empresas = empresaDAO.buscarPorNome(emp);
        List<EnderecoEmpresaModel> enderecos = enderecoDAO.buscar();

        req.setAttribute("empresas", empresas);
        req.setAttribute("enderecos", enderecos);
        req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaScaneia/areaRestritaScaneia.jsp")
                .forward(req, res);
    }

    private void editar(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        EmpresaDAO empresaDAO = new EmpresaDAO();
        EmpresaModel empresa = empresaDAO.buscarId(id);
        req.setAttribute("empresa", empresa);

        req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaScaneia/editarEmpresa.jsp")
                .forward(req, res);
    }

    private void excluir(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        EmpresaDAO empresaDAO = new EmpresaDAO();
        EmpresaModel emp = new EmpresaModel("", "", "", "");
        emp.setId(id);
        empresaDAO.deletar(emp);
        res.sendRedirect("areaRestrita");
    }
    private void verEnderecos(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        int idEmpresa = Integer.parseInt(req.getParameter("idEmpresa"));
        EnderecoEmpresaDAO enderecoDAO = new EnderecoEmpresaDAO();
        List<EnderecoEmpresaModel> enderecos = enderecoDAO.buscarPorIdEmpresa(idEmpresa);
        req.setAttribute("enderecos", enderecos);
        req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaScaneia/enderecosEmpresa.jsp")
                .forward(req, res);
        System.out.println("ID da empresa recebido: " + idEmpresa);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        // Bloqueio de acesso
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("acessoAreaRestrita") == null) {
            res.sendRedirect(req.getContextPath() + "/loginAreaRestrita.jsp");
            return;
        }
        EmpresaDAO empresaDAO = new EmpresaDAO();

        int id = Integer.parseInt(req.getParameter("id"));
        String nome = req.getParameter("nome");
        String cnpj = req.getParameter("cnpj");
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        EmpresaModel empresa = new EmpresaModel(id, nome, cnpj, senha, email);
        empresaDAO.atualizar(empresa);

        res.sendRedirect("areaRestrita");
    }
}
