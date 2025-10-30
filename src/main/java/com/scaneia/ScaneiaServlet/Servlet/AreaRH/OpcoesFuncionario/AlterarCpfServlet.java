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
import java.util.List;

@WebServlet(name = "AlterarCpf", value = "/areaRH/alterarCpf")
public class AlterarCpfServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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

        //variaveis da requisição
        String novoCpf = req.getParameter("cpf");
        String idUsuario = req.getParameter("idUsuario");


        //validação de entrada
        try {
            //valida o cpf
            if (!novoCpf.matches("^\\s*\\d{3}\\s*[.\\-]?\\s*\\d{3}\\s*[.\\-]?\\s*\\d{3}\\s*[.\\-]?\\s*\\d{2}\\s*$")){
                res.sendRedirect(req.getContextPath() + "/areaRH");
                return;
            }else {
                //remove os numeros
                novoCpf = novoCpf.replaceAll("[^0-9]", "");
            }

            //valida o id
            if (!idUsuario.matches("[0-9]+")){
                res.sendRedirect(req.getContextPath() + "/areaRH");
                return;
            }

        }catch (NullPointerException exception){
            res.sendRedirect(req.getContextPath() + "/areaRH");
            return;
        }

        //seta os setores da empresa
        setores = setorDAO.listarSetores();
        req.setAttribute("setores", setores);

        //altera o cpf
        resultado = usuarioDAO.alterarCpf(novoCpf, Integer.parseInt(idUsuario));

        if (resultado != 1){
            res.sendRedirect(req.getContextPath() + "/areaRH");
            return;
        }

        //responde para a mesma pagina
        res.sendRedirect(req.getContextPath() + "/areaRH/EditarFuncionario?id=" + idUsuario);
    }
}
