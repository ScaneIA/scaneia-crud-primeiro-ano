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
                "INSERT INTO EMPRESAS (NOME, CNPJ, EMAIL, SENHA, DATACRIACAO, DATAATUALIZACAO) VALUES (?, ?, ?, ?, NOW(), NOW())",
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

    // Atualizar empresa completa (usa quando for alterar a senha também)
    public int atualizar(EmpresaModel empresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return -1; // erro na conexão
        }
        // prepara o comando
        try (PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE EMPRESAS SET NOME=?, CNPJ=?, EMAIL=?, SENHA=?, DATAATUALIZACAO=NOW() WHERE ID=?")) {

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


    public int atualizarSemSenha(EmpresaModel empresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return -1; // erro na conexão
        }

        try (PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE EMPRESAS SET NOME=?, CNPJ=?, EMAIL=?, DATAATUALIZACAO=NOW() WHERE ID=?")) {

            pstmt.setString(1, empresa.getNome());
            pstmt.setString(2, empresa.getCnpj());
            pstmt.setString(3, empresa.getEmail());
            pstmt.setInt(4, empresa.getId());

            int retorno = pstmt.executeUpdate();
            if (retorno > 0) {
                empresa.setDataAtualizacao(LocalDateTime.now());
                return 1; // deu certo
            } else return 0; // nada alterado

        } catch (SQLException se) {
            se.printStackTrace();
            return -2;
        } catch (Exception e) {
            e.printStackTrace();
            return -3;
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
        try (PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE EMPRESAS SET NOME=?, DATAATUALIZACAO=NOW() WHERE ID=?")) {

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

    // Atualizando só o CNPJ
    public int atualizarCnpj(EmpresaModel empresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return -1; // erro na conexão
        }
        // prepara
        try (PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE EMPRESAS SET CNPJ=?, DATAATUALIZACAO=NOW() WHERE ID=?")) {

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
            return -3; // nada alterado
        } finally {
            conexao.desconectar();
            // desconecta
        }
    }

    // Atualizando o email e senha
    public int atualizarEmail(EmpresaModel empresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return -1; // erro na conexão
        }
        // prepara
        try (PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE EMPRESAS SET EMAIL=?, DATAATUALIZACAO=NOW() WHERE ID=?")) {

            pstmt.setString(1, empresa.getEmail());
            pstmt.setInt(2, empresa.getId());

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
        try (PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE EMPRESAS SET DATAEXCLUSAO = NOW() WHERE ID=?")) {

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
        try (Statement stmt = conn.createStatement();
             ResultSet rset = stmt.executeQuery("SELECT * FROM EMPRESAS WHERE DATAEXCLUSAO IS NULL")) {

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
        try (PreparedStatement pstmt = conn.prepareStatement(
                "SELECT * FROM EMPRESAS WHERE LOWER(NOME) LIKE ? AND DATAEXCLUSAO IS NULL ORDER BY 1")) {

            pstmt.setString(1, empresa.getNome().toLowerCase() + "%");
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
        // retorna a lista
    }

    // Buscar por ID
    public EmpresaModel buscarId(int id) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        EmpresaModel empresa = null;

        if (conn == null) return null;

        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM EMPRESAS WHERE ID=? AND DATAEXCLUSAO IS NULL")) {
            pstmt.setInt(1, id);
            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                String nome = rset.getString("nome");
                String cnpj = rset.getString("cnpj");
                String email = rset.getString("email");
                String senha = rset.getString("senha");

                // datas
                LocalDateTime dataExclusao = null;
                if (rset.getTimestamp("dataExclusao") != null)
                    dataExclusao = rset.getTimestamp("dataExclusao").toLocalDateTime();

                LocalDateTime dataAtualizacao = rset.getTimestamp("dataAtualizacao").toLocalDateTime();
                LocalDateTime dataCriacao = rset.getTimestamp("dataCriacao").toLocalDateTime();
                empresa = new EmpresaModel(id, nome, cnpj, email, senha, dataCriacao, dataAtualizacao, dataExclusao);
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            conexao.desconectar();
        }

        return empresa;
    }

    // Login da empresa
    public EmpresaModel login(String email, String senha, String cnpj) {
        //variaveis gerais
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        List<EmpresaModel> empresas = new ArrayList<>();
        int encontrados = 0;

        if (conn == null) {
            return null;
        }

        //faz a consulta sql
        try (PreparedStatement pstmt = conn.prepareStatement(
                "SELECT * FROM EMPRESAS WHERE CNPJ = ? AND EMAIL = ? AND SENHA = ? AND DATAEXCLUSAO IS NULL")) {

            //atualiza os parametros
            pstmt.setString(1, cnpj);
            pstmt.setString(2, email);
            pstmt.setString(3, senha);

            //faz a consulta
            ResultSet rs = pstmt.executeQuery();

            //itera sob os dados
            while (rs.next()) {
                //pega os campos
                int newId = rs.getInt("id");
                String newNome = rs.getString("nome");
                String newCnpj = rs.getString("cnpj");
                String newEmail = rs.getString("email");
                String newSenha = rs.getString("senha");
                String newDataExclusao = rs.getString("dataexclusao");

                //vê se é uma empresa apagada
                if (newDataExclusao == null) {
                    empresas.add(new EmpresaModel(newId, newNome, newCnpj, newEmail, newSenha));
                    encontrados++;
                }
            }

            //retorna true se só encontrar 1
            if (encontrados == 1) {
                return empresas.get(0);
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            conexao.desconectar();
        }
    }
}
