package com.scaneia.ScaneiaServlet.Servlet.AreaRH.OpcoesFuncionario;

import com.scaneia.ScaneiaServlet.DAO.SetorDAO;
import com.scaneia.ScaneiaServlet.DAO.UsuarioDAO;
import com.scaneia.ScaneiaServlet.Model.SetorModel;
import com.scaneia.ScaneiaServlet.Model.UsuarioModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.rmi.ServerError;
import java.util.List;

//Excluir o usuário
@WebServlet(name = "ExcluirUsuario", value = "/areaRH/excluirUsuario")
public class ExcluirUsuarioServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // objetos e variáveis
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        HttpSession httpSession = req.getSession();
        int resultado;
        List<SetorModel> setores;
        SetorDAO setorDAO = new SetorDAO();

        // valida sessão
        if(httpSession == null || httpSession.getAttribute("empresa") == null){
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        // parâmetros da requisição
        String idUsuario = req.getParameter("idUsuario");

        // valida entrada
        try {
            if (!idUsuario.matches("[0-9]+")){
                res.sendRedirect(req.getContextPath() + "/areaRH");
                return;
            }
        } catch (NullPointerException exception){
            res.sendRedirect(req.getContextPath() + "/areaRH");
            return;
        }

        // exclui o usuário
        resultado = usuarioDAO.delete(Integer.parseInt(idUsuario));

        // carrega setores
        setores = setorDAO.listarSetores();
        req.setAttribute("setores", setores);

        // redireciona se houve erro
        if (resultado != 1){
            res.sendRedirect(req.getContextPath() + "/areaRH");
            return;
        }

        // redireciona para a página do usuário
        res.sendRedirect(req.getContextPath() + "/areaRH/EditarFuncionario?id=" + idUsuario);
    }
}
