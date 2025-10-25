package com.scaneia.ScaneiaServlet.Servlet.AreaScaneia;

import com.scaneia.ScaneiaServlet.DAO.EnderecoEmpresaDAO;
import com.scaneia.ScaneiaServlet.Model.EnderecoEmpresaModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

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

        int idEmpresa = Integer.parseInt(req.getParameter("idEmpresa"));
        String rua = req.getParameter("rua");
        String numero = req.getParameter("numero");
        String cidade = req.getParameter("cidade");
        String estado = req.getParameter("estado");
        String bairro = req.getParameter("bairro");
        String complemento = req.getParameter("complemento");
        String cep = req.getParameter("cep");

        EnderecoEmpresaDAO dao = new EnderecoEmpresaDAO();

        // Verifica se a empresa já tem um endereço cadastrado
        List<EnderecoEmpresaModel> enderecos = dao.buscarPorIdEmpresa(idEmpresa);
        if (enderecos != null && !enderecos.isEmpty()) {
            // já tem endereço
            res.sendRedirect(req.getContextPath() + "/areaRestrita?acao=verEnderecos&idEmpresa=" + idEmpresa);
            return;
        }

        // Cria e insere novo endereço
        EnderecoEmpresaModel endereco = new EnderecoEmpresaModel();
        endereco.setRua(rua);
        endereco.setNumero(Integer.parseInt(numero));
        endereco.setCidade(cidade);
        endereco.setEstado(estado);
        endereco.setBairro(bairro);
        endereco.setComplemento(complemento);
        endereco.setCep(cep);
        endereco.setIdEmpresa(idEmpresa);

        dao.inserir(endereco);

        // Redireciona para o JSP certo (corrigido o link)
        res.sendRedirect(req.getContextPath() + "/areaRestrita?acao=verEnderecos&idEmpresa=" + idEmpresa);
    }
}
