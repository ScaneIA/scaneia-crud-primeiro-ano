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

// Servlet responsável por alterar/atualizar os dados do endereço da empresa.
// Todas as atualizações como rua, bairro, cep e etc são tratadas aqui,
// permitindo modificar um ou mais campos conforme a solicitação do usuário.
@WebServlet(name = "enderecos", value = "/areaRestrita/alterarEndereco")
public class AlterarEnderecoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // pega a sessão sem criar nova
        HttpSession session = req.getSession(false);
        if (session == null) {
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        EnderecoEmpresaDAO enderecoDAO = new EnderecoEmpresaDAO();

        // pega os parâmetros enviados pelo formulário
        String idParam = req.getParameter("id");
        String rua = req.getParameter("rua");
        String numeroParam = req.getParameter("numero");
        String cidade = req.getParameter("cidade");
        String estado = req.getParameter("estado");
        String bairro = req.getParameter("bairro");
        String complemento = req.getParameter("complemento");
        String cep = req.getParameter("cep");

        // valida o id do endereço
        if (idParam == null || !idParam.matches("[0-9]+")) {
            res.sendRedirect(req.getContextPath() + "/areaRestrita/enderecos");
            return;
        }

        int id = Integer.parseInt(idParam);

        // busca o endereço atual pelo id
        EnderecoEmpresaModel enderecoAtual = null;
        for (EnderecoEmpresaModel e : enderecoDAO.buscar()) {
            if (e.getId() == id) {
                enderecoAtual = e;
                break;
            }
        }

        // redireciona caso o endereço não exista
        if (enderecoAtual == null) {
            res.sendRedirect(req.getContextPath() + "/areaRestrita/enderecos");
            return;
        }

        // atualiza somente os campos preenchidos
        if (rua != null && !rua.isBlank()) enderecoAtual.setRua(rua);
        if (numeroParam != null && numeroParam.matches("[0-9]+")) enderecoAtual.setNumero(Integer.parseInt(numeroParam));
        if (cidade != null && !cidade.isBlank()) enderecoAtual.setCidade(cidade);
        if (estado != null && !estado.isBlank()) enderecoAtual.setEstado(estado);
        // permitido o usuário não escrever nada/ aceita valor vazio
        if (bairro != null) enderecoAtual.setBairro(bairro);
        // permitido o usuário não escrever nada/ aceita valor vazio
        if (complemento != null) enderecoAtual.setComplemento(complemento);
        if (cep != null && cep.matches("\\d{5}-?\\d{3}")) enderecoAtual.setCep(cep);

        // chama o DAO para salvar as alterações
        enderecoDAO.atualizar(enderecoAtual);

        // redireciona para a lista de endereços da empresa
        res.sendRedirect(req.getContextPath() + "/areaRestrita?acao=verEnderecos&idEmpresa=" + enderecoAtual.getIdEmpresa());
    }
}
