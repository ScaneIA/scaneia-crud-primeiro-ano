package com.scaneia.ScaneiaServlet.DAO;

import com.scaneia.ScaneiaServlet.Model.EnderecoEmpresaModel;
import com.scaneia.ScaneiaServlet.conexao.Conexao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EnderecoEmpresaDAO {

    // Inserir endereço
    public boolean inserir(EnderecoEmpresaModel endereco) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return false;
        } else {
            try (PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO ENDERECOSEMPRESAS (IDEMPRESAS, RUA, NUMERO,COMPLEMENTO,ESTADO,CIDADE,BAIRRO, CEP) VALUES (?, ?, ?, ?, ?, ?,?,?)",
                    Statement.RETURN_GENERATED_KEYS)) {

                pstmt.setInt(1, endereco.getIdEmpresa());
                pstmt.setString(2, endereco.getRua());
                pstmt.setInt(3, endereco.getNumero());
                pstmt.setString(4,endereco.getComplemento());
                pstmt.setString(5, endereco.getEstado());
                pstmt.setString(6, endereco.getCidade());
                pstmt.setString(7, endereco.getBairro());
                pstmt.setString(8, endereco.getCep());

                int retorno = pstmt.executeUpdate();

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        endereco.setId(rs.getInt(1));
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

    // Atualizar endereço completo
    public boolean update(EnderecoEmpresaModel endereco) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return false;
        } else {
            try {
                PreparedStatement pstmt = conn.prepareStatement(
                        "UPDATE ENDERECOSEMPRESAS SET RUA=?, NUMERO=?,COMPLEMENTO=?,ESTADO=?,CIDADE=?,BAIRRO=?, CEP=?, DATAATUALIZACAO=NOW() WHERE ID=?");

                pstmt.setString(1, endereco.getRua());
                pstmt.setInt(2, endereco.getNumero());
                pstmt.setString(3,endereco.getComplemento());
                pstmt.setString(4, endereco.getEstado());
                pstmt.setString(5, endereco.getCidade());
                pstmt.setString(6, endereco.getBairro());
                pstmt.setString(7, endereco.getCep());
                pstmt.setInt(8, endereco.getId());

                return pstmt.executeUpdate() > 0;
            } catch (SQLException se) {
                se.printStackTrace();
                return false;
            } finally {
                conexao.desconectar();
            }
        }
    }

    // Deletando endereço
    public boolean delete(EnderecoEmpresaModel endereco) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return false;
        } else {
            try {
                PreparedStatement pstmt = conn.prepareStatement(
                        "UPDATE ENDERECO_EMPRESA SET DATAEXCLUSAO = NOW() WHERE ID=?");
                pstmt.setInt(1, endereco.getId());
                endereco.setDataExclusao(LocalDateTime.now());

                return pstmt.executeUpdate() > 0;
            } catch (SQLException se) {
                se.printStackTrace();
                return false;
            } finally {
                conexao.desconectar();
            }
        }
    }

    // Buscar todos os endereços
    public List<EnderecoEmpresaModel> buscar() {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        List<EnderecoEmpresaModel> lista = new ArrayList<>();

        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return lista;
        }

        try {
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("SELECT * FROM ENDERECO_EMPRESA");

            while (rset.next()) {
                int id = rset.getInt("id");
                int idEmpresa = rset.getInt("empresa_id");
                String estado = rset.getString("estado");
                String cep = rset.getString("cep");
                String cidade = rset.getString("cidade");
                String bairro = rset.getString("bairro");
                String rua = rset.getString("rua");
                int numero = rset.getInt("numero");
                String complemento = rset.getString("complemento");

                LocalDateTime dataAtualizacao = rset.getTimestamp("dataAtualizacao").toLocalDateTime();
                LocalDateTime dataCriacao = rset.getTimestamp("dataCriacao").toLocalDateTime();

                LocalDateTime dataExclusao = null;
                if (rset.getTimestamp("dataExclusao") != null) {
                    dataExclusao = rset.getTimestamp("dataExclusao").toLocalDateTime();
                }

                EnderecoEmpresaModel end = new EnderecoEmpresaModel(
                        id, estado, cep, cidade, bairro, rua, numero, complemento,
                        dataCriacao, dataAtualizacao, dataExclusao, idEmpresa
                );
                lista.add(end);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            conexao.desconectar();
        }
        return lista;
    }

    // Buscar por cidade
    public List<EnderecoEmpresaModel> buscarPorCidade(EnderecoEmpresaModel end) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        List<EnderecoEmpresaModel> lista = new ArrayList<>();

        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return lista;
        }

        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT * FROM ENDERECO_EMPRESA WHERE CIDADE = ? ORDER BY 1"
            );
            pstmt.setString(1, end.getCidade());
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                int id = rset.getInt("id");
                int idEmpresa = rset.getInt("empresa_id");
                String estado = rset.getString("estado");
                String cep = rset.getString("cep");
                String cidade = rset.getString("cidade");
                String bairro = rset.getString("bairro");
                String rua = rset.getString("rua");
                int numero = rset.getInt("numero");
                String complemento = rset.getString("complemento");

                LocalDateTime dataAtualizacao = rset.getTimestamp("dataAtualizacao").toLocalDateTime();
                LocalDateTime dataCriacao = rset.getTimestamp("dataCriacao").toLocalDateTime();

                LocalDateTime dataExclusao = null;
                if (rset.getTimestamp("dataExclusao") != null) {
                    dataExclusao = rset.getTimestamp("dataExclusao").toLocalDateTime();
                }

                EnderecoEmpresaModel ends = new EnderecoEmpresaModel(
                        id, estado, cep, cidade, bairro, rua, numero, complemento,
                        dataCriacao, dataAtualizacao, dataExclusao, idEmpresa
                );
                lista.add(ends);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            conexao.desconectar();
        }

        return lista;
    }
}