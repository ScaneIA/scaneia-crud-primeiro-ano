package com.scaneia.ScaneiaServlet.Servlet.AreaScaneia;

import com.scaneia.ScaneiaServlet.DAO.EmpresaDAO;
import com.scaneia.ScaneiaServlet.Model.EmpresaModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

// Servlet responsável por alterar/atualizar os dados da empresa.
// Todas as atualizações como nome, CNPJ e e-mail são tratadas aqui,
// permitindo modificar um ou mais campos conforme a solicitação do usuário.

@WebServlet(name = "alterarEmpresa", value = "/areaRestrita/alterarEmpresa")
public class AlterarEmpresaServlet extends HttpServlet {

    private static final String REGEX_EMAIL = "^[A-Za-z0-9]+([._]?[A-Za-z0-9]+)*@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final String REGEX_CNPJ = "^\\d{2}[. -/]?\\d{3}[. -/]?\\d{3}[. -/]?\\d{4}[. -/]?\\d{2}$";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // pega a sessão sem criar nova
        HttpSession session = req.getSession(false);
        if (session == null) {
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        EmpresaDAO empresaDAO = new EmpresaDAO();

        // pega parâmetros do formulário
        String idParam = req.getParameter("id");
        String nome = req.getParameter("nome");
        String cnpj = req.getParameter("cnpj");
        String email = req.getParameter("email");

        // valida id
        if (idParam == null || !idParam.matches("[0-9]+")) {
            res.sendRedirect(req.getContextPath() + "/areaRestrita");
            return;
        }

        int id = Integer.parseInt(idParam);

        // busca empresa atual pelo id
        EmpresaModel empresaAtual = empresaDAO.buscarId(id);
        if (empresaAtual == null) {
            res.sendRedirect(req.getContextPath() + "/areaRestrita");
            return;
        }

        int resultado = 0;

        // atualiza nome se  for válido
        if (nome != null && !nome.isBlank()) {
            empresaAtual.setNome(nome);
            resultado = empresaDAO.atualizarNome(empresaAtual);
        }

        // atualiza cnpj se for válido
        if (cnpj != null && !cnpj.isBlank() && cnpj.matches(REGEX_CNPJ)) {
            empresaAtual.setCnpj(cnpj);
            resultado = empresaDAO.atualizarCnpj(empresaAtual);
        }

        // atualiza email se for válido
        if (email != null && !email.isBlank() && email.matches(REGEX_EMAIL)) {
            empresaAtual.setEmail(email);
            resultado = empresaDAO.atualizarEmail(empresaAtual);
        }

        // redireciona de volta para área restrita
        res.sendRedirect(req.getContextPath() + "/areaRestrita");
    }
}
