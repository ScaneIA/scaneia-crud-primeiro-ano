package DAO;

import Model.Setores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SetoresDAO {

    public boolean inserirSetor(Setores setor){
        //cria a conexão
        Conexao conexao = new Conexao();

        //prepara o comando sql
        try{
            //prepara o sql
            String sql = "INSERT INTO SETORES(DESCRICAO, NOME, IDAREAS) VALUES ?,?,?";
            Connection connection = conexao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //coloca os parametros
            preparedStatement.setString(1, setor.getDescricao());
            preparedStatement.setString(2, setor.getNome());
            preparedStatement.setInt(3, setor.getIdArea());

            //atualiza data de modificacao
            atualizarData(connection, setor);

            //executa o comando
            return preparedStatement.executeUpdate() > 0;
        }catch (SQLException exception){
            exception.printStackTrace();
            return false;
        }finally {
            //fecha a conexão
            conexao.desconectar();
        }
    }

    public boolean alterarNome(Setores setor, String nome){
        //cria a conexao
        Conexao conexao = new Conexao();

        //prepara o comando sql
        try {
            //script sql
            String sql = "UPDATE SETORES SET NOME = ? WHERE ID = ?";
            Connection connection = conexao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //coloca os parametros
            preparedStatement.setString(1, nome);
            preparedStatement.setInt(2, setor.getId());

            //atualiza data de modificacao
            atualizarData(connection, setor);

            //executa o comando
            return preparedStatement.executeUpdate() == 1;
        }catch (SQLException exception){
            exception.printStackTrace();
            return false;
        }finally {
            conexao.desconectar();
        }
    }

    public boolean alterarDescricao(Setores setor, String descricao){
        //cria a conexao
        Conexao conexao = new Conexao();

        //prepara o comando sql
        try {
            //cria o prepared statement
            String sql = "UPDATE SETORES SET DESCRICAO = ? WHERE ID = ?";
            Connection connection = conexao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //coloca os parametros
            preparedStatement.setString(1, descricao);
            preparedStatement.setInt(2, setor.getId());

            //atualiza data de modificacao
            atualizarData(connection, setor);

            //executa o comando
            return preparedStatement.executeUpdate() == 1;

        }catch (SQLException exception){
            exception.printStackTrace();
            return false;
        }
    }

    public boolean alterarIdAreas(Setores setor, int idArea){
        //cria a conexao
        Conexao conexao = new Conexao();

        //prepara o comando sql
        try {
            //cria o prepared statement
            String sql = "UPDATE SETORES SET IDAREAS = ? WHERE ID = ?";
            Connection connection = conexao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //coloca os parametros
            preparedStatement.setInt(1, idArea);
            preparedStatement.setInt(2, setor.getId());

            //atualiza data de modificacao
            atualizarData(connection, setor);

            //executa o comando
            return preparedStatement.executeUpdate() == 1;

        }catch (SQLException exception){
            exception.printStackTrace();
            return false;
        }finally {
            conexao.desconectar();
        }
    }

    public void atualizarData(Connection connection, Setores setor) throws SQLException{
        //prepara o script sql
        String sql = "UPDATE SETORES SET DATAATUALIZACAO = ? WHERE ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //prepara o timestamp
        LocalDateTime timestampLocal = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(timestampLocal);
        setor.setDataAtualizacao(timestamp);

        //atualiza os parametros do sql
        preparedStatement.setTimestamp(1, setor.getDataAtualizacao());
        preparedStatement.setInt(2, setor.getId());

        //executa o comando
        preparedStatement.executeUpdate();
    }
}
