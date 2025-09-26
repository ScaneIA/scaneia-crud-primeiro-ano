package DAO;

import Conexao.Conexao;
import Model.UsuarioModel;

import java.sql.*;

public class UsuarioDAO {
    public boolean cadastrar(UsuarioModel usuario){
        //cria a conexao
        Conexao conexao = new Conexao();

        //prepara o comando sql
        try{
            //script sql
            String sql = "INSERT INTO USUARIOS(NOME, EMAIL, CPF, SENHA, IDCARGOS) VALUES (?, ?, ?, ?, ?)";
            Connection connection = conexao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //adiciona os parametros
            preparedStatement.setString(1, usuario.getNome());
            preparedStatement.setString(2, usuario.getEmail());
            preparedStatement.setString(3, usuario.getCpf());
            preparedStatement.setString(4, usuario.getSenha());
            preparedStatement.setInt(5, usuario.getIdCargo());

            //executa o comando
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException exception){
            exception.printStackTrace();
            return false;
        }finally {
            conexao.desconectar();
        }

    }

    public boolean atualizarNome(UsuarioModel usuario, String nome){
        //cria a conexao
        Conexao conexao = new Conexao();

        //faz o script
        try {
            //prepara o script
            String sql = "UPDATE USUARIOS SET NOME = ? WHERE ID = ?";
            Connection connection = conexao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            //adiciona os parametros
            preparedStatement.setString(1, nome);
            preparedStatement.setInt(2, usuario.getId());

            //muda a data de atualizacao
            atualizarData(connection, usuario);

            //executa o comando
            preparedStatement.executeUpdate();
            return true;

        }catch (SQLException exception){
            exception.printStackTrace();
            return false;
        }finally {
            conexao.desconectar();
        }
    }

    public boolean atualizarIdCargo(UsuarioModel usuario, int idCargo){
        //cria a conexao
        Conexao conexao = new Conexao();

        //faz o script
        try {
            //Prepara o script
            String sql = "UPDATE USUARIOS SET IDCARGOS = ? WHERE ID = ?";
            Connection connection = conexao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //adiciona os parametros
            preparedStatement.setInt(1, idCargo);
            preparedStatement.setInt(2, usuario.getId());

            //muda a data de atualizacao
            atualizarData(connection, usuario);

            //executa o comando
            preparedStatement.executeUpdate();
            return true;

        }catch (SQLException exception){
            exception.printStackTrace();
            return false;
        }finally {
            conexao.desconectar();
        }

    }

    public boolean adicionarSetorUsuario(UsuarioModel usuario, int setorId){
        //cria a conexão
        Conexao conexao = new Conexao();

        //faz o script sql
        try{
            //prepara o comando
            String sql = "INSERT INTO SETORES_USUARIOS(IDUSUARIOS, IDSETOES) VALUES (?, ?)";
            Connection connection = conexao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //adiciona os parametros
            preparedStatement.setInt(1, usuario.getId());
            preparedStatement.setInt(2, setorId);

            //executa o comando
            return preparedStatement.executeUpdate() > 0;
        }catch (SQLException exception){
            exception.printStackTrace();
            return false;
        }
    }

    public boolean login(String email, String senha){
        //cria a conexao
        Conexao conexao = new Conexao();

        //faz o comando sql
        try{
            //prepara o script
            String sql = "SELECT * FROM USUARIOS WHERE EMAIL = ? AND SENHA = ?";
            Connection connection = conexao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //adiciona os parametros
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, senha);

            //executa a consula
            ResultSet resultSet = preparedStatement.executeQuery();

            //confirma que só teve uma busca
            int buscas = 0;
            while (resultSet.next()){
                buscas++;
            }

            return buscas == 1;

        }catch (SQLException exception){
            exception.printStackTrace();
            return false;
        }finally {
            conexao.desconectar();
        }
    }

    public void atualizarData(Connection connection, UsuarioModel usuario) throws SQLException{
        //prepara o script sql
        String sql = "UPDATE SETORES SET DATAATUALIZACAO = NOW() WHERE ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //atualiza os parametros do sql
        preparedStatement.setInt(1, usuario.getId());

        //executa o comando
        preparedStatement.executeUpdate();
    }
}
