package com.scaneia.ScaneiaServlet.Servlet.AreaRH.OpcoesFuncionario;

import com.scaneia.ScaneiaServlet.DAO.SetorDAO;
import com.scaneia.ScaneiaServlet.DAO.UsuarioDAO;
import com.scaneia.ScaneiaServlet.Model.SetorModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "AlterarEmail", value = "/areaRH/alterarEmail")
public class AlterarEmailServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        //variaveis gerais
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        HttpSession httpSession = req.getSession();
        int resultado;
        List<SetorModel> setores;
        SetorDAO setorDAO = new SetorDAO();

        //valida se a sessão existe
        if(httpSession == null || httpSession.getAttribute("empresa") == null){
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        //variaveis da reqisição
        String idUsuario = req.getParameter("idUsuario");
        String novoEmail = req.getParameter("email");


        //validação de entrada
        try {
            //valida o id
            if (!idUsuario.matches("[0-9]+")){
                res.sendRedirect(req.getContextPath() + "/areaRH");
                return;
            }

            //valida o email
            if (!novoEmail.matches("^[A-Za-z0-9]+([._]?[A-Za-z0-9]+)*@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){
                res.sendRedirect(req.getContextPath() + "/areaRH");
                return;
            }

        }catch (NullPointerException exception){
            res.sendRedirect(req.getContextPath() + "/areaRH");
            return;
        }

        //atualiza o email
        resultado = usuarioDAO.alterarEmail(novoEmail, Integer.parseInt(idUsuario));

        //seta os setores da empresa
        setores = setorDAO.listarSetores();
        req.setAttribute("setores", setores);

        if(resultado != 1){
            res.sendRedirect(req.getContextPath() + "/areaRH");
            return;
        }

        //responde para a mesma pagina
        res.sendRedirect(req.getContextPath() + "/areaRH/EditarFuncionario?id=" + idUsuario);
    }
}
