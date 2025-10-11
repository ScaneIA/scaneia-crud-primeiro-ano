package com.scaneia.ScaneiaServlet.Servlet;

import com.scaneia.ScaneiaServlet.DAO.EmpresaDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "LoginServlet", value = "/Login/login-empresa")
public class LoginEmpresaServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res){
        //variaveis gerais
        EmpresaDAO empresaDAO = new EmpresaDAO();

        //pega os parametros da empresa

    }
}
