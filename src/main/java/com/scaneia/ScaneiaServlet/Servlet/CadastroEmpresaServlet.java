package com.scaneia.ScaneiaServlet.Servlet;

import com.scaneia.ScaneiaServlet.Config.HashSenha;
import com.scaneia.ScaneiaServlet.DAO.EmpresaDAO;
import com.scaneia.ScaneiaServlet.DAO.EnderecoEmpresaDAO;
import com.scaneia.ScaneiaServlet.Model.EmpresaModel;
import com.scaneia.ScaneiaServlet.Model.EnderecoEmpresaModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@WebServlet(name = "CadastroEmpresa", value = "/Cadastro/cadastro-empresa")
public class CadastroEmpresaServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
        //variaveis gerais
        EmpresaDAO empresaDAO = new EmpresaDAO();
        EnderecoEmpresaDAO enderecoEmpresaDAO = new EnderecoEmpresaDAO();
        EmpresaModel empresa;
        EnderecoEmpresaModel enderecoEmpresa;

        //pega os parametros da empresa
        String senha = req.getParameter("senha");
        String senhaConfimacao = req.getParameter("senhaConfirmacao");
        String nome = req.getParameter("nome");
        String cnpj = req.getParameter("cnpj");
        String email = req.getParameter("email");

        //pega os parametros do endereço
        String rua = req.getParameter("rua");
        String estado = req.getParameter("estado");
        String cidade = req.getParameter("cidade");
        String cep = req.getParameter("cep");
        String bairro = req.getParameter("bairro");
        String numero = req.getParameter("numero");
        String complemento = req.getParameter("complemento");

        //validação de entrada
        try {
            //valida se as senhas são iguais
            if (!senha.equals(senhaConfimacao)){
                req.setAttribute("status",  400);
                req.setAttribute("mensagem", "As senhas são diferentes!");
                req.getRequestDispatcher("/WEB-INF/VIEW/erroCadastroEmpresa.jsp").forward(req, res);
                return;
            }

            //valida se a senha é segura
            if (!senha.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_])\\S{8,}$")){
                req.setAttribute("status",  400);
                req.setAttribute("mensagem", "Senha deve ter: 1 minúscula, 1 maiúscula, 1 número, 1 símbolo, mínimo 8 caracteres e sem espaços.");
                req.getRequestDispatcher("/WEB-INF/VIEW/erroCadastroEmpresa.jsp").forward(req, res);
                return;
            }else{
                //coloca o hash da senha
                try {
                    senha = HashSenha.hashSenha(senha);

                }catch (NoSuchAlgorithmException nsae){
                    req.setAttribute("status",  500);
                    req.setAttribute("mensagem", "Ops... Tente novamente!");
                    req.getRequestDispatcher("/WEB-INF/VIEW/erroCadastroEmpresa.jsp").forward(req, res);
                }
            }

            //valida se o nome é válido
            if (!nome.matches("^[A-Za-zÀ-ÖØ-öø-ÿ]+(?:\\s+[A-Za-zÀ-ÖØ-öø-ÿ]+)*$")){
                req.setAttribute("status",  400);
                req.setAttribute("mensagem", "Nome da empresa inválido!");
                req.getRequestDispatcher("/WEB-INF/VIEW/erroCadastroEmpresa.jsp").forward(req, res);
                return;
            }

            //valida se o cnpj é valido
            if (!cnpj.matches("^\\d{2}[. -/]?\\d{3}[. -/]?\\d{3}[. -/]?\\d{4}[. -/]?\\d{2}")){
                req.setAttribute("status",  400);
                req.setAttribute("mensagem", "formato inválido do cnpj!");
                req.getRequestDispatcher("/WEB-INF/VIEW/erroCadastroEmpresa.jsp").forward(req, res);
                return;
            }else{
                //extrai apenas os numeros
                cnpj = cnpj.replaceAll("[^0-9]", "");
            }

            //valida o formato do email
            if (!email.matches("^[A-Za-z0-9]+([._]?[A-Za-z0-9]+)*@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){
                req.setAttribute("status",  400);
                req.setAttribute("mensagem", "Formato inválido do email!");
                req.getRequestDispatcher("/WEB-INF/VIEW/erroCadastroEmpresa.jsp").forward(req, res);
                return;
            }

            //valida o formato do estado
            if (!estado.matches("^(AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO)$")){
                req.setAttribute("status",  400);
                req.setAttribute("mensagem", "Estado inválido!");
                req.getRequestDispatcher("/WEB-INF/VIEW/erroCadastroEmpresa.jsp").forward(req, res);
                return;
            }

            //valida o formato da cidade
            if (!cidade.matches("^[A-Za-zÀ-ÖØ-öø-ÿ]+(?:\\s+[A-Za-zÀ-ÖØ-öø-ÿ]+)*$")){
                req.setAttribute("status",  400);
                req.setAttribute("mensagem", "Cidade inválida!");
                req.getRequestDispatcher("/WEB-INF/VIEW/erroCadastroEmpresa.jsp").forward(req, res);
                return;
            }

            //valida o formato do cep
            if (!cep.matches("^\\d{5}[. -/]?\\d{3}")){
                req.setAttribute("status",  400);
                req.setAttribute("mensagem", "formato inválido do cep!");
                req.getRequestDispatcher("/WEB-INF/VIEW/erroCadastroEmpresa.jsp").forward(req, res);
                return;
            }else{
                //extrai os numeros
                cep = cep.replaceAll("[^0-9]", "");
            }

            //valida o formato do bairro
            if (!bairro.matches("^[A-Za-zÀ-ÖØ-öø-ÿ]+(?:\\s+[A-Za-zÀ-ÖØ-öø-ÿ]+)*$")){
                req.setAttribute("status",  400);
                req.setAttribute("mensagem", "Formato inválido do bairro!");
                req.getRequestDispatcher("/WEB-INF/VIEW/erroCadastroEmpresa.jsp").forward(req, res);
                return;
            }

            //valida o numero
            if (!numero.matches("^[0-9]+$")){
                req.setAttribute("status",  400);
                req.setAttribute("mensagem", "numero inválido!");
                req.getRequestDispatcher("/WEB-INF/VIEW/erroCadastroEmpresa.jsp").forward(req, res);
                return;
            }

            //valida a rua
            if (!rua.matches("^[A-Za-zÀ-ÖØ-öø-ÿ]+(?:\\s+[A-Za-zÀ-ÖØ-öø-ÿ]+)*$")){
                req.setAttribute("status",  400);
                req.setAttribute("mensagem", "Rua inválida!");
                req.getRequestDispatcher("/WEB-INF/VIEW/erroCadastroEmpresa.jsp").forward(req, res);
                return;
            }

            //valida o complemento
            if (!complemento.matches("^[A-Za-zÀ-ÖØ-öø-ÿ]+(?:\\s+[A-Za-zÀ-ÖØ-öø-ÿ]+)*$")){
                req.setAttribute("status",  400);
                req.setAttribute("mensagem", "Complemento inválido!");
                req.getRequestDispatcher("/WEB-INF/VIEW/erroCadastroEmpresa.jsp").forward(req, res);
                return;
            }

        }catch (NullPointerException npe){
            req.setAttribute("status", 400);
            req.setAttribute("mensagem", "preencha todos os itens");
            req.getRequestDispatcher("/WEB-INF/VIEW/erroCadastroEmpresa.jsp").forward(req, res);
            return;
        }

        //cria a empresa
        empresa = new EmpresaModel(nome, cnpj, email, senha);

        //envia a empresa para o banco
        if(!empresaDAO.inserir(empresa)){
            //jsp de erro caso dar errado
            req.setAttribute("status", 500);
            req.setAttribute("mensagem", "tente novamente");
            req.getRequestDispatcher("/WEB-INF/VIEW/erroCadastroEmpresa.jsp").forward(req, res);
        }

        //cria o endereco
        enderecoEmpresa = new EnderecoEmpresaModel(
                estado, cep, cidade, bairro, rua, Integer.parseInt(numero), complemento, empresa.getId()
        );

        //envia o endereco para o banco
        if (!enderecoEmpresaDAO.inserir(enderecoEmpresa)){
            req.setAttribute("status", 500);
            req.setAttribute("mensagem", "tente novamente");
            req.getRequestDispatcher("/WEB-INF/VIEW/erroCadastroEmpresa.jsp").forward(req, res);
        }

        //envia o usuario para o login
        res.sendRedirect("Login/login");
    }
}
