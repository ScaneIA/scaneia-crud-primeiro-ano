package com.scaneia.ScaneiaServlet.Servlet;

import com.scaneia.ScaneiaServlet.DAO.UsuarioDAO;
import com.scaneia.ScaneiaServlet.Model.UsuarioModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "cadastroUsuario", value = "/cadastro-usuario")
public class CadastroUsuarioServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //variaveis gerais
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean cadastroAutorizado;

        //pega os parametros da req
        String nome = req.getParameter("nome");
        String email = req.getParameter("email");
        String cpf = req.getParameter("cpf");
        String senha = req.getParameter("senha");
        String senhaConfirmacao = req.getParameter("senhaConfirmacao");
        String idAreaString = req.getParameter("idArea");

        //validação de entrada
        try {
            if (!senha.equals(senhaConfirmacao)){
                req.setAttribute("status", 400);
                req.setAttribute("mensagem", "as senhas são diferentes");
                req.getRequestDispatcher("/WEB-INF/erroCadastroUsuario.jsp").forward(req, res);
                return;
            }

            if (!cpf.matches("[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}[^0-9A-Za-z]?[0-9]{2}")){
                req.setAttribute("status", 400);
                req.setAttribute("mensagem", "formato invalido do cpf");
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
        cadastroAutorizado = usuarioDAO.cadastrar(new UsuarioModel(
                nome, email, senha, cpf, Integer.parseInt(idAreaString)
        ));

        //encaminha para as paginas coerentes
        if (cadastroAutorizado){
            req.getRequestDispatcher("/WEB-INF/cadastroAutorizado.jsp").forward(req, res);
        }else{
            req.setAttribute("status", 500);
            req.setAttribute("mensagem", "tente novamente");
            req.getRequestDispatcher("/WEB-INF/erroCadastroUsuario.jsp").forward(req, res);
        }
    }
}
