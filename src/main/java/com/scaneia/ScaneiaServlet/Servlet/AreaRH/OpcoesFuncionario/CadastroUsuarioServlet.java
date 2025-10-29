package com.scaneia.ScaneiaServlet.Servlet.AreaRH.OpcoesFuncionario;

import com.scaneia.ScaneiaServlet.Config.ImgConfig;
import com.scaneia.ScaneiaServlet.DAO.UsuarioDAO;
import com.scaneia.ScaneiaServlet.DAO.UsuarioViewDAO;
import com.scaneia.ScaneiaServlet.Model.EmpresaModel;
import com.scaneia.ScaneiaServlet.Model.UsuarioModel;
import com.scaneia.ScaneiaServlet.Model.UsuarioViewModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@WebServlet(name = "cadastroUsuario", value = "/areaRH/cadastroUsuario")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
public class CadastroUsuarioServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //variaveis gerais
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        HttpSession httpSession = req.getSession();
        EmpresaModel empresa;
        int cadastroAutorizado;
        List<UsuarioViewModel> usuarios;
        UsuarioViewDAO usuarioViewDAO = new UsuarioViewDAO();
        UsuarioModel usuarioAdicionado;

        //valida se a sessão existe
        if(httpSession == null || httpSession.getAttribute("empresa") == null){
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        //pega os parametros da req
        String nome = req.getParameter("addNome");
        String email = req.getParameter("addEmail");
        String cpf = req.getParameter("addCpf");
        String idCargo = req.getParameter("idCargo");
        String setorId = req.getParameter("setorId");
        Part partArquivo =req.getPart("arquivo");

        //transforma a imagem em bytes
        byte[] imagembytea = ImgConfig.transformarBytea(partArquivo);

        //carrega a empresa
        empresa = (EmpresaModel) httpSession.getAttribute("empresa");

        //define os usuarios da empresa
        usuarios = usuarioViewDAO.buscarPorEmpresa(empresa.getId());
        req.setAttribute("usuarios", usuarios);

        //validação de entrada
        try {
            //valida o formato do cpf
            if (!cpf.matches("^\\s*\\d{3}\\s*[.-]?\\s*\\d{3}\\s*[.-]?\\s*\\d{3}\\s*[.-]?\\s*\\d{2}\\s*$")){
                req.setAttribute("status", 400);
                req.setAttribute("mensagem", "formato inválido do cpf");
                req.getRequestDispatcher("/WEB-INF/erroCadastroUsuario.jsp").forward(req, res);
                return;
            }else{
                //remove os caracteres que não são numeros
                cpf = cpf.replaceAll("[^0-9]", "");
            }

            //valida o formato do email
            if (!email.matches("^[A-Za-z0-9]+([._]?[A-Za-z0-9]+)*@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){
                req.setAttribute("status", 400);
                req.setAttribute("mensagem", "formato inválido do email");
                req.getRequestDispatcher("/WEB-INF/erroCadastroUsuario.jsp").forward(req, res);
                return;
            }

            //valida o formato do nome
            if (!nome.matches("^[A-Za-zÀ-ÖØ-öø-ÿ]+(?:\\s+[A-Za-zÀ-ÖØ-öø-ÿ]+)+$")){
                req.setAttribute("status", 400);
                req.setAttribute("mensagem", "formato inválido do nome");
                req.getRequestDispatcher("/WEB-INF/erroCadastroUsuario.jsp").forward(req, res);
                return;
            }

            //valida o idCargo
            if (!idCargo.matches("[0-9]+")){
                req.setAttribute("status", 400);
                req.setAttribute("mensagem", "id do cargo inválido");
                req.getRequestDispatcher("/WEB-INF/erroCadastroUsuario.jsp").forward(req, res);
                return;
            }
        }catch (NullPointerException npe){
            req.setAttribute("status", 400);
            req.setAttribute("mensagem", "campos não preenchidos");
            req.getRequestDispatcher("/WEB-INF/erroCadastroUsuario.jsp").forward(req, res);
            return;
        }

        //manda as informações pro banco de dados
        usuarioAdicionado = new UsuarioModel(
                nome, email, cpf, Integer.parseInt(idCargo), empresa.getId(), imagembytea
        );

        cadastroAutorizado = usuarioDAO.insert(usuarioAdicionado);

        //seta o setor do usuario
        usuarioDAO.adicionarSetorUsuario(usuarioAdicionado, Integer.parseInt(setorId));

        //encaminha para as paginas coerentes
        if (cadastroAutorizado == 1){
            res.sendRedirect(req.getContextPath() + "/areaRH");
            return;
        }else if(cadastroAutorizado == -2){
            req.setAttribute("status", 409);
            req.setAttribute("mensagem", "Esse registro já existe!");
            req.getRequestDispatcher("/WEB-INF/erroCadastroUsuario.jsp").forward(req, res);
            return;
        }else{
            req.setAttribute("status", 500);
            req.setAttribute("mensagem", "Tente novamente!");
            req.getRequestDispatcher("/WEB-INF/erroCadastroUsuario.jsp").forward(req, res);
            return;
        }
    }
}
