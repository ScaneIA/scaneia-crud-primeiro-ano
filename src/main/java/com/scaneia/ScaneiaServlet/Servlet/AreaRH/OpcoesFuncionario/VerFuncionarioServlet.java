package com.scaneia.ScaneiaServlet.Servlet.AreaRH.OpcoesFuncionario;

import com.scaneia.ScaneiaServlet.Config.ImgConfig;
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

// Ver/Pesquisar o usuario pelo nome
@WebServlet(name = "editarFuncionario", value = "/areaRH/EditarFuncionario")
public class VerFuncionarioServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // DAO e variáveis gerais
        UsuarioViewDAO usuarioViewDAO = new UsuarioViewDAO();
        HttpSession httpSession = req.getSession();
        EmpresaModel empresa;
        UsuarioViewModel usuario;
        List<SetorModel> setores;
        SetorDAO setorDAO = new SetorDAO();
        List<UsuarioViewModel> usuarios;

        // valida sessão
        if(httpSession == null || httpSession.getAttribute("empresa") == null){
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        // pega parâmetros da requisição
        String id = req.getParameter("id");

        // valida entrada
        try {
            if (!id.matches("[0-9]+")){
                res.sendRedirect(req.getContextPath() + "/areaRH");
                return;
            }
        } catch (NullPointerException exception){
            // tenta pegar id de atributo
            if (req.getAttribute("id") != null){
                id = (String) req.getAttribute("id");
            } else {
                res.sendRedirect(req.getContextPath() + "/areaRH");
                return;
            }
        }

        // carrega empresa
        empresa = (EmpresaModel) httpSession.getAttribute("empresa");

        // carrega usuário e lista de usuários da empresa
        usuario = usuarioViewDAO.buscarPorId(empresa.getId(), Integer.parseInt(id));
        usuarios = usuarioViewDAO.buscarPorEmpresa(empresa.getId());

        // verifica se usuário existe
        if (usuario == null){
            res.sendRedirect(req.getContextPath()+ "/areaRH");
            return;
        }

        // carrega setores
        setores = setorDAO.listarSetores();
        req.setAttribute("setores", setores);

        // encaminha dados para a página do usuário
        req.setAttribute("imagem", ImgConfig.transformarBase64(usuario.getUrlFoto()));
        req.setAttribute("usuario", usuario);
        req.setAttribute("usuarios", usuarios);
        req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/areaUsuario.jsp").forward(req, res);
    }
}
