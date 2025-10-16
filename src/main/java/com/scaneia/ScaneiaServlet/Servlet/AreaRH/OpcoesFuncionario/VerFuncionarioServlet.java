package com.scaneia.ScaneiaServlet.Servlet.AreaRH.OpcoesFuncionario;

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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "editarFuncionario", value = "/areaRH/EditarFuncionario")
public class VerFuncionarioServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //variaveis gerais
        UsuarioViewDAO usuarioViewDAO = new UsuarioViewDAO();
        HttpSession httpSession = req.getSession();
        EmpresaModel empresa;
        UsuarioViewModel usuario;
        List<UsuarioViewModel> usuarios = new ArrayList<>();

        //verifica se a sessão é existente
        if (httpSession == null){
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        //pega os parametros da req
        String id = req.getParameter("id");

        //validação de entrada
        try {
            //valida o id
            if (!id.matches("[0-9]+")){
                res.sendRedirect(req.getContextPath() + "/areaRH");
                return;
            }
        }catch (NullPointerException exception){
            res.sendRedirect(req.getContextPath() + "/areaRH");
            return;
        }

        //carrega a empresa
        empresa = (EmpresaModel) httpSession.getAttribute("empresa");

        //carrega o usuario
        usuario = usuarioViewDAO.buscarPorId(empresa.getId(), Integer.parseInt(id));
        usuarios = usuarioViewDAO.buscarPorEmpresa(empresa.getId());

        //respnde para a pagina do usuario
        if (usuario == null){
            res.sendRedirect("/areaRH");
            return;
        }

        req.setAttribute("usuario", usuario);
        req.setAttribute("usuarios", usuarios);
        req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/areaUsuario.jsp").forward(req, res);
    }
}
