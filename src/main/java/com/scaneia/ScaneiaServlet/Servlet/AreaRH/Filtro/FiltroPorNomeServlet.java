package com.scaneia.ScaneiaServlet.Servlet.AreaRH.Filtro;

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

@WebServlet(name = "FiltroPorNome", value = "/areaRH/nome")
public class FiltroPorNomeServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        //variaveis gerais
        UsuarioViewDAO usuarioViewDAO = new UsuarioViewDAO();
        List<UsuarioViewModel> usuarios;
        HttpSession httpSession = req.getSession();
        EmpresaModel empresa;

        //variaveis da requisição
        String nome = req.getParameter("nome");

        //verifica se a sessao existe
        if(httpSession == null){
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        //validação de entrada
        try {
            if (!nome.matches("^[A-Za-zÀ-ÖØ-öø-ÿ]+(?:\\s+[A-Za-zÀ-ÖØ-öø-ÿ]+)*$")){
                res.sendRedirect(req.getContextPath() + "/areaRH");
                return;
            }

        }catch (NullPointerException exception){
            res.sendRedirect(req.getContextPath() + "/areaRH");
            return;
        }

        //carrega a empresa
        empresa = (EmpresaModel) httpSession.getAttribute("empresa");

        //carrega os usuarios
        usuarios = usuarioViewDAO.filtrarPorNome(nome, empresa.getId());

        //responde com jsp
        req.setAttribute("usuarios", usuarios);
        req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/areaRestritaEmpresa.jsp").forward(req, res);
    }
}
