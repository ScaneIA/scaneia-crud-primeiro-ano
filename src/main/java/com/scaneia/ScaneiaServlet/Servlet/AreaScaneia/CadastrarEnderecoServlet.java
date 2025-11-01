package com.scaneia.ScaneiaServlet.Servlet.AreaScaneia;

import com.scaneia.ScaneiaServlet.DAO.EnderecoEmpresaDAO;
import com.scaneia.ScaneiaServlet.Model.EnderecoEmpresaModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

// Cadastro de uma endereço totalmente novo
@WebServlet(name = "cadastroEndereco", value = "/areaRestrita/cadastroEndereco")
public class CadastrarEnderecoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // Pega sessão existente, não cria nova
        HttpSession session = req.getSession(false);

        // Valida sessão existente
        if (session == null) {
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        // Pega os parâmetros do formulário
        int idEmpresa = Integer.parseInt(req.getParameter("idEmpresa"));
        String rua = req.getParameter("rua");
        String numero = req.getParameter("numero");
        String cidade = req.getParameter("cidade");
        String estado = req.getParameter("estado");
        String bairro = req.getParameter("bairro");
        String complemento = req.getParameter("complemento");
        String cep = req.getParameter("cep");

        // Instancia DAO para manipulação de endereços
        EnderecoEmpresaDAO dao = new EnderecoEmpresaDAO();

        // Verifica se a empresa já possui endereço cadastrado
        List<EnderecoEmpresaModel> enderecos = dao.buscarPorIdEmpresa(idEmpresa);
        if (enderecos != null && !enderecos.isEmpty()) {
            // Redireciona para lista de endereços se já tiver
            res.sendRedirect(req.getContextPath() + "/areaRestrita?acao=verEnderecos&idEmpresa=" + idEmpresa);
            return;
        }

        // Cria objeto do endereço e popula campos
        EnderecoEmpresaModel endereco = new EnderecoEmpresaModel();
        endereco.setRua(rua);
        endereco.setNumero(Integer.parseInt(numero));
        endereco.setCidade(cidade);
        endereco.setEstado(estado);
        endereco.setBairro(bairro);
        endereco.setComplemento(complemento);
        endereco.setCep(cep);
        endereco.setIdEmpresa(idEmpresa);

        // Insere novo endereço no banco
        dao.inserir(endereco);

        // Redireciona para visualizar endereços da empresa
        res.sendRedirect(req.getContextPath() + "/areaRestrita?acao=verEnderecos&idEmpresa=" + idEmpresa);
    }
}
