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

@WebServlet(name = "excluirEmpresa", value = "/areaRestrita/excluirEmpresa")
public class ExcluirEmpresaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false); // não cria nova sessão
        if (session == null) {
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        EmpresaDAO empresaDAO = new EmpresaDAO();

        String idParam = req.getParameter("id");
        if (idParam == null || !idParam.matches("[0-9]+")) {
            res.sendRedirect(req.getContextPath() + "/areaRestrita");
            return;
        }

        int id = Integer.parseInt(idParam);

        // Cria o objeto EmpresaModel apenas com o ID, já que o deletar só precisa disso
        EmpresaModel empresa = new EmpresaModel(id, null, null, null);

        int resultado = empresaDAO.deletar(empresa);

        // Redireciona de volta para a listagem
        res.sendRedirect(req.getContextPath() + "/areaRestrita");
    }
}
