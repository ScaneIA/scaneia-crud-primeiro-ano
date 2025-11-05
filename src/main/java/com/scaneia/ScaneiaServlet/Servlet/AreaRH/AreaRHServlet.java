package com.scaneia.ScaneiaServlet.Servlet.AreaRH;


import com.scaneia.ScaneiaServlet.DAO.EmpresaDAO;
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
import java.util.ArrayList;
import java.util.List;
// Página principal do CRUD de usuários.
// Exibe os usuários de cada empresa com suas respectivas informações.
// Nesta página ocorre a integração de três CRUDs/Servlets em um único ponto,
// combinando as tabelas de Usuário, Setor e Cargo, o que facilita a visualização e compreensão dos dados pelo usuário.

// qualquer dúvida sobre o comentário acima acesse a documentação

@WebServlet(name = "areaRH", value = "/areaRH")
public class AreaRHServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        // sessão e variáveis gerais
        HttpSession httpSession = req.getSession();
        EmpresaModel empresa;
        SetorDAO setorDAO = new SetorDAO();
        List<SetorModel> setores;

        // valida sessão
        if (httpSession == null || (httpSession.getAttribute("empresa") == null && httpSession.getAttribute("admin") == null)) {
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        //valida se é admin em nova area
        if(req.getParameter("idEmpresa") != null && httpSession.getAttribute("admin") != null){
            httpSession.setAttribute("empresa", new EmpresaModel(
                    Integer.parseInt(req.getParameter("idEmpresa"))
            ));
        }

        // pega a empresa da sessão
        empresa = (EmpresaModel) httpSession.getAttribute("empresa");

        // busca usuários da empresa
        UsuarioViewDAO usuarioViewDAO = new UsuarioViewDAO();
        List<UsuarioViewModel> usuarios = usuarioViewDAO.buscarPorEmpresa(empresa.getId());

        // carrega setores
        setores = setorDAO.listarSetores();

        // envia dados para o JSP
        req.setAttribute("empresa", empresa);
        req.setAttribute("usuarios", usuarios);
        req.setAttribute("setores", setores);

        req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/areaRestritaEmpresa.jsp")
                .forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        doGet(req, res); // redireciona para a mesma página
    }
}
