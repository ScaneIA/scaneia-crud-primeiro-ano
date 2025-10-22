package com.scaneia.ScaneiaServlet.Servlet.AreaScaneia;

import com.scaneia.ScaneiaServlet.DAO.EnderecoEmpresaDAO;
import com.scaneia.ScaneiaServlet.Model.EnderecoEmpresaModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "excluirEndereco", value = "/areaRestrita/excluirEndereco")
public class ExcluirEnderecoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false); // não cria nova sessão
        if (session == null) {
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        EnderecoEmpresaDAO enderecoDAO = new EnderecoEmpresaDAO();

        String idParam = req.getParameter("id");
        String idEmpresaParam = req.getParameter("idEmpresa"); // necessário para redirecionar

        if (idParam == null || !idParam.matches("[0-9]+") ||
                idEmpresaParam == null || !idEmpresaParam.matches("[0-9]+")) {
            res.sendRedirect(req.getContextPath() + "/areaRestrita/enderecos?idEmpresa=" + idEmpresaParam);
            return;
        }

        int id = Integer.parseInt(idParam);
        int idEmpresa = Integer.parseInt(idEmpresaParam);

        // Cria o objeto preenchendo apenas os campos necessários
        EnderecoEmpresaModel endereco = new EnderecoEmpresaModel();
        endereco.setId(id);
        endereco.setIdEmpresa(idEmpresa);

        int resultado = enderecoDAO.deletar(endereco);

        // Redireciona de volta para a lista de endereços da empresa
        res.sendRedirect(req.getContextPath() + "/areaRestrita/enderecos?idEmpresa=" + idEmpresa);
    }
}
