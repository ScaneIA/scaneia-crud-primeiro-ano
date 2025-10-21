package com.scaneia.ScaneiaServlet.Servlet.AreaScaneia;

import com.scaneia.ScaneiaServlet.Config.HashSenha;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet("/loginAreaRestrita")
public class LoginAreaRestritaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //variaveis da requisição
        String senhaDigitada = req.getParameter("senha");

        try {
            String hashDigitado = HashSenha.hashSenha(senhaDigitada);

            if (hashDigitado.equals(HashSenha.HASH_FIXO)) {
                HttpSession session = req.getSession();
                session.setAttribute("acessoAreaRestrita", true);
                resp.sendRedirect(req.getContextPath() + "/areaRestrita");
            } else {
                req.setAttribute("erro", "Senha incorreta!");
                req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaScaneia/loginAreaRestrita.jsp").forward(req, resp);
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            resp.sendError(500, "Erro interno no servidor");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaScaneia/loginAreaRestrita.jsp").forward(req, resp);
    }

}
