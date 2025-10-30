package com.scaneia.ScaneiaServlet.Servlet.AreaRH.OpcoesFuncionario;

import com.scaneia.ScaneiaServlet.Config.ImgConfig;
import com.scaneia.ScaneiaServlet.DAO.SetorDAO;
import com.scaneia.ScaneiaServlet.DAO.UsuarioDAO;
import com.scaneia.ScaneiaServlet.DAO.UsuarioViewDAO;
import com.scaneia.ScaneiaServlet.Model.EmpresaModel;
import com.scaneia.ScaneiaServlet.Model.SetorModel;
import com.scaneia.ScaneiaServlet.Model.UsuarioViewModel;
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
        EmpresaModel empresa;
        UsuarioViewDAO usuarioViewDAO = new UsuarioViewDAO();
        UsuarioViewModel usuario;

        //valida se a sessão existe
        if(httpSession == null || httpSession.getAttribute("empresa") == null){
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }else{
            empresa = (EmpresaModel) httpSession.getAttribute("empresa");
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

        //seta os usuarios da empresa
        usuario = usuarioViewDAO.buscarPorId(empresa.getId(), Integer.parseInt(idUsuario));
        req.setAttribute("usuarios", usuarioViewDAO.buscarPorEmpresa(empresa.getId()));
        req.setAttribute("usuario", usuario);

        //seta a foto do usuario
        req.setAttribute("imagem", ImgConfig.transformarBase64(usuario.getUrlFoto()));

        //saidas do jsp
        if(resultado == -1 || resultado == -3){
            req.setAttribute("mensagem", "Ops... Tente novamente!");
            req.setAttribute("status", 500);
            req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/statusUsuario.jsp").forward(req, res);
        }else if(resultado == 0){
            req.setAttribute("mensagem", "Ops... Esse usuario não existe!");
            req.setAttribute("status", 400);
            req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/statusUsuario.jsp").forward(req, res);
        }else if(resultado == -2){
            req.setAttribute("mensagem", "Ops... Tente novamente!");
            req.setAttribute("status", 500);
            req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/statusUsuario.jsp").forward(req, res);
        }else if(resultado == 1){
            req.setAttribute("mensagem", "Atualizado com sucesso!");
            req.setAttribute("status", 200);
            req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/statusUsuario.jsp").forward(req, res);
        }else{
            req.setAttribute("mensagem", "Ops... Erro nosso, tente novamente!");
            req.setAttribute("status", 500);
            req.getRequestDispatcher("/WEB-INF/VIEW/areaRestritaEmpresa/statusUsuario.jsp").forward(req, res);
        }
    }
}
