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

// Alterar/atualizar o cargo do usuário
@WebServlet(name = "alterarCargo", value = "/areaRH/alterarCargo")
public class AlterarCargoServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        // Variáveis principais
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        HttpSession httpSession = req.getSession();
        int resultado;
        int idCargo = -1;
        List<SetorModel> setores;
        SetorDAO setorDAO = new SetorDAO();
        EmpresaModel empresa;
        UsuarioViewDAO usuarioViewDAO = new UsuarioViewDAO();
        UsuarioViewModel usuario;

        // Verifica se a sessão existe
        if (httpSession == null || httpSession.getAttribute("empresa") == null) {
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        } else {
            empresa = (EmpresaModel) httpSession.getAttribute("empresa");
        }

        // Pega os dados do formulário
        String idUsuario = req.getParameter("idUsuario");
        String novoCargo = req.getParameter("cargo");

        // Valida entradas
        try {
            if (!idUsuario.matches("[0-9]+")) { // ID deve ser número
                res.sendRedirect(req.getContextPath() + "/areaRH");
                return;
            }

            if (!novoCargo.matches("(Diretor|Chefe de área|RH|Colaborador)")) { // Cargo permitido
                res.sendRedirect(req.getContextPath() + "/areaRH");
                return;
            }

        } catch (NullPointerException exception) {
            res.sendRedirect(req.getContextPath() + "/areaRH");
            return;
        }

        // Define o id do cargo conforme o nome
        switch (novoCargo) {
            case "Diretor" -> idCargo = 5;
            case "Chefe de área" -> idCargo = 6;
            case "RH" -> idCargo = 7;
            case "Colaborador" -> idCargo = 8;
        }

        // Atualiza o cargo no banco
        resultado = usuarioDAO.updateIdCargo(idCargo, Integer.parseInt(idUsuario));

        // Carrega setores e usuários
        setores = setorDAO.listarSetores();
        req.setAttribute("setores", setores);

        usuario = usuarioViewDAO.buscarPorId(empresa.getId(), Integer.parseInt(idUsuario));
        req.setAttribute("usuarios", usuarioViewDAO.buscarPorEmpresa(empresa.getId()));
        req.setAttribute("usuario", usuario);

        // Define a imagem do usuário
        req.setAttribute("imagem", ImgConfig.transformarBase64(usuario.getUrlFoto()));

        // Retorna mensagens conforme o resultado
        if (resultado == -1 || resultado == -3) {
            req.setAttribute("mensagem", "Ops... Tente novamente!");
            req.setAttribute("status", 500);
            req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/statusUsuario.jsp").forward(req, res);
        } else if (resultado == 0) {
            req.setAttribute("mensagem", "Ops... Esse usuário não existe!");
            req.setAttribute("status", 400);
            req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/statusUsuario.jsp").forward(req, res);
        } else if (resultado == -2) {
            req.setAttribute("mensagem", "Ops... Tente novamente!");
            req.setAttribute("status", 500);
            req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/statusUsuario.jsp").forward(req, res);
        } else if (resultado == 1) {
            req.setAttribute("mensagem", "Atualizado com sucesso!");
            req.setAttribute("status", 200);
            req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/statusUsuario.jsp").forward(req, res);
        } else {
            req.setAttribute("mensagem", "Ops... Erro nosso, tente novamente!");
            req.setAttribute("status", 500);
            req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/statusUsuario.jsp").forward(req, res);
        }
    }
}
