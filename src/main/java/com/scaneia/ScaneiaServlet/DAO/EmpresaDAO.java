package com.scaneia.ScaneiaServlet.DAO;

import com.scaneia.ScaneiaServlet.Model.EmpresaModel;
import com.scaneia.ScaneiaServlet.conexao.Conexao;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {

    // Inserir empresa completa
    public boolean inserir(EmpresaModel empresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return false;
        } else {
            try (PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO EMPRESAS (NOME, CNPJ, EMAIL, SENHA) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {

                pstmt.setString(1, empresa.getNome());
                pstmt.setString(2, empresa.getCnpj());
                pstmt.setString(3, empresa.getEmail());
                pstmt.setString(4, empresa.getSenha());

                int retorno = pstmt.executeUpdate();

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        empresa.setId(rs.getInt(1));
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

    // Atualizar empresa completa
    public boolean update(EmpresaModel empresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return false;
        } else {
            try {
                PreparedStatement pstmt = conn.prepareStatement(
                        "UPDATE EMPRESAS SET NOME=?, CNPJ=?, EMAIL=?, SENHA=?, DATAATUALIZACAO=NOW() WHERE ID=?");

                pstmt.setString(1, empresa.getNome());
                pstmt.setString(2, empresa.getCnpj());
                pstmt.setString(3, empresa.getEmail());
                pstmt.setString(4, empresa.getSenha());
                pstmt.setInt(5, empresa.getId());

                return pstmt.executeUpdate() > 0;
            } catch (SQLException se) {
                se.printStackTrace();
                return false;
            } finally {
                conexao.desconectar();
            }
        }
    }
    public boolean updateNome(EmpresaModel empresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return false;
        } else {
            try {
                PreparedStatement pstmt = conn.prepareStatement(
                        "UPDATE EMPRESAS SET NOME=? DATAATUALIZACAO=NOW() WHERE ID=?");

                pstmt.setString(1, empresa.getNome());
                pstmt.setInt(2, empresa.getId());

                return pstmt.executeUpdate() > 0;
            } catch (SQLException se) {
                se.printStackTrace();
                return false;
            } finally {
                conexao.desconectar();
            }
        }
    }
    public boolean updateCnpj(EmpresaModel empresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return false;
        } else {
            try {
                PreparedStatement pstmt = conn.prepareStatement(
                        "UPDATE EMPRESAS SET CNPJ=? DATAATUALIZACAO=NOW() WHERE ID=?");

                pstmt.setString(1, empresa.getCnpj());
                pstmt.setInt(2, empresa.getId());

                return pstmt.executeUpdate() > 0;
            } catch (SQLException se) {
                se.printStackTrace();
                return false;
            } finally {
                conexao.desconectar();
            }
        }
    }
    public boolean updateEmailSenha(EmpresaModel empresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return false;
        } else {
            try {
                PreparedStatement pstmt = conn.prepareStatement(
                        "UPDATE EMPRESAS SET EMAIL=?, SENHA=?, DATAATUALIZACAO=NOW() WHERE ID=?");

                pstmt.setString(1, empresa.getEmail());
                pstmt.setString(2, empresa.getSenha());
                pstmt.setInt(3, empresa.getId());

                return pstmt.executeUpdate() > 0;
            } catch (SQLException se) {
                se.printStackTrace();
                return false;
            } finally {
                conexao.desconectar();
            }
        }
    }
    // Deletando empresa
    public boolean delete(EmpresaModel empresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return false;
        } else {
            try {
                String remover = "UPDATE EMPRESAS SET DATAEXCLUSAO = NOW() WHERE ID=?";
                PreparedStatement pstmt = conn.prepareStatement(remover);
                pstmt.setInt(1, empresa.getId());
                empresa.setDataExclusao(LocalDateTime.now());
                return pstmt.executeUpdate() > 0;
            } catch (SQLException se) {
                se.printStackTrace();
                return false;
            } finally {
                conexao.desconectar();
            }
        }
    }

    // Buscar todas as empresas
    public List<EmpresaModel> buscar() {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        List<EmpresaModel> lista = new ArrayList<>();

        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return lista;
        }

        try {
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("SELECT * FROM EMPRESAS");

            while (rset.next()) {
                int id = rset.getInt("id");
                String nome = rset.getString("nome");
                String cnpj = rset.getString("cnpj");
                String email = rset.getString("email");
                String senha = rset.getString("senha");

                LocalDateTime dataExclusao = null;
                if (rset.getTimestamp("dataExclusao") != null) {
                    dataExclusao = rset.getTimestamp("dataExclusao").toLocalDateTime();
                }

                LocalDateTime dataAtualizacao = rset.getTimestamp("dataAtualizacao").toLocalDateTime();
                LocalDateTime dataCriacao = rset.getTimestamp("dataCriacao").toLocalDateTime();
                EmpresaModel emp = new EmpresaModel(id, nome, cnpj, email, senha, dataCriacao, dataAtualizacao,dataExclusao);
                lista.add(emp);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            conexao.desconectar();
        }
        return lista;
    }

    // Buscar por nome
    public List<EmpresaModel> buscarPorNome(EmpresaModel empresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        List<EmpresaModel> lista = new ArrayList<>();

        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return lista;
        }

        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM EMPRESAS WHERE NOME = ? ORDER BY 1");
            pstmt.setString(1, empresa.getNome());
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                int id = rset.getInt("id");
                String nome = rset.getString("nome");
                String cnpj = rset.getString("cnpj");
                String email = rset.getString("email");
                String senha = rset.getString("senha");

                LocalDateTime dataExclusao = null;
                if (rset.getTimestamp("dataExclusao") != null) {
                    dataExclusao = rset.getTimestamp("dataExclusao").toLocalDateTime();
                }

                LocalDateTime dataAtualizacao = rset.getTimestamp("dataAtualizacao").toLocalDateTime();
                LocalDateTime dataCriacao = rset.getTimestamp("dataCriacao").toLocalDateTime();

                EmpresaModel emp = new EmpresaModel(id, nome, cnpj, email, senha, dataCriacao, dataAtualizacao,dataExclusao);
                lista.add(emp);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            conexao.desconectar();
        }

        return lista;
    }

    public EmpresaModel login(String email, String senha, String cnpj){
        //variaveis gerais
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        List<EmpresaModel> empresas = new ArrayList<>();
        int encontrados = 0;

        if (conn == null){
            return null;
        }

        //faz a consulta sql
        try{
            //prepara o statement
            String sql = "SELECT * FROM EMPRESAS WHERE CNPJ = ? AND EMAIL = ? AND SENHA = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //atualiza os parametros
            pstmt.setString(1, cnpj);
            pstmt.setString(2, email);
            pstmt.setString(3, senha);

            //faz a consulta
            ResultSet rs = pstmt.executeQuery();

            //itera sob os dados
            while (rs.next()){
                //pega os campos
                int newId = rs.getInt("id");
                String newNome = rs.getString("nome");
                String newCnpj = rs.getString("cnpj");
                String newEmail = rs.getString("email");
                String newSenha = rs.getString("senha");
                String newDataExclusao = rs.getString("dataexclusao");

                //vê se é uma empresa apagada
                if (newDataExclusao == null){
                     empresas.add(new EmpresaModel(newId, newNome, newCnpj, newEmail, newSenha));
                     encontrados++;
                }
            }

            //retorna true se só encontar 1
            if (encontrados == 1){
                return empresas.getFirst();
            }else {
                return null;
            }

        }catch (SQLException e){
            return null;
        }finally {
            conexao.desconectar();
        }
    }
}
