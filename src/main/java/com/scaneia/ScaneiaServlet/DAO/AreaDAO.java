package com.scaneia.ScaneiaServlet.DAO;

import com.scaneia.ScaneiaServlet.Model.AreaModel;
import com.scaneia.ScaneiaServlet.conexao.Conexao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AreaDAO {

    // Inserir área (com nome e descrição)
    public int inserir(AreaModel area) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            // erro na conexão
            return -1;
        }

        // prepara
        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO AREAS (NOME, DESCRICAO) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, area.getNome());
            pstmt.setString(2, area.getDescricao());

            // executa
            int retorno = pstmt.executeUpdate();

            // pega ID gerado
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    area.setId(rs.getInt(1));
                }
            }

            if (retorno > 0) {
                area.setDataCriacao(LocalDateTime.now());
                area.setDataAtualizacao(LocalDateTime.now());
                // colocando esses valores no objeto
                // deu certo
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

    // Inserindo apenas o nome
    public int inserirNome(AreaModel area) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            // erro na conexão
            return -1;
        }

        // prepara
        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO AREAS (NOME) VALUES (?)",
                Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, area.getNome());

            // executa
            int retorno = pstmt.executeUpdate();

            // pega ID gerado
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    area.setId(rs.getInt(1));
                }
            }

            if (retorno > 0) {
                area.setDataCriacao(LocalDateTime.now());
                area.setDataAtualizacao(LocalDateTime.now());
                // colocando esses valores no objeto
                // deu certo
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

    // Atualizando todas as informações
    public int atualizar(AreaModel area) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            // erro na conexão
            return -1;
        }

        // prepara
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE AREAS SET NOME=?, DESCRICAO=?, DATAATUALIZACAO=NOW() WHERE ID=?"
            );
            pstmt.setString(1, area.getNome());
            pstmt.setString(2, area.getDescricao());
            pstmt.setInt(3, area.getId());

            // executa
            int linhasAfetadas = pstmt.executeUpdate();

            if (linhasAfetadas > 0) {
                area.setDataAtualizacao(LocalDateTime.now());
                // colocando esse valor no objeto
                // deu certo
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

    // Atualizando apenas o nome
    public int atualizarNome(AreaModel area) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            // erro na conexão
            return -1;
        }

        // prepara
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE AREAS SET NOME=?, DATAATUALIZACAO=NOW() WHERE ID=?"
            );
            pstmt.setString(1, area.getNome());
            pstmt.setInt(2, area.getId());

            // executa
            int retorno = pstmt.executeUpdate();

            if (retorno > 0) {
                area.setDataAtualizacao(LocalDateTime.now());
                // colocando esse valor no objeto
                // deu certo
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

    // Atualizando apenas a descrição
    public int atualizarDescricao(AreaModel area) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            // erro na conexão
            return -1;
        }

        // prepara
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE AREAS SET DESCRICAO=?, DATAATUALIZACAO=NOW() WHERE ID=?"
            );
            pstmt.setString(1, area.getDescricao());
            pstmt.setInt(2, area.getId());

            // executa
            int retorno = pstmt.executeUpdate();

            if (retorno > 0) {
                area.setDataAtualizacao(LocalDateTime.now());
                // colocando esse valor no objeto
                // deu certo
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
            conexao.desconectar();
            // desconecta
        }
    }

    // Deletar área
    public int deletar(AreaModel area) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            // erro na conexão
            return -1;
        }

        // prepara
        try {
            String remover = "UPDATE AREAS SET DATAEXCLUSAO = NOW() WHERE ID=?";
            PreparedStatement pstmt = conn.prepareStatement(remover);
            pstmt.setInt(1, area.getId());

            // executa
            int retorno = pstmt.executeUpdate();

            if (retorno > 0) {
                area.setDataExclusao(LocalDateTime.now());
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

    // Fazendo uma busca geral
    public List<AreaModel> buscar() {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        ResultSet rset = null;
        List<AreaModel> listaBusca = new ArrayList<>();
        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return listaBusca;
        }

        // prepara e executa
        try {
            Statement stmt = conn.createStatement();
            rset = stmt.executeQuery("SELECT * FROM AREAS");

            // percorre resultados
            while (rset.next()) {
                int id = rset.getInt("id");
                String nome = rset.getString("nome");
                String descricao = rset.getString("descricao");

                // verifica se dataExclusao é null
                LocalDateTime dataExclusao = null;
                if (rset.getTimestamp("dataExclusao") != null) {
                    dataExclusao = rset.getTimestamp("dataExclusao").toLocalDateTime();
                }

                LocalDateTime dataAtualizacao = rset.getTimestamp("dataAtualizacao").toLocalDateTime();
                LocalDateTime dataCriacao = rset.getTimestamp("dataCriacao").toLocalDateTime();

                AreaModel area1 = new AreaModel(id, nome, descricao, dataExclusao, dataAtualizacao, dataCriacao);
                listaBusca.add(area1);
            }

        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        } finally {
            conexao.desconectar();
            // desconecta
        }
        return listaBusca;
    }

    // Buscando apenas pelo nome
    public List<AreaModel> buscarPorNome(AreaModel area) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        ResultSet rset;
        List<AreaModel> listaBusca = new ArrayList<>();
        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return listaBusca;
        }

        // prepara e executa
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT * FROM AREAS WHERE NOME = ? ORDER BY 1"
            );
            pstmt.setString(1, area.getNome());
            rset = pstmt.executeQuery();

            // percorre resultados
            while (rset.next()) {
                int id = rset.getInt("id");
                String nome = rset.getString("nome");
                String descricao = rset.getString("descricao");

                // verifica se dataExclusao é null
                LocalDateTime dataExclusao = null;
                if (rset.getTimestamp("dataExclusao") != null) {
                    dataExclusao = rset.getTimestamp("dataExclusao").toLocalDateTime();
                }

                LocalDateTime dataAtualizacao = rset.getTimestamp("dataAtualizacao").toLocalDateTime();
                LocalDateTime dataCriacao = rset.getTimestamp("dataCriacao").toLocalDateTime();

                AreaModel area1 = new AreaModel(id, nome, descricao, dataExclusao, dataAtualizacao, dataCriacao);
                listaBusca.add(area1);
            }

        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        } finally {
            conexao.desconectar();
            // desconecta
        }
        return listaBusca;
    }
}
