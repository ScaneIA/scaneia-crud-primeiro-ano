package com.scaneia.ScaneiaServlet.Servlet;

import com.scaneia.ScaneiaServlet.DAO.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "loginUsuario", value = "/login-usuario")
public class LoginUsuarioServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        //variaveis gerais
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean loginAutorizado;

        //pega os parametrod da req
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        //validação de entrada
        try {
            if (!email.matches("[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}[^0-9A-Za-z]?[0-9]{2}")){
                req.setAttribute("status", 400);
                req.setAttribute("mensagem", "formato inválido do email");
                req.getRequestDispatcher("/WEB-INF/erroLoginUsuario.jsp").forward(req, res);
                return;
            }

            if (senha == null){
                req.setAttribute("status", 400);
                req.setAttribute("mensagem", "formato inválido da senha");
                req.getRequestDispatcher("/WEB-INF/erroLoginUsuario.jsp").forward(req, res);
                return;
            }

        }catch (NullPointerException npe){
            req.setAttribute("status", 400);
            req.setAttribute("status", "campos não preenchidos");
            req.getRequestDispatcher("/WEB-INF/erroLoginUsuario.jsp").forward(req, res);
        }

        //faz requisição com o banco de dados
        loginAutorizado = usuarioDAO.login(email, senha);

        if (loginAutorizado){
            res.sendRedirect("url segundo ano");
        }else{
            req.setAttribute("status", 500);
            req.setAttribute("mensagem", "tente novamente");
            req.getRequestDispatcher("WEB-INF/erroLoginUsuario.jsp").forward(req, res);
        }
    }
}
