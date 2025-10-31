package com.scaneia.ScaneiaServlet.Servlet.AreaRH.Filtro;

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
import java.util.List;

@WebServlet(name = "FiltroPorCargo", value = "/areaRH/filtro")
public class FiltroPorCargoServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        //variaveis gerais
        UsuarioViewDAO usuarioViewDAO = new UsuarioViewDAO();
        List<UsuarioViewModel> usuarios;
        HttpSession httpSession = req.getSession();
        EmpresaModel empresa;
        List<SetorModel> setores;
        SetorDAO setorDAO = new SetorDAO();

        //valida se a sessão existe
        if(httpSession == null || httpSession.getAttribute("empresa") == null){
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        //variaveis da requisição
        String cargo = req.getParameter("cargo");

        //validação de entrada
        try {
            if (!cargo.matches("(operario|chefeDeArea|RH|diretor|todos)")){
                res.sendRedirect(req.getContextPath() + "/areaRH");
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
            res.sendRedirect(req.getContextPath() + "/areaRH");
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

        //carrega os setores
        setores = setorDAO.listarSetores();
        req.setAttribute("setores", setores);

        //responde com jsp
        req.setAttribute("usuarios", usuarios);
        req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/areaRestritaEmpresa.jsp").forward(req, res);
    }
}
