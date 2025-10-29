package com.scaneia.ScaneiaServlet.DAO;

import com.scaneia.ScaneiaServlet.Model.SetorModel;
import com.scaneia.ScaneiaServlet.conexao.Conexao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SetorDAO {

    // Inserir setor
    public int inserir(SetorModel setor) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return -1; // erro na conexão
        }

        // prepara o comando
        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO SETORES (DESCRICAO, NOME, IDAREAS) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, setor.getDescricao());
            pstmt.setString(2, setor.getNome());
            pstmt.setInt(3, setor.getIdArea());

            // executa
            int retorno = pstmt.executeUpdate();

            // pega o ID gerado
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    setor.setId(rs.getInt(1));
                }
            }

            if (retorno > 0) {
                setor.setDataCriacao(LocalDateTime.now());
                setor.setDataAtualizacao(LocalDateTime.now());
                // // colocando esses valores no objeto
                return 1; // deu certo
            } else {
                return 0; // nada alterado
            }

        } catch (SQLException se) {
            se.printStackTrace();
            return -2; // erroSQL
        } catch (Exception e) {
            e.printStackTrace();
            return -3; // outro erro
        } finally {
            // desconecta
            conexao.desconectar();
        }
    }

    // Atualizar nome
    public int atualizarNome(SetorModel setor) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return -1; // erro na conexão
        }
        // prepara o comando
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE SETORES SET NOME = ?, DATAATUALIZACAO = NOW() WHERE ID = ?"
            );
            pstmt.setString(1, setor.getNome());
            pstmt.setInt(2, setor.getId());
            // executa
            int retorno = pstmt.executeUpdate();

            if (retorno > 0) {
                setor.setDataAtualizacao(LocalDateTime.now());
                // colocando esse valor no objeto
                return 1; // deu certo
            } else {
                return 0; // nada alterado
            }

        } catch (SQLException se) {
            se.printStackTrace();
            return -2; // erro SQL
        } catch (Exception e) {
            e.printStackTrace();
            return -3; // outro erro
        } finally {
            // desconecta
            conexao.desconectar();
        }
    }

    // Atualizar descrição
    public int atualizarDescricao(SetorModel setor) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return -1; // erro na conexão
        }
        // prepara o comando
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE SETORES SET DESCRICAO = ?, DATAATUALIZACAO = NOW() WHERE ID = ?"
            );
            pstmt.setString(1, setor.getDescricao());
            pstmt.setInt(2, setor.getId());
            // executa
            int retorno = pstmt.executeUpdate();

            if (retorno > 0) {
                setor.setDataAtualizacao(LocalDateTime.now());
                // colocando esse valor no objeto
                return 1; // deu certo
            } else {
                return 0; // nada alterado
            }

        } catch (SQLException se) {
            se.printStackTrace();
            return -2; // erro SQL
        } catch (Exception e) {
            e.printStackTrace();
            return -3; // outro erro
        } finally {
            conexao.desconectar();
            // desconecta
        }
    }

    // Atualizar ID da área
    public int updateIdArea(SetorModel setor) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return -1; // erro na conexão
        }
        // prepara o comando
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE SETORES SET IDAREAS = ?, DATAATUALIZACAO = NOW() WHERE ID = ?"
            );
            pstmt.setInt(1, setor.getIdArea());
            pstmt.setInt(2, setor.getId());

            // executa
            int retorno = pstmt.executeUpdate();

            if (retorno > 0) {
                setor.setDataAtualizacao(LocalDateTime.now());
                // colocando esse valor no objeto
                return 1; // deu certo
            } else {
                return 0; // nada altarado
            }

        } catch (SQLException se) {
            se.printStackTrace();
            return -2; // erro SQL
        } catch (Exception e) {
            e.printStackTrace();
            return -3; // outro erro
        } finally {
            conexao.desconectar();
            // desconecta
        }
    }

    // Deletar setor
    public int delete(SetorModel setor) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return -1; // erro na conexão
        }
        // prepara
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE SETORES SET DATAEXCLUSAO = NOW() WHERE ID = ?"
            );
            pstmt.setInt(1, setor.getId());

            // executa
            int retorno = pstmt.executeUpdate();

            if (retorno > 0) {
                setor.setDataExclusao(LocalDateTime.now());
                // colocando esse valor no objeto
                return 1; // deu certo
            } else {
                return 0; // nada alterado
            }

        } catch (SQLException se) {
            se.printStackTrace();
            return -2; // erro SQL
        } catch (Exception e) {
            e.printStackTrace();
            return -3; // outro erro
        } finally {
            conexao.desconectar();
            // desconecta
        }
    }

    public int descobrirId(String nome){
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        if (conn == null){
            return -1;
        }

        //prepara a consulta
        try {
            //cria o pstmt
            String sql = "SELECT ID FROM SETORES WHERE NOME = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //atualiza os atributos
            pstmt.setString(1, nome);

            //executa a consulta
            ResultSet rs = pstmt.executeQuery();

            //retorna o id encontrado
            while (rs.next()){
                return rs.getInt("id");
            }
        }catch (SQLException exception){
            return -2;
        }catch (Exception exception){
            return -3;
        }
        finally {
            conexao.desconectar();
        }
        return -3;
    }

    public List<SetorModel> listarSetores(){
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        List<SetorModel> setores = new ArrayList<>();

        if (conn == null){
            return null;
        }

        //prepara a consulta
        try {
            //cria o pstmt
            String sql = "SELECT * FROM SETORES";
            Statement stmt  = conn.createStatement();

            //executa a consulta
            ResultSet rs = stmt.executeQuery(sql);

            //retorna o id encontrado
            while (rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");
                String dataExclusao = rs.getString("dataexclusao");

                if (dataExclusao == null){
                    setores.add(
                            new SetorModel(id, nome, descricao)
                    );
                }
            }

            return setores;
        }catch (SQLException exception){
            return null;
        }finally {
            conexao.desconectar();
        }
    }

}
