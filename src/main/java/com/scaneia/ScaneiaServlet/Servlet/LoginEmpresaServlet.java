package com.scaneia.ScaneiaServlet.Servlet;

import com.scaneia.ScaneiaServlet.Config.HashSenha;
import com.scaneia.ScaneiaServlet.DAO.EmpresaDAO;
import com.scaneia.ScaneiaServlet.Model.EmpresaModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet(name = "LoginServlet", value = "/Login/login-empresa")
public class LoginEmpresaServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        //variaveis gerais
        EmpresaDAO empresaDAO = new EmpresaDAO();
        EmpresaModel empresa;
        HttpSession httpSession = req.getSession();

        //pega os parametros da empresa
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");
        String cnpj = req.getParameter("cnpj");

        //validação de entrada
        try {
            //valida o formato do email
            if (!email.matches("^[A-Za-z0-9]+([._]?[A-Za-z0-9]+)*@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){
                req.setAttribute("status", 400);
                req.setAttribute("mensagem", "Formato inválido do email!");
                req.getRequestDispatcher("/WEB-INF/VIEW/erroLoginEmpresa.jsp").forward(req, res);
                return;
            }

            //valida o formato da senha
            if (!senha.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_])\\S{8,}$")){
                req.setAttribute("status", 400);
                req.setAttribute("mensagem", "Formato inválido da senha!");
                req.getRequestDispatcher("/WEB-INF/VIEW/erroLoginEmpresa.jsp").forward(req, res);
                return;
            }else{
                //pega o hash da senha
                try {
                    senha = HashSenha.hashSenha(senha);

                }catch (NoSuchAlgorithmException nsae){
                    req.setAttribute("status",  500);
                    req.setAttribute("mensagem", "Ops... Tente novamente!");
                    req.getRequestDispatcher("/WEB-INF/VIEW/erroLoginEmpresa.jsp").forward(req, res);
                    return;
                }
            }

            //valida o formato do cnpj
            if (!cnpj.matches("^\\d{2}[. -/]?\\d{3}[. -/]?\\d{3}[. -/]?\\d{4}[. -/]?\\d{2}")){
                req.setAttribute("status", 400);
                req.setAttribute("mensagem", "Formato inválido do cnpj!");
                req.getRequestDispatcher("/WEB-INF/VIEW/erroLoginEmpresa.jsp").forward(req, res);
                return;
            }else{
                //extaí os numeros
                cnpj = cnpj.replaceAll("[^0-9]", "");
            }

        }catch (NullPointerException exception){
            req.setAttribute("status", 400);
            req.setAttribute("mensagem", "Insira todos os campos!");
            req.getRequestDispatcher("/WEB-INF/VIEW/erroLoginEmpresa.jsp").forward(req, res);
            return;
        }

        //faz o login
        empresa = empresaDAO.login(email, senha, cnpj);

        System.out.println(empresa);
        System.out.println(senha);

        if (empresa == null){
            req.setAttribute("status", 400);
            req.setAttribute("mensagem", "Credenciais inválidos!");
            req.getRequestDispatcher("/WEB-INF/VIEW/erroLoginEmpresa.jsp").forward(req, res);
            return;
        }

        //salva a sessão
        httpSession.setAttribute("empresa", empresa);
        httpSession.setMaxInactiveInterval(1800); //30m

        //manda para a area interna
        req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/areaRestritaEmpresa.jsp").forward(req, res);
    }
}
