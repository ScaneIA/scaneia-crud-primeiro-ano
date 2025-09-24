package DAO;

import Model.Usuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public boolean delete(){
        Conexao conexao= new Conexao();
        Connection conn= conexao.getConnection();
        if(conn==null){
            System.out.println("Não foi possível conectar");
            return false;
        }
        else{
            try{
                String remover="DELETE FROM USUARIO WHERE CPF=?";
                PreparedStatement pstmt= conn.prepareStatement(remover);
                pstmt.setInt(1,569874532);
                pstmt.execute();
            }
            catch (SQLException se){
                se.printStackTrace();
                return false;
            }
            finally {
                conexao.desconectar();
            }
            return true;
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
}
