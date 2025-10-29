package com.scaneia.ScaneiaServlet.Servlet.AreaRH.OpcoesFuncionario;

import com.scaneia.ScaneiaServlet.DAO.UsuarioDAO;
import com.scaneia.ScaneiaServlet.Model.EmpresaModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebServlet(name = "alterarCargo", value = "/areaRH/alterarCargo")
public class AlterarCargoServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        //variaveis gerais
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        HttpSession httpSession = req.getSession();
        int resultado;
        int idCargo = -1;

        //valida se a sessão existe
        if(httpSession == null || httpSession.getAttribute("empresa") == null){
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        //variaveis da requisição
        String idUsuario = req.getParameter("idUsuario");
        String novoCargo = req.getParameter("cargo");

        //validação de entrada
        try {
            //valida o id
            if (!idUsuario.matches("[0-9]+")){
                res.sendRedirect(req.getContextPath() + "/areaRH");
                return;
            }

            //valida o nome do cargo
            if (!novoCargo.matches("(Diretor|Chefe de área|RH|Colaborador)")){
                res.sendRedirect(req.getContextPath() + "/areaRH");
                return;
            }

        }catch (NullPointerException exception){
            res.sendRedirect(req.getContextPath() + "/areaRH");
            return;
        }

        //pega o id do cargo
        switch (novoCargo){
            case "Diretor" -> {
                idCargo = 5;
            }
            case "Chefe de área" -> {
                idCargo = 6;
            }
            case "RH" -> {
                idCargo = 7;
            }
            case "Colaborador" -> {
                idCargo = 8;
            }
        }

        //atualiza o idCargo
        resultado = usuarioDAO.updateIdCargo(idCargo, Integer.parseInt(idUsuario));

        //sai da operação caso der erro
        if(resultado == -1 || resultado == -3){
            req.setAttribute("mensagem", "Ops... Tente novamente!");
            req.setAttribute("status", 500);
            req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/statusUsuario.jsp");
            return;
        }else if(resultado == 0){
            req.setAttribute("mensagem", "Ops... Esse usuario não existe!");
            req.setAttribute("status", 400);
            req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/statusUsuario.jsp");
            return;
        }else if(resultado == -2){
            req.setAttribute("mensagem", "Ops... Tente novamente!");
            req.setAttribute("status", 500);
            req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/statusUsuario.jsp");
            return;
        }else if(resultado == 1){
            req.setAttribute("mensagem", "Atualizado com sucesso!");
            req.setAttribute("status", 200);
            req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/statusUsuario.jsp");
            return;
        }

        //responde para a pagina do RH
        res.sendRedirect(req.getContextPath() + "/areaRH");
    }


}
