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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "areaRH", value = "/areaRH")
public class AreaRHServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        //variaveis gerais
        HttpSession httpSession = req.getSession();
        EmpresaModel empresa;

        //valida se tem uma sessao
        if (httpSession == null || (httpSession.getAttribute("empresa") == null && httpSession.getAttribute("admin")
                == null)){
            res.sendRedirect(req.getContextPath() + "/index.html"); // sem parâmetro
            return;
        }else {
            //valida se é admin
            if (httpSession.getAttribute("admin") != null){
                try {
                    httpSession.setAttribute("empresa", new EmpresaModel(
                            Integer.parseInt(req.getParameter("idEmpresa"))
                    ));

                }catch (NullPointerException  | NumberFormatException exception){
                    res.sendRedirect(req.getContextPath() + "/index.html");
                }
            }
        }

        //adiciona a empresa
        empresa = (EmpresaModel) httpSession.getAttribute("empresa");

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
        doGet(req, res); // redireciona para a pagina de login
    }
}
