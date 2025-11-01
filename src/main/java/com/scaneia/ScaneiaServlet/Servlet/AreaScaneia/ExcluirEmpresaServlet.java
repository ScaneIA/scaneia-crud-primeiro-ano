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
// Servlet responsável por excluir empresas.
// Observasse que não existe a opção de excluir endereços separadamente, pois não faz sentido remover um endereço
// sem a empresa associada. Como as tabelas estão relacionadas e "endereço" é uma tabela fraca dependente de "empresa",
// ao excluir uma empresa, o endereço vinculado é automaticamente removido.

// qualquer dúvida sobre o comentário acima acesse a documentação
@WebServlet(name = "excluirEmpresa", value = "/areaRestrita/excluirEmpresa")
public class ExcluirEmpresaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // Pega a sessão existente, não cria nova
        HttpSession session = req.getSession(false);

        // Verifica se existe
        if (session == null) {
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        // Instancia DAO para manipulação de empresas
        EmpresaDAO empresaDAO = new EmpresaDAO();

        // Pega parâmetro ID do formulário
        String idParam = req.getParameter("id");

        // Valida o ID recebido
        if (idParam == null || !idParam.matches("[0-9]+")) {
            res.sendRedirect(req.getContextPath() + "/areaRestrita");
            return;
        }

        int id = Integer.parseInt(idParam);

        // Cria objeto EmpresaModel apenas com ID (como é só deletar, não tem problema)
        EmpresaModel empresa = new EmpresaModel(id, null, null, null);

        // Chama DAO para deletar empresa
        int resultado = empresaDAO.deletar(empresa);

        // Redireciona para a listagem de empresas
        res.sendRedirect(req.getContextPath() + "/areaRestrita");
    }
}
