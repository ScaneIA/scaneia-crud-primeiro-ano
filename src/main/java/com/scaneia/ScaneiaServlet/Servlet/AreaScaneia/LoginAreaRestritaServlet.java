package com.scaneia.ScaneiaServlet.Servlet.AreaScaneia;

import com.scaneia.ScaneiaServlet.Config.HashSenha;
import com.scaneia.ScaneiaServlet.DAO.AdministradorDAO;
import com.scaneia.ScaneiaServlet.Model.AdministradorModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

// Página para colocar email e senha e conseguir acessar área restrita
@WebServlet("/loginAreaRestrita")
public class LoginAreaRestritaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        //variaveis gerais
        AdministradorModel adimin;
        AdministradorDAO administradorDAO = new AdministradorDAO();
        HttpSession httpSession = req.getSession();

        //variaveis da requisição
        String emailDigitado = req.getParameter("email");
        String senhaDigitada = req.getParameter("senha");


        //validação de entrada
        try {
            //valida a senha
            if (!senhaDigitada.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_])\\S{8,}$")){
                req.setAttribute("status", 400);
                req.setAttribute("mensagem", "Formato inválido da senha!");
                req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaScaneia/loginAreaRestrita.jsp").forward(req, res);
                return;
            }else{
                //pega o hash da senha
                try {
                    senhaDigitada = HashSenha.hashSenha(senhaDigitada);

                }catch (NoSuchAlgorithmException nsae){
                    res.sendRedirect(req.getContextPath() + "/index.html");
                }
            }

            //valida o formato do email
            if (!emailDigitado.matches("")){
                if (!emailDigitado.matches("^[A-Za-z0-9]+([._]?[A-Za-z0-9]+)*@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){
                    req.setAttribute("status", 400);
                    req.setAttribute("mensagem", "Formato inválido do email!");
                    req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaScaneia/loginAreaRestrita.jsp").forward(req, res);
                    return;
                }
            }

        } catch (NullPointerException npe) {
            req.setAttribute("status", 400);
            req.setAttribute("mensagem", "Informe todos os campos");
            req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaScaneia/loginAreaRestrita.jsp").forward(req, res);
            return;
        }

        //faz o login
        adimin = administradorDAO.login(emailDigitado, senhaDigitada);

        if (adimin == null){
            req.setAttribute("status", 400);
            req.setAttribute("mensagem", "Credenciais inválidos");
            req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaScaneia/loginAreaRestrita.jsp").forward(req, res);
        }

        //salva a sessão
        httpSession.setAttribute("admin", adimin);

        //manda para a area restrita
        res.sendRedirect(req.getContextPath() + "/areaRestrita");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
        req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaScaneia/loginAreaRestrita.jsp").forward(req, res);
        return;
    }

}
