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

@WebServlet(name = "enderecos", value = "/areaRestrita/alterarEndereco")
public class AlterarEnderecoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false); // não cria nova sessão
        if (session == null) {
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        EnderecoEmpresaDAO enderecoDAO = new EnderecoEmpresaDAO();

        // parâmetros do formulário
        String idParam = req.getParameter("id");
        String rua = req.getParameter("rua");
        String numeroParam = req.getParameter("numero");
        String cidade = req.getParameter("cidade");
        String estado = req.getParameter("estado");
        String bairro = req.getParameter("bairro");
        String complemento = req.getParameter("complemento");
        String cep = req.getParameter("cep");

        // valida id
        if (idParam == null || !idParam.matches("[0-9]+")) {
            res.sendRedirect(req.getContextPath() + "/areaRestrita/enderecos");
            return;
        }

        int id = Integer.parseInt(idParam);

        // busca o endereço atual
        EnderecoEmpresaModel enderecoAtual = null;
        for (EnderecoEmpresaModel e : enderecoDAO.buscar()) {
            if (e.getId() == id) {
                enderecoAtual = e;
                break;
            }
        }

        if (enderecoAtual == null) {
            res.sendRedirect(req.getContextPath() + "/areaRestrita/enderecos");
            return;
        }

        // Atualiza os campos somente se preenchidos
        if (rua != null && !rua.isBlank()) enderecoAtual.setRua(rua);
        if (numeroParam != null && numeroParam.matches("[0-9]+")) enderecoAtual.setNumero(Integer.parseInt(numeroParam));
        if (cidade != null && !cidade.isBlank()) enderecoAtual.setCidade(cidade);
        if (estado != null && !estado.isBlank()) enderecoAtual.setEstado(estado);
        if (bairro != null) enderecoAtual.setBairro(bairro); // pode ser vazio
        if (complemento != null) enderecoAtual.setComplemento(complemento); // pode ser vazio
        if (cep != null && cep.matches("\\d{5}-?\\d{3}")) enderecoAtual.setCep(cep);

        // chama o DAO para atualizar
        enderecoDAO.atualizar(enderecoAtual);

        // redireciona de volta à lista de endereços da empresa
        res.sendRedirect(req.getContextPath() + "/areaRestrita?acao=verEnderecos&idEmpresa=" + enderecoAtual.getIdEmpresa());
    }
}
