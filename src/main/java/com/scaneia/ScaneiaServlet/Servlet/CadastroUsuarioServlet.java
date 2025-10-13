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
        int cadastroAutorizado;

        //pega os parametros da req
        String nome = req.getParameter("nome");
        String email = req.getParameter("email");
        String cpf = req.getParameter("cpf");
        String senha = req.getParameter("senha");
        String senhaConfirmacao = req.getParameter("senhaConfirmacao");
        String idAreaString = req.getParameter("idArea");

        //validação de entrada
        try {

            //valida se as senhas são iguais
            if (!senha.equals(senhaConfirmacao)){
                req.setAttribute("status", 400);
                req.setAttribute("mensagem", "as senhas são diferentes");
                req.getRequestDispatcher("/WEB-INF/erroCadastroUsuario.jsp").forward(req, res);
                return;
            }

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

            //valida o formato da senha
            if (!senha.matches("adicionar regex")){
                req.setAttribute("status", 400);
                req.setAttribute("mensagem", "formato inválido da senha");
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
        }catch (NullPointerException npe){
            req.setAttribute("status", 400);
            req.setAttribute("mensagem", "campos não preenchidos");
            req.getRequestDispatcher("/WEB-INF/erroCadastroUsuario.jsp").forward(req, res);
            return;
        }

        //manda as informações pro banco de dados
        cadastroAutorizado = usuarioDAO.insert(new UsuarioModel(
                nome, email, senha, cpf, Integer.parseInt(idAreaString)
        ));

        //encaminha para as paginas coerentes
        if (cadastroAutorizado == 1){
            req.getRequestDispatcher("/WEB-INF/cadastroAutorizado.jsp").forward(req, res);
        }else if(cadastroAutorizado == -2){
            req.setAttribute("status", 409);
            req.setAttribute("mensagem", "Esse registro já existe!");
            req.getRequestDispatcher("/WEB-INF/erroCadastroUsuario.jsp").forward(req, res);
        }else{
            req.setAttribute("status", 500);
            req.setAttribute("mensagem", "Tente novamente!");
            req.getRequestDispatcher("/WEB-INF/erroCadastroUsuario.jsp").forward(req, res);
        }
    }
}
