package com.scaneia.ScaneiaServlet.DAO;

import com.scaneia.ScaneiaServlet.Model.UsuarioModel;
import com.scaneia.ScaneiaServlet.conexao.Conexao;

import java.sql.*;
import java.time.LocalDateTime;

public class UsuarioDAO {

    // Cadastrar usuário
    public int insert(UsuarioModel usuario) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return -1; // erro na conexão
        }

        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO USUARIOS (NOME, EMAIL, CPF, IDCARGOS, IDEMPRESAS) VALUES (?, ?, ?, ?, ?)")) {

            // adiciona os parâmetros
            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getCpf());
            pstmt.setInt(4, usuario.getIdCargo());
            pstmt.setInt(5, usuario.getIdEmpresa());

            // executa
            int retorno = pstmt.executeUpdate();

            if (retorno > 0) {
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

    // Atualizar nome
    public int updateNome(UsuarioModel usuario) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return -1; // erro na conexão
        }

        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE USUARIOS SET NOME = ?, DATAATUALIZACAO = NOW() WHERE ID = ?"
            );
            pstmt.setString(1, usuario.getNome());
            pstmt.setInt(2, usuario.getId());

            // executa
            int retorno = pstmt.executeUpdate();

            if (retorno > 0) {
                usuario.setDataAtualizacao(LocalDateTime.now());
                // atualiza data no objeto
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

    // Atualizar ID do cargo
    public int updateIdCargo(int idCargo, int idUsuario) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return -1; // erro na conexão
        }

        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE USUARIOS SET IDCARGOS = ?, DATAATUALIZACAO = NOW() WHERE ID = ?"
            );
            pstmt.setInt(1, idCargo);
            pstmt.setInt(2, idUsuario);

            // executa
            int retorno = pstmt.executeUpdate();

            if (retorno > 0) {
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
            conexao.desconectar();// desconecta
        }
    }

    public int alterarCpf(String novoCpf, int idUsuario){
        //cria conexao
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        if (conn == null){
            return -1;
        }

        //faz o comando sql
        try {
            //prepara o script
            String sql = "UPDATE USUARIOS SET CPF = ?, DATAATUALIZACAO = NOW() WHERE ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //atualiza os parametro
            pstmt.setString(1, novoCpf);
            pstmt.setInt(2, idUsuario);

            //manda a resposta
            int resultado = pstmt.executeUpdate();

            if (resultado > 0){
                return 1;
            }
            return 0;

        }catch (SQLException exception){
            return -2;
        }catch (Exception exception){
            return -3;
        }
    }

    // Adicionar setor ao usuário
    public int adicionarSetorUsuario(UsuarioModel usuario, int setorId) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return -1; // erro na conexão
        }

        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO SETORES_USUARIOS (IDUSUARIOS, IDSETORES) VALUES (?, ?)"
        )) {
            pstmt.setInt(1, usuario.getId());
            pstmt.setInt(2, setorId);

            // executa
            int retorno = pstmt.executeUpdate();

            if (retorno > 0) {
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

    public int alterarIdSetor(int novoId, int idUsuario){
        //cria a conexao
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        if (conn == null){
            return -1;
        }

        //faz o script
        try {
            //prepara o sql
            String sql = "UPDATE SETORES_USUARIOS SET IDSETORES = ?, DATAATUALIZACAO = NOW() WHERE IDUSUARIOS = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //atualiza os parametros
            pstmt.setInt(1, novoId);
            pstmt.setInt(2, idUsuario);

            //realiza a requisição
            int afetadas = pstmt.executeUpdate();

            if (afetadas > 0){
                return 1;
            }else{
                return 0;
            }

        }catch (SQLException exception){
            return -2;
        }catch (Exception exception){
            return -3;
        }finally {
            conexao.desconectar();
        }
    }

    // Deletar usuário
    public int delete(int idUsuario) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        // cria a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            return -1; // erro na conexão
        }

        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE USUARIOS SET DATAEXCLUSAO = NOW(), DATAATUALIZACAO = NOW() WHERE ID = ?"
            );
            pstmt.setInt(1, idUsuario);

            // executa
            int retorno = pstmt.executeUpdate();

            if (retorno > 0) {
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

    public int alterarEmail(String email, int idUsuario){
        //cria a conexao
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        if (conn == null){
            return -1;
        }

        //faz o script
        try {
            //prepara o sql
            String sql = "UPDATE USUARIOS SET EMAIL = ?, DATAATUALIZACAO = NOW() WHERE ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //atualiza os parametros
            pstmt.setString(1, email);
            pstmt.setInt(2, idUsuario);

            //realiza a requisição
            int afetadas = pstmt.executeUpdate();

            if (afetadas > 0){
                return 1;
            }else{
                return 0;
            }

        }catch (SQLException exception){
            return -2;
        }catch (Exception exception){
            return -3;
        }finally {
            conexao.desconectar();
        }
    }
}
