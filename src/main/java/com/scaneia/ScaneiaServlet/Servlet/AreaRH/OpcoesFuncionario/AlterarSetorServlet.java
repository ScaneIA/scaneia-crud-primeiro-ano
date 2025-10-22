package com.scaneia.ScaneiaServlet.Servlet.AreaRH.OpcoesFuncionario;

import com.scaneia.ScaneiaServlet.DAO.SetorDAO;
import com.scaneia.ScaneiaServlet.DAO.UsuarioDAO;
import com.scaneia.ScaneiaServlet.Model.EmpresaModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "AlterarSetor", value = "/areaRH/alterarSetor")
public class AlterarSetorServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        //variaveis gerais
        SetorDAO setorDAO = new SetorDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        HttpSession httpSession = req.getSession();
        int idSetor;
        int resultado;

        //valida se a sessão existe
        if(httpSession == null || httpSession.getAttribute("empresa") == null){
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        //variaveis da requisição
        String novoSetor = req.getParameter("setor");
        String idUsuario = req.getParameter("idUsuario");


        //validação de entrada
        try {
            //valida o novo setor
            if (!novoSetor.matches("^[a-zA-ZáàâãäéèêëíïîóôõöúüçñÁÀÂÃÄÉÈÊËÍÏÎÓÔÕÖÚÜÇÑ ]+$")) {
                res.sendRedirect(req.getContextPath() + "/areaRH");
                return;
            }

            //valida o id do usuario
            if (!idUsuario.matches("[0-9]+")) {
                res.sendRedirect(req.getContextPath() + "/areaRH");
                return;
            }

        } catch (NullPointerException exception) {
            res.sendRedirect(req.getContextPath() + "/areaRH");
            return;
        }

        //pega o id do setor novo
        idSetor = setorDAO.descobrirId(novoSetor);

        //valida se deu erro
        if (idSetor < 0){
            res.sendRedirect(req.getContextPath() + "/areaRH");
            return;
        }

        //atualiza o setor
        resultado = usuarioDAO.alterarIdSetor(idSetor, Integer.parseInt(idUsuario));

        //valida se deu certo
        if (resultado == 1){
            //responde para a mesma pagina
            res.sendRedirect(req.getContextPath() + "/areaRH/EditarFuncionario?id=" + idUsuario);
            return;
        }else{
            //responde para a pagina do RH
            res.sendRedirect(req.getContextPath() + "/areaRH");
            return;
        }

    }
}
