package com.scaneia.ScaneiaServlet.Servlet.AreaRH.OpcoesFuncionario;

import com.scaneia.ScaneiaServlet.DAO.UsuarioDAO;
import com.scaneia.ScaneiaServlet.Model.EmpresaModel;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebServlet(name = "alterarCargo", value = "/areaRH/alterarCargo")
public class AlterarCargoServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        //variaveis gerais
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        HttpSession httpSession = req.getSession();
        EmpresaModel empresa;
        int resultado;
        int idCargo = -1;

        //variaveis da requisição
        String idUsuario = req.getParameter("idUsuario");
        String novoCargo = req.getParameter("cargo");

        //valida se a sessão existe
        if (httpSession == null){
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

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

        //carrega a empresa
        empresa = (EmpresaModel) httpSession.getAttribute("empresa");

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
        if (resultado != 1){
            res.sendRedirect(req.getContextPath() + "/areaRH");
        }
    }


}
