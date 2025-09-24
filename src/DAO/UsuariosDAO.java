package DAO;

import Model.Usuarios;

import java.sql.*;
import java.time.LocalDateTime;

public class UsuariosDAO {
    public boolean cadastrar(Usuarios usuario){
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

    public boolean atualizarNome(Usuarios usuario, String nome){
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

    public boolean atualizarIdCargo(Usuarios usuario, int idCargo){
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

    public boolean login(String email, String senha){
        //variaveis
        int usersFound = 0;

        //Cria uma conexão
        Conexao conexao = new Conexao();
        ResultSet resultSet;

        //try-catch para a consulta
        try {
            //Prepara a consulta
            String sql = "SELECT COUNT(*) FROM USUARIO WHERE email = ? and senha = ?";
            PreparedStatement preparedStatement = conexao.getConnection().prepareStatement(sql);

            //Modifica os parametros
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, senha);

            //Faz a consulta
            resultSet = preparedStatement.executeQuery();

        }catch (SQLException exception){
            System.out.println("Erro ao fazer consulta");
            exception.printStackTrace();
            return false;
        }finally {
            conexao.desconectar();
        }

        //Verifica a quantidade de usuarios encontrados
        try{
            if (resultSet.next()){
                usersFound = resultSet.getInt(1);
            }

        }catch (SQLException exception){
            System.out.println("Erro ao visualizar consulta");
            exception.printStackTrace();
            return false;
        }

        //Retorna se tem apenas 1 usuario encontrado
        return usersFound == 1;
    }

    public void atualizarData(Connection connection, Usuarios usuario)throws SQLException{
        //prepara o script sql
        String sql = "UPDATE USUARIOS SET DATAATUALIZACAO = ? WHERE ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //prepara o timestamp
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        usuario.setDataAtualizacao(timestamp);

        //atualiza os parametros do statement
        preparedStatement.setTimestamp(1, usuario.getDataAtualizacao());
        preparedStatement.setInt(2, usuario.getId());

        //executa o comando
        preparedStatement.executeUpdate();
    }
}
