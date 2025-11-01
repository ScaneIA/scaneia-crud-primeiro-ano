package com.scaneia.ScaneiaServlet.DAO;

import com.scaneia.ScaneiaServlet.Model.CargoModel;
import com.scaneia.ScaneiaServlet.conexao.Conexao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CargoDAO {

    // Insert normal, inserindo todos os dados
    public int inserir(CargoModel cargo) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        // cria conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            // erro na conexão
            return -1;
        }
        // prepara
        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO CARGOS (NOME, DESCRICAO) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, cargo.getNome());
            pstmt.setString(2, cargo.getDescricao());
            // executa
            int retorno = pstmt.executeUpdate();

            // pega o ID gerado
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cargo.setId(rs.getInt(1));
                }
            }

            if (retorno > 0) {
                cargo.setDataCriacao(LocalDateTime.now());
                cargo.setDataAtualizacao(LocalDateTime.now());
                // colocando esses valores no objeto
                // deu certo
                return 1;
            } else {
                // nada foi alterado
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

    // Inserindo apenas o nome
    public int inserirNome(CargoModel cargo) {
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
                "INSERT INTO CARGOS (NOME) VALUES (?)",
                Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, cargo.getNome());

            // executa
            int retorno = pstmt.executeUpdate();

            // pega o ID gerado
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cargo.setId(rs.getInt(1));
                }
            }

            if (retorno > 0) {
                cargo.setDataCriacao(LocalDateTime.now());
                cargo.setDataAtualizacao(LocalDateTime.now());
                // colocando esses valores no objeto
                // deu certo
                return 1;
            } else {
                // nada foi alterado
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

    // Atualizando tudo
    public int atualizar(CargoModel cargo) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            // erro na conexão
            return -1;
        }

        // prepara o comando
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE CARGOS SET NOME=?, DESCRICAO=?, DATAATUALIZACAO=NOW() WHERE ID=?"
            );
            pstmt.setString(1, cargo.getNome());
            pstmt.setString(2, cargo.getDescricao());
            pstmt.setInt(3, cargo.getId());

            // executa
            int linhasAfetadas = pstmt.executeUpdate();

            if (linhasAfetadas > 0) {
                cargo.setDataAtualizacao(LocalDateTime.now());
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

    // Atualizando só nome
    public int atualizarNome(CargoModel cargo) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        // cria conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            // erro na conexão
            return -1;
        }
        // prepara a conexão
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE CARGOS SET NOME=?, DATAATUALIZACAO=NOW() WHERE ID=?"
            );
            pstmt.setString(1, cargo.getNome());
            pstmt.setInt(2, cargo.getId());

            // executa
            int retorno = pstmt.executeUpdate();

            if (retorno > 0) {
                cargo.setDataAtualizacao(LocalDateTime.now());
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

    // Atualizando só a descriação
    public int atualizarDescricao(CargoModel cargo) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        // cria conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            // erro na conexão
            return -1;
        }
        // prepara
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE CARGOS SET DESCRICAO=?, DATAATUALIZACAO=NOW() WHERE ID=?"
            );
            pstmt.setString(1, cargo.getDescricao());
            pstmt.setInt(2, cargo.getId());

            // executa
            int retorno = pstmt.executeUpdate();

            if (retorno > 0) {
                cargo.setDataAtualizacao(LocalDateTime.now());
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

    // deletando
    public int deletar(CargoModel cargo) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        // cria conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            // erro na conexão
            return -1;
        }

        // prepara
        try {
            String remover = "UPDATE CARGOS SET DATAEXCLUSAO = NOW() WHERE ID=?";
            PreparedStatement pstmt = conn.prepareStatement(remover);
            pstmt.setInt(1, cargo.getId());

            // executa
            int retorno = pstmt.executeUpdate();

            if (retorno > 0) {
                cargo.setDataExclusao(LocalDateTime.now());
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

    // Fazendo uma busca geral
    public List<CargoModel> buscar() {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        ResultSet rset = null;
        List<CargoModel> listaBusca = new ArrayList<>();

        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return listaBusca;
        }

        try {
            Statement stmt = conn.createStatement();
            rset = stmt.executeQuery("SELECT * FROM CARGOS");

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

                CargoModel cargo1 = new CargoModel(id, nome, descricao, dataExclusao, dataAtualizacao, dataCriacao);
                listaBusca.add(cargo1);
            }

        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        } finally {
            conexao.desconectar();
            // desconecta
        }
        return listaBusca;
        // retorna lista
    }
    // Busca só pelo nome
    public List<CargoModel> buscarPorNome(CargoModel cargo) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        ResultSet rset;
        List<CargoModel> listaBusca = new ArrayList<>();
        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return listaBusca;
        }
        // prepara
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT * FROM CARGOS WHERE NOME = ? ORDER BY 1"
            );
            pstmt.setString(1, cargo.getNome());
            rset = pstmt.executeQuery();

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

                CargoModel cargo1 = new CargoModel(id, nome, descricao, dataExclusao, dataAtualizacao, dataCriacao);
                listaBusca.add(cargo1);
            }

        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        } finally {
            conexao.desconectar();
            // desconecta
        }
        return listaBusca;
        // lista
    }
}