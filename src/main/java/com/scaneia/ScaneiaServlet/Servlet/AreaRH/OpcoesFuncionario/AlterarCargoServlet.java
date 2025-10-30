package com.scaneia.ScaneiaServlet.Servlet.AreaRH.OpcoesFuncionario;

import com.scaneia.ScaneiaServlet.Config.ImgConfig;
import com.scaneia.ScaneiaServlet.DAO.SetorDAO;
import com.scaneia.ScaneiaServlet.DAO.UsuarioDAO;
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


@WebServlet(name = "alterarCargo", value = "/areaRH/alterarCargo")
public class AlterarCargoServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        //variaveis gerais
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        HttpSession httpSession = req.getSession();
        int resultado;
        int idCargo = -1;
        List<SetorModel> setores;
        SetorDAO setorDAO = new SetorDAO();
        EmpresaModel empresa;
        UsuarioViewDAO usuarioViewDAO = new UsuarioViewDAO();
        UsuarioViewModel usuario;

        //valida se a sessão existe
        if(httpSession == null || httpSession.getAttribute("empresa") == null){
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }else{
            empresa = (EmpresaModel) httpSession.getAttribute("empresa");
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

        //seta os setores da empresa
        setores = setorDAO.listarSetores();
        req.setAttribute("setores", setores);

        //seta os usuarios da empresa
        usuario = usuarioViewDAO.buscarPorId(empresa.getId(), Integer.parseInt(idUsuario));
        req.setAttribute("usuarios", usuarioViewDAO.buscarPorEmpresa(empresa.getId()));
        req.setAttribute("usuario", usuario);

        //seta a foto do usuario
        req.setAttribute("imagem", ImgConfig.transformarBase64(usuario.getUrlFoto()));

        //saidas do jsp
        if(resultado == -1 || resultado == -3){
            req.setAttribute("mensagem", "Ops... Tente novamente!");
            req.setAttribute("status", 500);
            req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/statusUsuario.jsp").forward(req, res);
        }else if(resultado == 0){
            req.setAttribute("mensagem", "Ops... Esse usuario não existe!");
            req.setAttribute("status", 400);
            req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/statusUsuario.jsp").forward(req, res);
        }else if(resultado == -2){
            req.setAttribute("mensagem", "Ops... Tente novamente!");
            req.setAttribute("status", 500);
            req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/statusUsuario.jsp").forward(req, res);
        }else if(resultado == 1){
            req.setAttribute("mensagem", "Atualizado com sucesso!");
            req.setAttribute("status", 200);
            req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/statusUsuario.jsp").forward(req, res);
        }else{
            req.setAttribute("mensagem", "Ops... Erro nosso, tente novamente!");
            req.setAttribute("status", 500);
            req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/statusUsuario.jsp").forward(req, res);;
        }
    }


}
