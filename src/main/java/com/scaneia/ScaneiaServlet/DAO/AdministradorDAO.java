package com.scaneia.ScaneiaServlet.DAO;

import com.scaneia.ScaneiaServlet.Config.HashSenha;
import com.scaneia.ScaneiaServlet.Model.AdministradorModel;
import com.scaneia.ScaneiaServlet.Model.EmpresaModel;
import com.scaneia.ScaneiaServlet.conexao.Conexao;

import java.sql.*;
import java.time.LocalDateTime;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO {
    public AdministradorModel buscarPorEmail(String email) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        AdministradorModel admin = null;
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            // erro na conexão
        }
        // faz a consulta sql
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT * FROM ADMINISTRADOR WHERE LOWER(EMAIL) = LOWER(?) AND DATAEXCLUSAO IS NULL"
            );
            pstmt.setString(1, email);

            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                int id = rset.getInt("id");
                String emailDB = rset.getString("email");
                LocalDateTime dataCriacao = rset.getTimestamp("datacriacao").toLocalDateTime();
                // verifica se dataExclusao e atualizacao são null
                LocalDateTime dataAtualizacao = null;
                if (rset.getTimestamp("dataatualizacao") != null) {
                    dataAtualizacao = rset.getTimestamp("dataatualizacao").toLocalDateTime();
                }
                LocalDateTime dataExclusao = null;
                if (rset.getTimestamp("dataexclusao") != null) {
                    dataExclusao = rset.getTimestamp("dataexclusao").toLocalDateTime();
                }
                // coloca os valores no objeto
                admin = new AdministradorModel(id, emailDB, null, dataCriacao, dataAtualizacao, dataExclusao);
            }

            rset.close();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // desconecta
            conexao.desconectar();
        }

        return admin;
    }

    public AdministradorModel login(String email, String senha){
        //variaveis gerais
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        List<AdministradorModel> encontradosLista = new ArrayList<>();
        int encontrados = 0;

        if (conn == null){
            return null;
        }

        //faz a consulta sql
        try{
            //prepara o statement
            String sql = "SELECT * FROM ADMINISTRADOR WHERE EMAIL = ? AND SENHA = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //atualiza os parametros
            pstmt.setString(1, email);
            pstmt.setString(2, senha);

            //faz a consulta
            ResultSet rs = pstmt.executeQuery();

            //itera sob os dados
            while (rs.next()){
                //pega os campos
                int id = rs.getInt("id");
                String newEmail = rs.getString("email");
                String newSenha = rs.getString("senha");
                String dataExclusao = rs.getString("dataexclusao");

                //vê se é uma empresa apagada
                if (dataExclusao == null){
                    encontradosLista.add(new AdministradorModel(id, email, senha));
                    encontrados++;
                }
            }

            //retorna true se só encontar 1
            if (encontrados == 1){
                return encontradosLista.get(0);
            }else {
                return null;
            }

        }catch (SQLException e){
            return null;
        }finally {
            // desconecta
            conexao.desconectar();
        }
    }

}
