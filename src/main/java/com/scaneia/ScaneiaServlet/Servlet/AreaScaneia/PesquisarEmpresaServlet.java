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
import java.util.List;

// Pesquisa/Vê as empresas presentes.Busca pelo nome
// Não existe isso em empresa porque não faz sentido um pesquisar sendo que só vai ter um endereço, já que não faz sentido
// na nossa regra de negócio ter mais que um e se ele quiser mudar o enderço é só editar

// qualquer dúvida sobre o comentário acima acesse a documentação
@WebServlet(name = "pesquisarEmpresa", value = "/areaRestrita/pesquisarEmpresa")
public class PesquisarEmpresaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // Obtém a sessão existente sem criar nova
        HttpSession session = req.getSession(false);

        // Valida se o usuário é administrador
        if (session == null || session.getAttribute("admin") == null) {
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        // Captura parâmetros da requisição
        String acao = req.getParameter("acao");
        String nome = req.getParameter("nome");

        // Instancia DAO para empresas
        EmpresaDAO empresaDAO = new EmpresaDAO();
        List<EmpresaModel> empresas;

        // Define a lista de empresas a retornar
        if ("pesquisar".equals(acao) && nome != null && !nome.isBlank()) {
            // Cria filtro e busca empresas pelo nome
            EmpresaModel filtro = new EmpresaModel(nome);
            filtro.setNome(nome);
            empresas = empresaDAO.buscarPorNome(filtro);
        } else {
            // Busca todas as empresas
            empresas = empresaDAO.buscar();
        }

        // Define atributos para o JSP
        req.setAttribute("empresas", empresas);

        // Encaminha para a página de listagem
        req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaScaneia/areaRestritaScaneia.jsp").forward(req, res);
    }
}
