package com.scaneia.ScaneiaServlet.Servlet.AreaRH.OpcoesFuncionario;

import com.scaneia.ScaneiaServlet.DAO.UsuarioDAO;
import com.scaneia.ScaneiaServlet.Model.UsuarioModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.rmi.ServerError;

@WebServlet(name = "ExcluirUsuario", value = "/areaRH/excluirUsuario")
public class ExcluirUsuarioServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //variaveis gerais
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        HttpSession httpSession = req.getSession();
        int resultado;

        //variaveis da reqisição
        String idUsuario = req.getParameter("idUsuario");

        //valida se tem cadastro
        if (httpSession == null){
            res.sendRedirect(req.getContextPath() + "/areaRH");
            return;
        }

        //validação de entrada
        try {
            //valida o id
            if (!idUsuario.matches("[0-9]+")){
                res.sendRedirect(req.getContextPath() + "/areaRH");
                return;
            }

        }catch (NullPointerException exception){
            res.sendRedirect(req.getContextPath() + "/areaRH");
            return;
        }

        //exclui o usuario
        resultado = usuarioDAO.delete(Integer.parseInt(idUsuario));

        if (resultado != 1){
            res.sendRedirect(req.getContextPath() + "/areaRH");
            return;
        }

        //responde para a mesma pagina
        res.sendRedirect(req.getContextPath() + "/areaRH/EditarFuncionario?id=" + idUsuario);
    }
}
