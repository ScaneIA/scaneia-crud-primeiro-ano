package com.scaneia.ScaneiaServlet.Servlet.AreaRH.OpcoesFuncionario;

import com.scaneia.ScaneiaServlet.Config.ImgConfig;
import com.scaneia.ScaneiaServlet.DAO.SetorDAO;
import com.scaneia.ScaneiaServlet.DAO.UsuarioDAO;
import com.scaneia.ScaneiaServlet.Model.SetorModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@MultipartConfig
@WebServlet(name = "AlterarImagem", value = "/areaRH/alterarImagem")
public class AlterarImagemServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //variaveis gerais
        byte[] imagem;
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        HttpSession httpSession = req.getSession();
        int resultado;
        List<SetorModel> setores;
        SetorDAO setorDAO = new SetorDAO();

        //valida se a sessão existe
        if(httpSession == null || httpSession.getAttribute("empresa") == null){
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        //recebe os parametros da requisicao
        Part partImagem = req.getPart("arquivo");
        String idUsuario = req.getParameter("idUsuario");

        //converte a imagem para vetor de bytes
        imagem = ImgConfig.transformarBytea(partImagem);

        //salva no banco de dados
        resultado = usuarioDAO.alterarImagem(imagem, Integer.parseInt(idUsuario));

        //seta os setores da empresa
        setores = setorDAO.listarSetores();
        req.setAttribute("setores", setores);

        //valida se deu certo
        if (resultado == 1){
            //responde para a mesma pagina
            res.sendRedirect(req.getContextPath() + "/areaRH/EditarFuncionario?id=" + idUsuario);
            return;
        }else{
            //responde para a pagina do RH
            res.sendRedirect(req.getContextPath() + "/areaRH");
            return;
        }
    }
}
