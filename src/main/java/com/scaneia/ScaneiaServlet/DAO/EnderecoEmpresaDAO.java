package com.scaneia.ScaneiaServlet.DAO;

import com.scaneia.ScaneiaServlet.Model.EnderecoEmpresaModel;
import com.scaneia.ScaneiaServlet.conexao.Conexao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EnderecoEmpresaDAO {

    // Inserir endereço
    public int inserir(EnderecoEmpresaModel endereco) {
        //cria a conexão
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        // verifica a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            // erro na conexão
            return -1;
        }
        //prepara o sql
        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO ENDERECOS_EMPRESAS (IDEMPRESAS, RUA, NUMERO, COMPLEMENTO, ESTADO, CIDADE, BAIRRO, CEP) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            //coloca os parametros
            pstmt.setInt(1, endereco.getIdEmpresa());
            pstmt.setString(2, endereco.getRua());
            pstmt.setInt(3, endereco.getNumero());
            pstmt.setString(4, endereco.getComplemento());
            pstmt.setString(5, endereco.getEstado());
            pstmt.setString(6, endereco.getCidade());
            pstmt.setString(7, endereco.getBairro());
            pstmt.setString(8, endereco.getCep());

            //executa o comando
            int retorno = pstmt.executeUpdate();

            // pega o ID gerado
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    endereco.setId(rs.getInt(1));
                }
            }

            if (retorno > 0) {
                endereco.setDataCriacao(LocalDateTime.now());
                endereco.setDataAtualizacao(LocalDateTime.now());
                // colocando esses valores no objeto
                // sucesso
                return 1;
            } else {
                // nada alterado
                return 0;
            }

        } catch (SQLException se) {
            se.printStackTrace();
            // erro SQL
            return -2;
        } catch (Exception e) {
            e.printStackTrace();
            // outro erro
            return -3;
        } finally {
            // fecha a conexão
            conexao.desconectar();
        }
    }

    // Atualizar endereço completo
    public int atualizar(EnderecoEmpresaModel endereco) {
        // cria a conexão
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        // verifica a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            // erro na conexão
            return -1;
        }
        // prepara o comando sql
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE ENDERECOS_EMPRESAS SET RUA=?, NUMERO=?, COMPLEMENTO=?, ESTADO=?, CIDADE=?, BAIRRO=?, CEP=?, DATAATUALIZACAO=NOW() WHERE ID=?"
            );

            // coloca os parametros
            pstmt.setString(1, endereco.getRua());
            pstmt.setInt(2, endereco.getNumero());
            pstmt.setString(3, endereco.getComplemento());
            pstmt.setString(4, endereco.getEstado());
            pstmt.setString(5, endereco.getCidade());
            pstmt.setString(6, endereco.getBairro());
            pstmt.setString(7, endereco.getCep());
            pstmt.setInt(8, endereco.getId());

            // executa o comando
            int retorno = pstmt.executeUpdate();

            if (retorno > 0) {
                endereco.setDataAtualizacao(LocalDateTime.now());
                // colocando esse valor no objeto
                // sucesso
                return 1;
            } else {
                // nada alterado
                return 0;
            }

        } catch (SQLException se) {
            se.printStackTrace();
            // erro SQL
            return -2;
        } catch (Exception e) {
            e.printStackTrace();
            // outro erro
            return -3;
        } finally {
            // desconecta
            conexao.desconectar();
        }
    }

    // Buscar todos os endereços
    public List<EnderecoEmpresaModel> buscar() {
        // cria a conexão
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        List<EnderecoEmpresaModel> lista = new ArrayList<>();

        // verifica a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return lista;
        }

        // prepara o comando
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("SELECT * FROM ENDERECOS_EMPRESAS WHERE DATAEXCLUSAO IS NULL");

            while (rset.next()) {
                int id = rset.getInt("id");
                int idEmpresa = rset.getInt("idEmpresas");
                String estado = rset.getString("estado");
                String cep = rset.getString("cep");
                String cidade = rset.getString("cidade");
                String bairro = rset.getString("bairro");
                String rua = rset.getString("rua");
                int numero = rset.getInt("numero");
                String complemento = rset.getString("complemento");

                LocalDateTime dataAtualizacao = rset.getTimestamp("dataAtualizacao").toLocalDateTime();
                LocalDateTime dataCriacao = rset.getTimestamp("dataCriacao").toLocalDateTime();

                // verifica se dataExclusao é null
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
            // erro de banco
            se.printStackTrace();
        } finally {
            // desconecta
            conexao.desconectar();
        }
        // retorna a lista
        return lista;
    }

    public List<EnderecoEmpresaModel> buscarPorIdEmpresa(int idEmpresa) {
        // cria a conexão
        Conexao conexao = new Conexao();
        List<EnderecoEmpresaModel> lista = new ArrayList<>();
        Connection conn = conexao.getConnection();

        // verifica a conexão
        if (conn == null) return lista;

        // prepara o comando
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT * FROM ENDERECOS_EMPRESAS WHERE IDEMPRESAS = ? AND DATAEXCLUSAO IS NULL"
            );
            pstmt.setInt(1, idEmpresa);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                int id = rset.getInt("id");
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

        } catch (SQLException e) {
            // erro de banco
            e.printStackTrace();
        } finally {
            // desconecta
            conexao.desconectar();
        }
        // retorna a lista
        return lista;
    }

}