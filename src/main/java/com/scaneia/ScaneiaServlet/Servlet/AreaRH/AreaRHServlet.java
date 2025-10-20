package com.scaneia.ScaneiaServlet.Servlet.AreaRH;

import com.scaneia.ScaneiaServlet.DAO.EmpresaDAO;
import com.scaneia.ScaneiaServlet.DAO.UsuarioViewDAO;
import com.scaneia.ScaneiaServlet.Model.EmpresaModel;
import com.scaneia.ScaneiaServlet.Model.UsuarioViewModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "areaRH", value = "/areaRH")
public class AreaRHServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        // Recebe o idEmpresa do parâmetro
        String idEmpresaParam = req.getParameter("idEmpresa");
        if (idEmpresaParam == null || idEmpresaParam.isEmpty()) {
            res.sendRedirect(req.getContextPath() + "/index.html"); // sem parâmetro
            return;
        }

        int idEmpresa;
        try {
            idEmpresa = Integer.parseInt(idEmpresaParam);
        } catch (NumberFormatException e) {
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        // Busca a empresa no banco
        EmpresaDAO empresaDAO = new EmpresaDAO();
        EmpresaModel empresa = empresaDAO.buscarId(idEmpresa);

        if (empresa == null) {
            res.sendRedirect(req.getContextPath() + "/index.html"); // empresa não encontrada
            return;
        }

        // Busca os usuários da empresa
        UsuarioViewDAO usuarioViewDAO = new UsuarioViewDAO();
        List<UsuarioViewModel> usuarios = usuarioViewDAO.buscarPorEmpresa(empresa.getId());

        // Envia para o JSP
        req.setAttribute("empresa", empresa);
        req.setAttribute("usuarios", usuarios);

        req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/areaRestritaEmpresa.jsp")
                .forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        doGet(req, res); // redireciona post para get
    }
}
