package com.scaneia.ScaneiaServlet.Servlet.AreaScaneia;

import com.scaneia.ScaneiaServlet.DAO.EnderecoEmpresaDAO;
import com.scaneia.ScaneiaServlet.Model.EnderecoEmpresaModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

// Cadastro de um endereço totalmente novo
@WebServlet(name = "cadastroEndereco", value = "/areaRestrita/cadastroEndereco")
public class CadastrarEnderecoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session == null) {
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        // Pega parâmetros do formulário
        String idEmpresaParam = req.getParameter("idEmpresa");
        String rua = req.getParameter("rua");
        String numeroParam = req.getParameter("numero");
        String cidade = req.getParameter("cidade");
        String estado = req.getParameter("estado");
        String bairro = req.getParameter("bairro");
        String complemento = req.getParameter("complemento");
        String cep = req.getParameter("cep");

        // Validação básica de campos obrigatórios
        if (idEmpresaParam == null || rua == null || rua.isBlank() ||
                numeroParam == null || numeroParam.isBlank() ||
                cidade == null || cidade.isBlank() ||
                estado == null || estado.isBlank() ||
                cep == null || cep.isBlank()) {

            req.setAttribute("status", 400);
            req.setAttribute("mensagem", "Campos obrigatórios não preenchidos.");
            req.getRequestDispatcher("/WEB-INF/erroCadastroEndereco.jsp").forward(req, res);
            return;
        }

        int idEmpresa;
        int numero;
        try {
            idEmpresa = Integer.parseInt(idEmpresaParam);
            numero = Integer.parseInt(numeroParam);
        } catch (NumberFormatException e) {
            req.setAttribute("status", 400);
            req.setAttribute("mensagem", "ID da empresa ou número do endereço inválido.");
            req.getRequestDispatcher("/WEB-INF/erroCadastroEndereco.jsp").forward(req, res);
            return;
        }

        EnderecoEmpresaDAO dao = new EnderecoEmpresaDAO();

        // Verifica se já existe endereço para a empresa
        List<EnderecoEmpresaModel> enderecos = dao.buscarPorIdEmpresa(idEmpresa);
        if (enderecos != null && !enderecos.isEmpty()) {
            req.setAttribute("status", 409);
            req.setAttribute("mensagem", "Essa empresa já possui um endereço cadastrado.");
            req.getRequestDispatcher("/WEB-INF/erroCadastroEndereco.jsp").forward(req, res);
            return;
        }

        // Cria objeto de endereço
        EnderecoEmpresaModel endereco = new EnderecoEmpresaModel();
        endereco.setIdEmpresa(idEmpresa);
        endereco.setRua(rua);
        endereco.setNumero(numero);
        endereco.setCidade(cidade);
        endereco.setEstado(estado);
        endereco.setBairro(bairro != null ? bairro : "");
        endereco.setComplemento(complemento != null ? complemento : "");
        endereco.setCep(cep);

        // Insere no banco
        dao.inserir(endereco);

        // Redireciona para lista de endereços
        res.sendRedirect(req.getContextPath() + "/areaRestrita?acao=verEnderecos&idEmpresa=" + idEmpresa);
    }
}
