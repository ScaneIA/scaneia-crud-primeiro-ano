package com.scaneia.ScaneiaServlet.DAO;

import com.scaneia.ScaneiaServlet.Config.HashSenha;
import com.scaneia.ScaneiaServlet.Model.AdministradorModel;
import com.scaneia.ScaneiaServlet.conexao.Conexao;

import java.sql.*;
import java.time.LocalDateTime;
import java.security.NoSuchAlgorithmException;

public class AdministradorDAO {
    public AdministradorModel buscarPorEmail(String email) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        AdministradorModel admin = null;

        if (conn == null) return null;

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
                LocalDateTime dataAtualizacao = null;
                if (rset.getTimestamp("dataatualizacao") != null) {
                    dataAtualizacao = rset.getTimestamp("dataatualizacao").toLocalDateTime();
                }
                LocalDateTime dataExclusao = null;
                if (rset.getTimestamp("dataexclusao") != null) {
                    dataExclusao = rset.getTimestamp("dataexclusao").toLocalDateTime();
                }

                admin = new AdministradorModel(id, emailDB, null, dataCriacao, dataAtualizacao, dataExclusao);
            }

            rset.close();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar();
        }

        return admin;
    }

}
