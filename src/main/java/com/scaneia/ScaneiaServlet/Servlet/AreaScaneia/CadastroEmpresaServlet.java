package com.scaneia.ScaneiaServlet.Servlet.AreaScaneia;

import com.scaneia.ScaneiaServlet.Config.HashSenha;
import com.scaneia.ScaneiaServlet.DAO.EmpresaDAO;
import com.scaneia.ScaneiaServlet.Model.EmpresaModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet(name = "cadastroEmpresa", value = "/areaRestrita/cadastroEmpresa")
public class CadastroEmpresaServlet extends HttpServlet {

    private static final String REGEX_EMAIL = "^[A-Za-z0-9]+([._]?[A-Za-z0-9]+)*@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final String REGEX_CNPJ = "\\d{2}\\.?\\d{3}\\.?\\d{3}/?\\d{4}-?\\d{2}";
    private static final String REGEX_SENHA = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_])\\S{8,}$";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        // Valida sessão de administrador
        if (session == null || session.getAttribute("admin") == null) {
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        // Pega os parâmetros do formulário
        String nome = req.getParameter("nome");
        String cnpj = req.getParameter("cnpj");
        String email = req.getParameter("email");
        String senha= req.getParameter("senha");

        // Validações
        if (nome == null || nome.isBlank()) {
            req.setAttribute("status", 400);
            req.setAttribute("mensagem", "Nome é obrigatório.");
            req.getRequestDispatcher("/WEB-INF/erroCadastroEmpresa.jsp").forward(req, res);
            return;
        }

        if (email == null || !email.matches(REGEX_EMAIL)) {
            req.setAttribute("status", 400);
            req.setAttribute("mensagem", "Formato de e-mail inválido.");
            req.getRequestDispatcher("/WEB-INF/erroCadastroEmpresa.jsp").forward(req, res);
            return;
        }

        if (cnpj == null || !cnpj.matches(REGEX_CNPJ)) {
            req.setAttribute("status", 400);
            req.setAttribute("mensagem", "Formato de CNPJ inválido.");
            req.getRequestDispatcher("/WEB-INF/erroCadastroEmpresa.jsp").forward(req, res);
            return;
        }
        if (senha == null || !senha.matches(REGEX_SENHA)) {
            req.setAttribute("status", 400);
            req.setAttribute("mensagem", "Formato de SENHA inválido.");
            req.getRequestDispatcher("/WEB-INF/erroCadastroEmpresa.jsp").forward(req, res);
            return;
        }else{
            try {
                senha = HashSenha.hashSenha(senha);

            }catch (NoSuchAlgorithmException exception){
                req.setAttribute("status",  500);
                req.setAttribute("mensagem", "Ops... Tente novamente!");
                req.getRequestDispatcher("/WEB-INF/VIEW/erroLoginEmpresa.jsp").forward(req, res);
                return;
            }
        }

        // Normaliza o CNPJ (apenas números)
        cnpj = cnpj.replaceAll("[^0-9]", "");

        // Cria o objeto empresa
        EmpresaModel empresa = new EmpresaModel(nome, cnpj, email,"");

        // Insere no banco
        EmpresaDAO empresaDAO = new EmpresaDAO();
        int resultado = empresaDAO.inserir(empresa);

        // Verifica resultado
        if (resultado == 1) {
            // Sucesso → redireciona para a lista de empresas
            res.sendRedirect(req.getContextPath() + "/areaRestrita");
        } else if (resultado == -2) {
            // Empresa duplicada
            req.setAttribute("status", 409);
            req.setAttribute("mensagem", "Essa empresa já existe!");
            req.getRequestDispatcher("/WEB-INF/erroCadastroEmpresa.jsp").forward(req, res);
        } else {
            // Outro erro
            req.setAttribute("status", 500);
            req.setAttribute("mensagem", "Erro interno. Tente novamente!");
            req.getRequestDispatcher("/WEB-INF/erroCadastroEmpresa.jsp").forward(req, res);
        }
    }
}
