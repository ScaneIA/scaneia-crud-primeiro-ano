package TestDAO;

import DAO.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class TestDAO {
    public void insert(){
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return;
        }

        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO USUARIO (NOME, EMAIL, CPF) VALUES (?, ?, ?)")) {
            pstmt.setString(1,"Gustavo Amex");
            pstmt.setString(2, "gustavo.amex@gmail.com");
            pstmt.setInt(3, 569874532);
            pstmt.execute();
            System.out.println("Inserção realizada com sucesso");
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            conexao.desconectar();
        }
    }
    public void atualizar(){}

}
