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

    public void doPost(HttpServletRequest req, HttpServletResponse res){
        //variaveis gerais


        //pega os parametros da req
        String nome = req.getParameter("nome");
        String cnpj = req.getParameter("cnpj");
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");
        String senhaConfimacao = req.getParameter("senhaConfirmacao");

        //valida se as senhas são iguais
        if (!senha.equals(senhaConfimacao)){
            req.setAttribute("status",  400);
            req.setAttribute("mensagem", "As senhas não coincidem");
        }

        //valida o formato da senha
        if (!senha.matches("^(?=[A-Z])*(?=[a-z])*(?=[0-9])*(?=[\\W_)*\\S{6,}$])")){
            req.setAttribute("status", 400);
            req.setAttribute("mensagem", "A senha não cumpre com os resquisitos");
        }

        //valida o formato do cnpj
        if (!cnpj.matches("^\\d{2}[. -/]?\\d{3}[. -/]?\\d{3}[. -/]?\\d{4}[. -/]?\\d{2}")){
            req.setAttribute("status", 400);
            req.setAttribute("mensagem", "cnpj inválido");
        }

        //manda as informações pro banco


    }
}
