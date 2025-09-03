package DAO;

import DAO.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {
    public boolean insert(){
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return false;
        }
        else{
            try (PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO USUARIO (NOME, EMAIL, CPF) VALUES (?, ?, ?)")) {
                pstmt.setString(1,"Gustavo Amex");
                pstmt.setString(2, "gustavo.amex@gmail.com");
                pstmt.setInt(3, 569874534);
                pstmt.execute();

            } catch (SQLException se) {
                se.printStackTrace();
            } finally {
                conexao.desconectar();
            }
            return true;
        }

    }
    public boolean update(){
        Conexao conexao= new Conexao();
        Connection conn= conexao.getConnection();
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return false;
        }
        else{

            try{
                PreparedStatement pstmt= conn.prepareStatement("UPDATE USUARIO SET NOME=? WHERE CPF=? ");
                pstmt.setString(1,"Aline");
                pstmt.setInt(2,569874534);
                pstmt.execute();
            }
            catch (SQLException se){
                se.printStackTrace();
            }
            finally {
                conexao.desconectar();
            }
            return true;
        }
    }
    public boolean delete(){
        Conexao conexao= new Conexao();
        Connection conn= conexao.getConnection();
        if(conn==null){
            System.out.println("Não foi possível conectar");
            return false;
        }
        else{
            try{
                String remover="DELETE FROM USUARIO WHERE CPF=?";
                PreparedStatement pstmt= conn.prepareStatement(remover);
                pstmt.setInt(1,569874532);
                pstmt.execute();
            }
            catch (SQLException se){
                se.printStackTrace();
            }
            finally {
                conexao.desconectar();
            }
            return true;
        }

    }

}
