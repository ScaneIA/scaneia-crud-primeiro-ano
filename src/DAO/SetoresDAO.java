package DAO;

import Model.Setores;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SetoresDAO {

    public boolean inserirSetor(Setores setor){
        //cria a conexão
        Conexao conexao = new Conexao();

        //prepara o comando sql
        try{
            //prepara o sql
            String sql = "INSERT INTO SETORES(DESCRICAO, NOME, IDAREAS) VALUES ?,?,?";
            PreparedStatement preparedStatement = conexao.getConnection().prepareStatement(sql);

            //coloca os parametros
            preparedStatement.setString(1, setor.getDescricao());
            preparedStatement.setString(2, setor.getNome());
            preparedStatement.setInt(3, setor.getIdArea());

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
}
