package DAO;

import Conexao.Conexao;
import Model.AreaModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class AreaDAO {
    public boolean inserir(AreaModel area){
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return false;
        }
        else{
            try(PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO AREAS (NOME, DESCRICAO) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS)){

                pstmt.setString(1,area.getNome());
                pstmt.setString(2, area.getDescricao());
                int retorno = pstmt.executeUpdate();
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        area.setId(rs.getInt(1));
                    }
                }
                return retorno > 0;
            } catch (SQLException se) {
                se.printStackTrace();
                return false;
            } finally {
                conexao.desconectar();
            }
        }

    }

    public boolean inserirNome(AreaModel area){
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return false;
        }
        else{
            try(PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO AREAS (NOME) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS)){

                pstmt.setString(1,area.getNome());
                int retorno = pstmt.executeUpdate();
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        area.setId(rs.getInt(1));
                    }
                }
                return retorno > 0;

            } catch (SQLException se) {
                se.printStackTrace();
                return false;
            } finally {
                conexao.desconectar();
            }
        }

    }

    public boolean update(AreaModel area){
        Conexao conexao= new Conexao();
        Connection conn= conexao.getConnection();
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return false;
        }
        else{

            try{
                PreparedStatement pstmt = conn.prepareStatement("UPDATE AREAS SET NOME=?, DESCRICAO=? WHERE ID=?");
                pstmt.setString(1, area.getNome());
                pstmt.setString(2, area.getDescricao());
                pstmt.setInt(3, area.getId());
                return pstmt.executeUpdate()>0;
            }
            catch (SQLException se){
                se.printStackTrace();
                return false;
            }
            finally {
                conexao.desconectar();
            }
        }
    }

    public boolean updateNome(AreaModel area){
        Conexao conexao= new Conexao();
        Connection conn= conexao.getConnection();
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return false;
        }
        else{

            try{
                PreparedStatement pstmt = conn.prepareStatement("UPDATE AREAS SET NOME=? WHERE ID=?");
                pstmt.setString(1, area.getNome());
                pstmt.setInt(2, area.getId());
                return pstmt.executeUpdate()>0;
            }
            catch (SQLException se){
                se.printStackTrace();
                return false;
            }
            finally {
                conexao.desconectar();
            }
        }
    }
    public boolean updateDescricao(AreaModel area){
        Conexao conexao= new Conexao();
        Connection conn= conexao.getConnection();
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return false;
        }
        else{

            try{
                PreparedStatement pstmt = conn.prepareStatement("UPDATE AREAS SET DESCRICAO=? WHERE ID=?");
                pstmt.setString(1, area.getDescricao());
                pstmt.setInt(2, area.getId());
                return pstmt.executeUpdate()>0;
            }
            catch (SQLException se){
                se.printStackTrace();
                return false;
            }
            finally {
                conexao.desconectar();
            }
        }
    }
    public boolean delete(AreaModel area){
        Conexao conexao= new Conexao();
        Connection conn= conexao.getConnection();
        if(conn==null){
            System.out.println("Não foi possível conectar");
            return false;
        }
        else{
            try{
                String remover="UPDATE AREAS SET DATAEXCLUSAO = NOW() WHERE ID=?";
                PreparedStatement pstmt= conn.prepareStatement(remover);
                pstmt.setInt(1,area.getId());
                area.setDataExclusao(LocalDateTime.now());
                return pstmt.executeUpdate()>0;
            }
            catch (SQLException se){
                se.printStackTrace();
                return false;
            }
            finally {
                conexao.desconectar();
            }
        }

    }

    public List<AreaModel> buscar(){
        Conexao conexao= new Conexao();
        Connection conn= conexao.getConnection();
        ResultSet rset = null;
        List<AreaModel> listaBusca= new ArrayList<>();
        try{
            Statement stmt = conn.createStatement();
            rset= stmt.executeQuery("SELECT * FROM AREAS");

            while (rset.next()){
                int id = rset.getInt("id");
                String nome = rset.getString("nome");
                String descricao = rset.getString("descricao");

                LocalDateTime dataExclusao = null;
                if (rset.getTimestamp("dataExclusao") != null) {
                    dataExclusao = rset.getTimestamp("dataExclusao").toLocalDateTime();
                }

                LocalDateTime dataAtualizacao = rset.getTimestamp("dataAtualizacao").toLocalDateTime();
                LocalDateTime dataCriacao = rset.getTimestamp("dataCriacao").toLocalDateTime();

                AreaModel area1 = new AreaModel(id, nome, descricao, dataExclusao, dataAtualizacao, dataCriacao);

                listaBusca.add(area1);
            }

        }
        catch (SQLException se){
            se.printStackTrace();
            return null;
        }
        finally {
            conexao.desconectar();
        }
        return listaBusca;
    }

    public List<AreaModel> buscarPorNome(AreaModel area){
        Conexao conexao= new Conexao();
        Connection conn= conexao.getConnection();
        ResultSet rset = null;
        List<AreaModel> listaBusca= new ArrayList<>();
        try{
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM AREAS WHERE NOME = ? ORDER BY 1");
            pstmt.setString(1, area.getNome());
            rset = pstmt.executeQuery();
            while (rset.next()){
                int id = rset.getInt("id");
                String nome = rset.getString("nome");
                String descricao = rset.getString("descricao");

                LocalDateTime dataExclusao = null;
                if (rset.getTimestamp("dataExclusao") != null) {
                    dataExclusao = rset.getTimestamp("dataExclusao").toLocalDateTime();
                }

                LocalDateTime dataAtualizacao = rset.getTimestamp("dataAtualizacao").toLocalDateTime();
                LocalDateTime dataCriacao = rset.getTimestamp("dataCriacao").toLocalDateTime();

                AreaModel area1 = new AreaModel(id, nome, descricao, dataExclusao, dataAtualizacao, dataCriacao);

                listaBusca.add(area1);
            }

        }
        catch (SQLException se){
            se.printStackTrace();
            return null;
        }
        finally {
            conexao.desconectar();
        }
        return listaBusca;
    }
}