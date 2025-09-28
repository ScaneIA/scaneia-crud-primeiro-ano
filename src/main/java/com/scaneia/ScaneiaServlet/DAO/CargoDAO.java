package com.scaneia.ScaneiaServlet.DAO;

import com.scaneia.ScaneiaServlet.Model.CargoModel;
import com.scaneia.ScaneiaServlet.conexao.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CargoDAO {
    public boolean insert(CargoModel cargo){
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return false;
        }
        else{
            try(PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO CARGOS (NOME, DESCRICAO) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS)){

                ResultSet rs = pstmt.getGeneratedKeys();
                pstmt.setString(1,cargo.getNome());
                pstmt.setString(2, cargo.getDescricao());
                int retorno=pstmt.executeUpdate();

                if (rs.next()) {
                    cargo.setId(rs.getInt(1));
                }

                return retorno>0;
            } catch (SQLException se) {
                se.printStackTrace();
                return false;
            } finally {
                conexao.desconectar();
            }
        }

    }

    public boolean insertNome(CargoModel cargo){
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return false;
        }
        else{
            try(PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO CARGOS (NOME) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS)){

                ResultSet rs = pstmt.getGeneratedKeys();
                pstmt.setString(1,cargo.getNome());
                if (rs.next()) {
                    cargo.setId(rs.getInt(1));
                }
                return pstmt.executeUpdate()>0;

            } catch (SQLException se) {
                se.printStackTrace();
                return false;
            } finally {
                conexao.desconectar();
            }
        }

    }

    public boolean update(CargoModel cargo){
        Conexao conexao= new Conexao();
        Connection conn= conexao.getConnection();
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return false;
        }
        else{

            try{
                PreparedStatement pstmt = conn.prepareStatement("UPDATE CARGOS SET NOME=?, DESCRICAO=? WHERE ID=?");
                pstmt.setString(1, cargo.getNome());
                pstmt.setString(2, cargo.getDescricao());
                pstmt.setInt(3, cargo.getId());
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

    public boolean updateNome(CargoModel cargo){
        Conexao conexao= new Conexao();
        Connection conn= conexao.getConnection();
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return false;
        }
        else{

            try{
                PreparedStatement pstmt = conn.prepareStatement("UPDATE CARGOS SET NOME=? WHERE ID=?");
                pstmt.setString(1, cargo.getNome());
                pstmt.setInt(2, cargo.getId());
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
    public boolean updateDescricao(CargoModel cargo){
        Conexao conexao= new Conexao();
        Connection conn= conexao.getConnection();
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return false;
        }
        else{

            try{
                PreparedStatement pstmt = conn.prepareStatement("UPDATE CARGOS SET DESCRICAO=? WHERE ID=?");
                pstmt.setString(1, cargo.getDescricao());
                pstmt.setInt(2, cargo.getId());
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
    public boolean delete(CargoModel cargo){
        Conexao conexao= new Conexao();
        Connection conn= conexao.getConnection();
        if(conn==null){
            System.out.println("Não foi possível conectar");
            return false;
        }
        else{
            try{
                String remover="UPDATE CARGOS SET DATAEXCLUSAO = NOW() WHERE ID=?";
                PreparedStatement pstmt= conn.prepareStatement(remover);
                pstmt.setInt(1,cargo.getId());
                return pstmt.executeUpdate() >0;
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

    public List<CargoModel> buscar(CargoModel cargo){
        Conexao conexao= new Conexao();
        Connection conn= conexao.getConnection();
        ResultSet rset = null;
        List<CargoModel> listaBusca= new ArrayList<>();
        try{
            Statement stmt = conn.createStatement();
            rset= stmt.executeQuery("SELECT * FROM CARGOS");

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

                CargoModel cargo1 = new CargoModel(id, nome, descricao, dataExclusao, dataAtualizacao, dataCriacao);

                listaBusca.add(cargo1);
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

    public List<CargoModel> buscarPorNome(CargoModel cargo){
        Conexao conexao= new Conexao();
        Connection conn= conexao.getConnection();
        ResultSet rset = null;
        List<CargoModel> listaBusca= new ArrayList<>();
        try{
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM CARGOS WHERE NOME = ? ORDER BY 1");
            pstmt.setString(1, cargo.getNome());
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

                CargoModel area1 = new CargoModel(id, nome, descricao, dataExclusao, dataAtualizacao, dataCriacao);

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
