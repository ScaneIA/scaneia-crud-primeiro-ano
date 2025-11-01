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

// Alterar/atualizar o setor do usuário
@WebServlet(name = "AlterarSetor", value = "/areaRH/alterarSetor")
public class AlterarSetorServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        //objetos DAO e variáveis
        SetorDAO setorDAO = new SetorDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        HttpSession httpSession = req.getSession();
        int idSetor;
        int resultado;
        List<SetorModel> setores;
        UsuarioViewDAO usuarioViewDAO = new UsuarioViewDAO();
        EmpresaModel empresa;
        UsuarioViewModel usuario;

        //verifica sessão
        if(httpSession == null || httpSession.getAttribute("empresa") == null){
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }else{
            empresa = (EmpresaModel) httpSession.getAttribute("empresa");
        }

        //recebe dados da requisição
        String novoSetor = req.getParameter("setor");
        String idUsuario = req.getParameter("idUsuario");

        //validação de entrada
        try {
            //valida o nome do setor
            if (!novoSetor.matches("^[A-Za-zÀ-ÖØ-öø-ÿ0-9.,!?\\s'\"\\-():;]+$")) {
                res.sendRedirect(req.getContextPath() + "/areaRH");
                return;
            }

            //valida o id do usuário
            if (!idUsuario.matches("[0-9]+")) {
                res.sendRedirect(req.getContextPath() + "/areaRH");
                return;
            }

        } catch (NullPointerException exception) {
            res.sendRedirect(req.getContextPath() + "/areaRH");
            return;
        }

        //pega o id do setor
        idSetor = setorDAO.descobrirId(novoSetor);

        //verifica se encontrou o setor
        if (idSetor < 0){
            res.sendRedirect(req.getContextPath() + "/areaRH");
            return;
        }

        //atualiza setor do usuário
        resultado = usuarioDAO.alterarIdSetor(idSetor, Integer.parseInt(idUsuario));

        //carrega setores
        setores = setorDAO.listarSetores();
        req.setAttribute("setores", setores);

        //carrega usuários
        usuario = usuarioViewDAO.buscarPorId(empresa.getId(), Integer.parseInt(idUsuario));
        req.setAttribute("usuarios", usuarioViewDAO.buscarPorEmpresa(empresa.getId()));
        req.setAttribute("usuario", usuario);

        //imagem do usuário
        req.setAttribute("imagem", ImgConfig.transformarBase64(usuario.getUrlFoto()));

        //resposta para JSP
        if(resultado == -1){
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
            req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/statusUsuario.jsp").forward(req, res);
        }

    }
}
