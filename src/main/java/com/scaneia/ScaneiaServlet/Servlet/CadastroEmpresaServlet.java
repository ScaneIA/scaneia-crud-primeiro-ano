package com.scaneia.ScaneiaServlet.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CadastroEmpresa", value = "/cadastro-empresa")
public class CadastroEmpresaServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
        //variaveis gerais


        //pega os parametros da req
        String nome = req.getParameter("nome");
        String cnpj = req.getParameter("cnpj");
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");
        String senhaConfimacao = req.getParameter("senhaConfirmacao");

        //validação de entrada
        try {
            //valida se as senhas são iguais
            if (!senha.equals(senhaConfimacao)){
                req.setAttribute("status",  400);
                req.setAttribute("mensagem", "As senhas não coincidem");
                req.getRequestDispatcher("/WEB-INF/erroCadastroEmpresa.jsp").forward(req, res);
                return;
            }

            //valida o formato da senha
            if (!senha.matches("^(?=[A-Z])*(?=[a-z])*(?=[0-9])*(?=[\\W_)*\\S{6,}$])")){
                req.setAttribute("status", 400);
                req.setAttribute("mensagem", "A senha não cumpre com os resquisitos");
                req.getRequestDispatcher("/WEB-INF/erroCadastroEmpresa.jsp").forward(req, res);
                return;
            }

            //valida o formato do cnpj
            if (!cnpj.matches("^\\d{2}[. -/]?\\d{3}[. -/]?\\d{3}[. -/]?\\d{4}[. -/]?\\d{2}")){
                req.setAttribute("status", 400);
                req.setAttribute("mensagem", "cnpj inválido");
                req.getRequestDispatcher("/WEB-INF/erroCadastroEmpresa.jsp").forward(req, res);
                return;
            }

            //valida o formato do nome
            if(!nome.matches("^[A-Za-zÀ-ÖØ-öø-ÿ]+(?:\\s+[A-Za-zÀ-ÖØ-öø-ÿ]+)+$")){
                req.setAttribute("status", 400);
                req.setAttribute("mensagem", "formato do nome inválido");
                req.getRequestDispatcher("/WEB-INF/erroCadastroEmpresa.jsp").forward(req, res);
            }

            //valida o formato do email
            if (!email.matches("^[A-Za-z0-9]+([._]?[A-Za-z0-9]+)*@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){
                req.setAttribute("status", 400);
                req.setAttribute("mensagem", "formato do email inválido");
            }

        }catch (NullPointerException npe){
            req.getRequestDispatcher("/WEB-INF/erroCadastroEmpresa.jsp").forward(req, res);
            return;
        }

        //manda as informações pro banco


    }
}
