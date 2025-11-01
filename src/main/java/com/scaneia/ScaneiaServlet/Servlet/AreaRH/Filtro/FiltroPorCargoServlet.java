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

// Com esse servlet conseguimos filtrar por cargos, dessa forma o usuário consegue encontrar informações com maior facilidade
// Visualmente é apresentada um lista de opções, o usuário escolhe uma, então é filtrada
@WebServlet(name = "FiltroPorCargo", value = "/areaRH/filtro")
public class FiltroPorCargoServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        // variáveis principais
        UsuarioViewDAO usuarioViewDAO = new UsuarioViewDAO();
        List<UsuarioViewModel> usuarios;
        HttpSession httpSession = req.getSession();
        EmpresaModel empresa;
        List<SetorModel> setores;
        SetorDAO setorDAO = new SetorDAO();

        // verifica se a sessão existe
        if(httpSession == null || httpSession.getAttribute("empresa") == null){
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        // recebe o cargo da requisição
        String cargo = req.getParameter("cargo");

        // carrega os setores
        setores = setorDAO.listarSetores();
        req.setAttribute("setores", setores);

        // valida o parâmetro recebido
        try {
            if (!cargo.matches("(operario|chefeDeArea|RH|diretor|todos)")){
                res.sendRedirect(req.getContextPath() + "/areaRH");
                return;
            }else{
                // ajusta o nome do cargo conforme o banco
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

        // se o filtro for "todos", retorna à página principal
        if (cargo.equals("todos")){
            res.sendRedirect(req.getContextPath() + "/areaRH");
            return;
        }

        // pega a empresa da sessão
        empresa = (EmpresaModel) httpSession.getAttribute("empresa");

        // busca usuários filtrados por cargo e empresa
        usuarios = usuarioViewDAO.filtrarPorCargo(cargo, empresa.getId());

        // envia os dados para o JSP
        req.setAttribute("usuarios", usuarios);
        req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/areaRestritaEmpresa.jsp").forward(req, res);
    }
}
