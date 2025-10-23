package com.scaneia.ScaneiaServlet.Servlet.AreaScaneia;

import com.scaneia.ScaneiaServlet.DAO.EmpresaDAO;
import com.scaneia.ScaneiaServlet.Model.EmpresaModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "pesquisarEmpresa", value = "/areaRestrita/pesquisarEmpresa")
public class PesquisarEmpresaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        String acao = req.getParameter("acao");
        String nome = req.getParameter("nome");

        EmpresaDAO empresaDAO = new EmpresaDAO();
        List<EmpresaModel> empresas;

        if ("pesquisar".equals(acao) && nome != null && !nome.isBlank()) {
            // pesquisa por nome
            EmpresaModel filtro = new EmpresaModel(nome);
            filtro.setNome(nome);
            empresas = empresaDAO.buscarPorNome(filtro);
        } else {
            // busca todas
            empresas = empresaDAO.buscar();
        }

        req.setAttribute("empresas", empresas);
        req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaScaneia/areaRestritaScaneia.jsp").forward(req, res);

    }
}
