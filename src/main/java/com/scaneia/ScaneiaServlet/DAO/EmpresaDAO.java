package com.scaneia.ScaneiaServlet.DAO;

import com.scaneia.ScaneiaServlet.Model.EmpresaModel;
import com.scaneia.ScaneiaServlet.conexao.Conexao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {

    // Inserir empresa completa
    public int inserir(EmpresaModel empresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return -1; // erro de conexão
        }
        // prepara
        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO EMPRESAS (NOME, CNPJ, EMAIL, SENHA) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, empresa.getNome());
            pstmt.setString(2, empresa.getCnpj());
            pstmt.setString(3, empresa.getEmail());
            pstmt.setString(4, empresa.getSenha());

            // executa
            int retorno = pstmt.executeUpdate();

            // pega o ID gerado
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    empresa.setId(rs.getInt(1));
                }
            }

            if (retorno > 0) {
                empresa.setDataCriacao(LocalDateTime.now());
                empresa.setDataAtualizacao(LocalDateTime.now());
                // colocando esses valores no objeto
                return 1; // sucesso
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

    // Atualizar empresa completa
    public int atualizar(EmpresaModel empresa) {
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
                    "UPDATE EMPRESAS SET NOME=?, CNPJ=?, EMAIL=?, SENHA=?, DATAATUALIZACAO=NOW() WHERE ID=?");

            pstmt.setString(1, empresa.getNome());
            pstmt.setString(2, empresa.getCnpj());
            pstmt.setString(3, empresa.getEmail());
            pstmt.setString(4, empresa.getSenha());
            pstmt.setInt(5, empresa.getId());
            // executa
            int retorno = pstmt.executeUpdate();
            if (retorno > 0) {
                empresa.setDataAtualizacao(LocalDateTime.now());
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
        }
    }

    // Atualizando só o nome
    public int atualizarNome(EmpresaModel empresa) {
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
                    "UPDATE EMPRESAS SET NOME=?, DATAATUALIZACAO=NOW() WHERE ID=?");

            pstmt.setString(1, empresa.getNome());
            pstmt.setInt(2, empresa.getId());

            //executa
            int retorno = pstmt.executeUpdate();
            if (retorno > 0) {
                empresa.setDataAtualizacao(LocalDateTime.now());
                // colocando esse valor no objeto
                return 1; // deu certo
            } else return 0; // nada alterado

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

    // Atuliazando só o CNPJ
    public int atualizarCnpj(EmpresaModel empresa) {
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
                    "UPDATE EMPRESAS SET CNPJ=?, DATAATUALIZACAO=NOW() WHERE ID=?");

            pstmt.setString(1, empresa.getCnpj());
            pstmt.setInt(2, empresa.getId());

            int retorno = pstmt.executeUpdate();
            if (retorno > 0) {
                empresa.setDataAtualizacao(LocalDateTime.now());
                // colocando esse valor no objeto
                return 1; // deu certo
            } else return 0; // nada alterado

        } catch (SQLException se) {
            se.printStackTrace();
            return -2; // erro SQL
        } catch (Exception e) {
            e.printStackTrace();
            return -3; // nada altardo
        } finally {
            conexao.desconectar();
            // desconecta
        }
    }

    // Atualizando o email e senha
    public int atualizarEmailSenha(EmpresaModel empresa) {
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
                    "UPDATE EMPRESAS SET EMAIL=?, SENHA=?, DATAATUALIZACAO=NOW() WHERE ID=?");

            pstmt.setString(1, empresa.getEmail());
            pstmt.setString(2, empresa.getSenha());
            pstmt.setInt(3, empresa.getId());

            // executa
            int retorno = pstmt.executeUpdate();
            if (retorno > 0) {
                empresa.setDataAtualizacao(LocalDateTime.now());
                // colocando esse valor no objeto
                return 1; // deu certo
            } else return 0; // nada alterado

        } catch (SQLException se) {
            se.printStackTrace();
            return -2; // erro na conexão
        } catch (Exception e) {
            e.printStackTrace();
            return -3; // outro erro
        } finally {
            conexao.desconectar();
            // desconecta
        }
    }

    // Deletando empresa
    public int deletar(EmpresaModel empresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return -1; // erro na conexão
        }
        // prepara o comando
        try {
            String remover = "UPDATE EMPRESAS SET DATAEXCLUSAO = NOW() WHERE ID=?";
            PreparedStatement pstmt = conn.prepareStatement(remover);
            pstmt.setInt(1, empresa.getId());

            // executa
            int retorno = pstmt.executeUpdate();
            if (retorno > 0) {
                empresa.setDataExclusao(LocalDateTime.now());
                // colocando esse valor no objeto
                return 1; // deu certo
            } else return 0; // nada alterado

        } catch (SQLException se) {
            se.printStackTrace();
            return -2; // erro SQL
        } catch (Exception e) {
            e.printStackTrace();
            return -3; // outro erro
        } finally {
            conexao.desconectar();
            //desconecta
        }
    }

    // Buscar todas as empresas
    public List<EmpresaModel> buscar() {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        List<EmpresaModel> lista = new ArrayList<>();
        // cria a conexão
        if (conn == null) return lista;

        // prepara
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("SELECT * FROM EMPRESAS");

            while (rset.next()) {
                int id = rset.getInt("id");
                String nome = rset.getString("nome");
                String cnpj = rset.getString("cnpj");
                String email = rset.getString("email");
                String senha = rset.getString("senha");

                // verifica se dataExclusao é null
                LocalDateTime dataExclusao = null;
                if (rset.getTimestamp("dataExclusao") != null)
                    dataExclusao = rset.getTimestamp("dataExclusao").toLocalDateTime();

                LocalDateTime dataAtualizacao = rset.getTimestamp("dataAtualizacao").toLocalDateTime();
                LocalDateTime dataCriacao = rset.getTimestamp("dataCriacao").toLocalDateTime();

                EmpresaModel emp = new EmpresaModel(id, nome, cnpj, email, senha, dataCriacao, dataAtualizacao, dataExclusao);
                lista.add(emp);
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            conexao.desconectar();
            // desconecta
        }
        return lista;
        // retorna a lista
    }

    // Buscar por nome
    public List<EmpresaModel> buscarPorNome(EmpresaModel empresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        List<EmpresaModel> lista = new ArrayList<>();
        // cria a conexão
        if (conn == null) return lista;

        // prepara o comando
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

                // verifica se dataExclusao é null
                LocalDateTime dataExclusao = null;
                if (rset.getTimestamp("dataExclusao") != null)
                    dataExclusao = rset.getTimestamp("dataExclusao").toLocalDateTime();

                LocalDateTime dataAtualizacao = rset.getTimestamp("dataAtualizacao").toLocalDateTime();
                LocalDateTime dataCriacao = rset.getTimestamp("dataCriacao").toLocalDateTime();

                EmpresaModel emp = new EmpresaModel(id, nome, cnpj, email, senha, dataCriacao, dataAtualizacao, dataExclusao);
                lista.add(emp);
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            conexao.desconectar();
            // desconecta
        }

        return lista;
        // retorna a lisat
    }
}
