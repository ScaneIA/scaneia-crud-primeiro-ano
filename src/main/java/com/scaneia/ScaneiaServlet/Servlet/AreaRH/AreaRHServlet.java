package com.scaneia.ScaneiaServlet.Servlet.AreaRH;

import com.scaneia.ScaneiaServlet.DAO.UsuarioViewDAO;
import com.scaneia.ScaneiaServlet.Model.EmpresaModel;
import com.scaneia.ScaneiaServlet.Model.UsuarioViewModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "areaRH", value = "/areaRH")
public class AreaRHServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        //variaveis gerais
        UsuarioViewDAO usuarioViewDAO = new UsuarioViewDAO();
        List<UsuarioViewModel> usuarios;
        HttpSession httpSession = req.getSession(false);
        EmpresaModel empresa;

        //verifica se a sessão é existente
        if (httpSession == null){
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        //carrega a empresa
        empresa = (EmpresaModel) httpSession.getAttribute("empresa");

        //carrega os usuarios
        usuarios = usuarioViewDAO.buscarPorEmpresa(empresa.getId());

        //responde com o jsp
        req.setAttribute("usuarios", usuarios);
        req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/areaRestritaEmpresa.jsp").forward(req, res);
    }
}
