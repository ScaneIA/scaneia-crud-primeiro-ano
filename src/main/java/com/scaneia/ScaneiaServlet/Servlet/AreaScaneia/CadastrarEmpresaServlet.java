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

// Cadastro de uma empresa totalmente nova
@WebServlet(name = "cadastroEmpresa", value = "/areaRestrita/cadastroEmpresa")
public class CadastrarEmpresaServlet extends HttpServlet {

    // Regex para validar campos
    private static final String REGEX_EMAIL = "^[A-Za-z0-9]+([._]?[A-Za-z0-9]+)*@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final String REGEX_CNPJ = "\\d{2}\\.?\\d{3}\\.?\\d{3}/?\\d{4}-?\\d{2}";
    private static final String REGEX_SENHA = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_])\\S{8,}$";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // Pega sessão existente, não cria nova
        HttpSession session = req.getSession(false);
        boolean erro = false;

        // Valida se o usuário é administrador
        if (session == null || session.getAttribute("admin") == null) {
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;       }

        // Captura os parâmetros do formulário
        String nome = req.getParameter("nome");
        String cnpj = req.getParameter("cnpj");
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        // Valida o campo nome
        if (nome == null || nome.isBlank()) {
            erro = true;
        }

        // Valida o formato do email
        if (email == null || !email.matches(REGEX_EMAIL)) {
            erro = true;
        }

        // Valida o formato do CNPJ
        if (cnpj == null || !cnpj.matches(REGEX_CNPJ)) {
            erro = true;
        }

        // Valida o formato da senha
        if (senha == null || !senha.matches(REGEX_SENHA)) {
            erro = true;
        } else {
            try {
                // Gera hash da senha
                senha = HashSenha.hashSenha(senha);
            } catch (NoSuchAlgorithmException exception) {
                res.sendRedirect(req.getContextPath() + "/areaRestrita");
                return;
            }
        }

        //retorna se teve erro
        if (erro){
            res.sendRedirect(req.getContextPath() + "/areaRestrita");
            return;
        }

        // Deixa só os núemros no CNPJ
        cnpj = cnpj.replaceAll("[^0-9]", "");

        // Cria objeto da empresa
        EmpresaModel empresa = new EmpresaModel(nome, cnpj, email, "");

        // Instancia DAO e insere no banco
        EmpresaDAO empresaDAO = new EmpresaDAO();
        int resultado = empresaDAO.inserir(empresa);

        // Verifica resultado da inserção
        res.sendRedirect(req.getContextPath() + "/areaRestrita");
    }
}
