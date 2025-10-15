package com.scaneia.ScaneiaServlet.Servlet.AreaRH.Filtro;

import com.scaneia.ScaneiaServlet.DAO.UsuarioViewDAO;
import com.scaneia.ScaneiaServlet.Model.EmpresaModel;
import com.scaneia.ScaneiaServlet.Model.UsuarioViewModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "FiltroPorCargo", value = "/areaRH/filtro")
public class FiltroPorCargoServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        //variaveis gerais
        UsuarioViewDAO usuarioViewDAO = new UsuarioViewDAO();
        List<UsuarioViewModel> usuarios;
        HttpSession httpSession = req.getSession();
        EmpresaModel empresa;

        //variaveis da requisição
        String cargo = req.getParameter("cargo");

        //verifica se a sessao existe
        if(httpSession == null){
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        //validação de entrada
        try {
            if (!cargo.matches("(operario|chefeDeArea|RH|diretor|todos)")){
                req.setAttribute("status", 400);
                req.setAttribute("mensagem", "Cargo inválido!");
                req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/erro.jsp"); //fazer jsp
                return;
            }else{
                //deixa o cargo igual o banco
                switch (cargo){
                    case "operario" -> {
                        cargo = "Colaborador";
                    }
                    case "chefeDeArea" -> {
                        cargo = "Chefe de área";
                    }
                    case "diretor" -> {
                        cargo = "Diretor";
                    }
                }
            }

        }catch (NullPointerException exception){
            req.setAttribute("status", 400);
            req.setAttribute("mensagem", "Insira todos os campos");
            req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/erro.jsp"); //fazer jsp
            return;
        }

        //verifica se são todos os filtros
        if (cargo.equals("todos")){
            res.sendRedirect(req.getContextPath() + "/areaRH");
            return;
        }

        //carrega a empresa
        empresa = (EmpresaModel) httpSession.getAttribute("empresa");

        //carrega os usuarios
        usuarios = usuarioViewDAO.filtrarPorCargo(cargo, empresa.getId());

        //responde com jsp
        req.setAttribute("usuarios", usuarios);
        req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/areaRestritaEmpresa.jsp").forward(req, res);
    }
}
