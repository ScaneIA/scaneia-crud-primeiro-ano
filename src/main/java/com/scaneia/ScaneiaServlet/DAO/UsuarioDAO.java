package com.scaneia.ScaneiaServlet.DAO;


import com.scaneia.ScaneiaServlet.Model.UsuarioModel;
import com.scaneia.ScaneiaServlet.conexao.Conexao;

import java.sql.*;
import java.time.LocalDateTime;

public class UsuarioDAO {

    // Cadastrar usuário
    public int inserir(UsuarioModel usuario) {
        // cria a conexão
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        // verifica a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            // erro na conexão
            return -1;
        }

        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO USUARIOS (NOME, EMAIL, CPF, IDCARGOS, IDEMPRESAS, URLFOTO) VALUES (?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            // adiciona os parâmetros
            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getCpf());
            pstmt.setInt(4, usuario.getIdCargo());
            pstmt.setInt(5, usuario.getIdEmpresa());
            pstmt.setBytes(6, usuario.getUrlFoto());

            // executa
            int retorno = pstmt.executeUpdate();

            //atualiza o id de acordo com o gerado
            ResultSet rs = pstmt.getGeneratedKeys();

            while (rs.next()){
                int id = rs.getInt("id");
                usuario.setId(id);
            }

            //retorno do metodo

            if (retorno > 0) {
                // deu certo
                return 1;
            } else {
                // nada alterado
                return 0;
            }

        } catch (SQLException se) {
            // erro SQL
            se.printStackTrace();
            return -2;
        } catch (Exception e) {
            // outro erro
            e.printStackTrace();
            return -3;
        } finally {
            conexao.desconectar();
            // desconecta
        }
    }

    // Adicionar setor ao usuário
    public int inserirSetorUsuario(UsuarioModel usuario, int setorId) {
        // cria a conexão
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        // verifica a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            // erro na conexão
            return -1;
        }

        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO SETORES_USUARIOS (IDUSUARIOS, IDSETORES) VALUES (?, ?)"
        )) {
            pstmt.setInt(1, usuario.getId());
            pstmt.setInt(2, setorId);

            // executa
            int retorno = pstmt.executeUpdate();

            if (retorno > 0) {
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

    // Atualizar ID do cargo
    public int alterarIdCargo(int idCargo, int idUsuario) {
        // cria a conexão
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        // verifica a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            // erro na conexão
            return -1;
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

    public int alterarCpf(String novoCpf, int idUsuario){
        //cria conexao
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        // validar a conexão
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
                // deu certo
                return 1;
            }
            // nada alterado
            return 0;

        }catch (SQLException exception){
            // erro de banco
            return -2;
        }catch (Exception exception){
            // outro erro
            return -3;
        }
    }
    public int alterarIdSetor(int novoId, int idUsuario){
        //cria a conexao
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        // validar a conexão
        if (conn == null){
            return -1;
        }

        //faz o script
        try {
            //prepara o sql
            String sql = "UPDATE SETORES_USUARIOS SET IDSETORES = ? WHERE IDUSUARIOS = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //atualiza os parametros
            pstmt.setInt(1, novoId);
            pstmt.setInt(2, idUsuario);

            //realiza a requisição
            int afetadas = pstmt.executeUpdate();

            if (afetadas > 0){
                // deu certo
                return 1;
            }else{
                // nada alterado
                return 0;
            }

        }catch (SQLException exception){
            // erro no banco
            return -2;
        }catch (Exception exception){
            // outro erro
            return -3;
        }finally {
            // desconectar
            conexao.desconectar();
        }
    }

    // Deletar usuário
    public int deletar(int idUsuario) {
        // cria a conexão
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        // verifica a conexão
        if (conn == null) {
            System.out.println("Não foi possível conectar");
            // erro na conexão
            return -1;
        }

        // preparando o comando
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE USUARIOS SET DATAEXCLUSAO = NOW(), DATAATUALIZACAO = NOW() WHERE ID = ?"
            );
            pstmt.setInt(1, idUsuario);

            // executa
            int retorno = pstmt.executeUpdate();

            if (retorno > 0) {
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

    public int alterarEmail(String email, int idUsuario){
        //cria a conexão
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        // verifica a conexão
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
                // deu certo
                return 1;
            }else{
                // nada alterado
                return 0;
            }

        }catch (SQLException exception){
            // erro de banco
            return -2;
        }catch (Exception exception){
            // outro erro
            return -3;
        }finally {
            // desconectar
            conexao.desconectar();
        }
    }

    public int alterarImagem(byte[] imagem, int idUsuario){
        //cria a conexao
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        // verifica a conexão
        if (conn == null){
            return -1;
        }

        //faz o script
        try {
            //prepara o sql
            String sql = "UPDATE USUARIOS SET URLFOTO = ?, DATAATUALIZACAO = NOW() WHERE ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //atualiza os parametros
            pstmt.setBytes(1, imagem);
            pstmt.setInt(2, idUsuario);

            //realiza a requisição
            int afetadas = pstmt.executeUpdate();

            if (afetadas > 0){
                // deu certo
                return 1;
            }else{
                // nada alterado
                return 0;
            }

        }catch (SQLException exception){
            // erro de banco
            return -2;
        }catch (Exception exception){
            // outro erro
            return -3;
        }finally {
            // desconectar
            conexao.desconectar();
        }
    }
}