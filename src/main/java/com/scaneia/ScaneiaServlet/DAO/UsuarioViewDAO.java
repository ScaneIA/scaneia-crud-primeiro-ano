package com.scaneia.ScaneiaServlet.DAO;

import com.scaneia.ScaneiaServlet.Model.UsuarioViewModel;
import com.scaneia.ScaneiaServlet.conexao.Conexao;

import java.sql.*;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UsuarioViewDAO {
    public List<UsuarioViewModel> buscarPorEmpresa(int idEmpresa){
        //variaveis gerais
        List<UsuarioViewModel> usuarios = new ArrayList<>();

        //cria a conexao
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        if (conn == null){
            return null;
        }

        //cria o script sql
        try{

            String sql = "SELECT * FROM USUARIOS_SETORES_CARGO WHERE ID_EMPRESA = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //atualiza os atributos
            pstmt.setInt(1, idEmpresa);

            ResultSet rs = pstmt.executeQuery();

            //itera sob o resultado
            while (rs.next()){
                int id = rs.getInt("id_usuario");
                String nome = rs.getString("nome_usuario");
                String email = rs.getString("email_usuario");
                String cpf = rs.getString("cpf_usuario");
                String setor = rs.getString("setor_usuario");
                String cargo = rs.getString("cargo_usuario");
                String urlFoto = rs.getString("url_foto_usuario");
                String registro = rs.getString("data_criacao");

                usuarios.add(new UsuarioViewModel(
                        id, nome, email, cpf, setor, cargo, urlFoto, formatarHora(registro)
                ));
            }

            return usuarios;
        }catch (SQLException sqle){
            return null;
        }finally {
            conexao.desconectar();
        }
    }

    public List<UsuarioViewModel> filtrarPorCargo(String cargoFiltro, int idEmpresa){
        //variaveis gerais
        List<UsuarioViewModel> usuarios = new ArrayList<>();

        //cria a conexão
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        if (conn == null){
            return null;
        }

        //cria o script sql
        try {
            //prepara a consulta
            String sql = "SELECT * FROM USUARIOS_SETORES_CARGO WHERE CARGO_USUARIO = ? AND ID_EMPRESA = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //atualiza os parametros
            pstmt.setString(1, cargoFiltro);
            pstmt.setInt(2, idEmpresa);

            //executa o comando
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id_usuario");
                String nome = rs.getString("nome_usuario");
                String email = rs.getString("email_usuario");
                String cpf = rs.getString("cpf_usuario");
                String setor = rs.getString("setor_usuario");
                String cargo = rs.getString("cargo_usuario");
                String urlFoto = rs.getString("url_foto_usuario");
                String registro = rs.getString("data_criacao");

                usuarios.add(new UsuarioViewModel(
                        id, nome, email, cpf, setor, cargo, urlFoto, formatarHora(registro)
                ));
            }
            return usuarios;

        }catch (SQLException exception){
            return null;
        }finally {
            conexao.desconectar();
        }
    }

    public UsuarioViewModel buscarPorId(int idEmpresa, int idUsuario){
        //variaveis gerais
        UsuarioViewModel usuario = null;

        //cria a conexao
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        if (conn == null){
            return null;
        }

        //cria o script sql
        try{

            String sql = "SELECT * FROM USUARIOS_SETORES_CARGO WHERE ID_EMPRESA = ? AND ID_USUARIO = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //atualiza os atributos
            pstmt.setInt(1, idEmpresa);
            pstmt.setInt(2, idUsuario);

            ResultSet rs = pstmt.executeQuery();

            //itera sob o resultado
            while (rs.next()){
                int id = rs.getInt("id_usuario");
                String nome = rs.getString("nome_usuario");
                String email = rs.getString("email_usuario");
                String cpf = rs.getString("cpf_usuario");
                String setor = rs.getString("setor_usuario");
                String cargo = rs.getString("cargo_usuario");
                String urlFoto = rs.getString("url_foto_usuario");
                String registro = rs.getString("data_criacao");

                usuario = new UsuarioViewModel(id, nome, email, cpf, setor, cargo, urlFoto, registro);
            }

            return usuario;
        }catch (SQLException sqle){
            return null;
        }finally {
            conexao.desconectar();
        }
    }

    public String formatarHora(String hora){
        //pega a hora no modelo ceto
        DateTimeFormatter formatterEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSX");
        OffsetDateTime horaNormal = OffsetDateTime.parse(hora, formatterEntrada);

        //cria o formatter da saida
        DateTimeFormatter formatterSaida = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return horaNormal.format(formatterSaida);
    }
}
