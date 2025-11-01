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

// Alterar/atualizar o CPF do usuário
@WebServlet(name = "AlterarCpf", value = "/areaRH/alterarCpf")
public class AlterarCpfServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Variávies principais
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        HttpSession httpSession = req.getSession();
        int resultado;
        List<SetorModel> setores;
        SetorDAO setorDAO = new SetorDAO();
        EmpresaModel empresa;
        UsuarioViewDAO usuarioViewDAO = new UsuarioViewDAO();
        UsuarioViewModel usuario;

        // Verifica se a sessão é válida
        if (httpSession == null || httpSession.getAttribute("empresa") == null) {
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        } else {
            empresa = (EmpresaModel) httpSession.getAttribute("empresa");
        }

        // Dados enviados pelo formulário
        String novoCpf = req.getParameter("cpf");
        String idUsuario = req.getParameter("idUsuario");

        // Validação dos dados
        try {
            // Verifica formato do CPF
            if (!novoCpf.matches("^\\s*\\d{3}\\s*[.\\-]?\\s*\\d{3}\\s*[.\\-]?\\s*\\d{3}\\s*[.\\-]?\\s*\\d{2}\\s*$")) {
                res.sendRedirect(req.getContextPath() + "/areaRH");
                return;
            } else {
                // Mantém apenas os números
                novoCpf = novoCpf.replaceAll("[^0-9]", "");
            }

            // Verifica se o ID é numérico
            if (!idUsuario.matches("[0-9]+")) {
                res.sendRedirect(req.getContextPath() + "/areaRH");
                return;
            }

        } catch (NullPointerException exception) {
            res.sendRedirect(req.getContextPath() + "/areaRH");
            return;
        }

        // Carrega setores
        setores = setorDAO.listarSetores();
        req.setAttribute("setores", setores);

        // Atualiza o CPF
        resultado = usuarioDAO.alterarCpf(novoCpf, Integer.parseInt(idUsuario));

        // Carrega dados do usuário
        usuario = usuarioViewDAO.buscarPorId(empresa.getId(), Integer.parseInt(idUsuario));
        req.setAttribute("usuarios", usuarioViewDAO.buscarPorEmpresa(empresa.getId()));
        req.setAttribute("usuario", usuario);

        // Define a imagem do usuário
        req.setAttribute("imagem", ImgConfig.transformarBase64(usuario.getUrlFoto()));

        // Resposta conforme o resultado
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
