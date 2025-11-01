package com.scaneia.ScaneiaServlet.Servlet.AreaRH.Filtro;

import com.scaneia.ScaneiaServlet.DAO.SetorDAO;
import com.scaneia.ScaneiaServlet.DAO.UsuarioViewDAO;
import com.scaneia.ScaneiaServlet.Model.EmpresaModel;
import com.scaneia.ScaneiaServlet.Model.SetorModel;
import com.scaneia.ScaneiaServlet.Model.UsuarioViewModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

// Com esse servlet conseguimos filtrar por nomes dessa forma o usuário consegue encontrar informações com maior facilidade

@WebServlet(name = "FiltroPorNome", value = "/areaRH/nome")
public class FiltroPorNomeServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        // variáveis principais
        UsuarioViewDAO usuarioViewDAO = new UsuarioViewDAO();
        List<UsuarioViewModel> usuarios;
        HttpSession httpSession = req.getSession();
        EmpresaModel empresa;
        List<SetorModel> setores;
        SetorDAO setorDAO = new SetorDAO();

        // verifica se a sessão existe
        if (httpSession == null || httpSession.getAttribute("empresa") == null) {
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        // recebe o nome da requisição
        String nome = req.getParameter("nome");

        // valida o parâmetro recebido
        try {
            if (!nome.matches("^[A-Za-zÀ-ÖØ-öø-ÿ]+(?:\\s+[A-Za-zÀ-ÖØ-öø-ÿ]+)*$")) {
                res.sendRedirect(req.getContextPath() + "/areaRH");
                return;
            } else {
                // transforma o nome em minúsculo
                nome = nome.toLowerCase();
            }
        } catch (NullPointerException exception) {
            res.sendRedirect(req.getContextPath() + "/areaRH");
            return;
        }

        // pega a empresa da sessão
        empresa = (EmpresaModel) httpSession.getAttribute("empresa");

        // busca os usuários filtrados por nome
        usuarios = usuarioViewDAO.filtrarPorNome(nome, empresa.getId());

        // carrega os setores
        setores = setorDAO.listarSetores();
        req.setAttribute("setores", setores);

        // envia os dados para o JSP
        req.setAttribute("usuarios", usuarios);
        req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/areaRestritaEmpresa.jsp").forward(req, res);
    }
}
