package com.scaneia.ScaneiaServlet.DAO;

import com.scaneia.ScaneiaServlet.Model.UsuarioViewModel;
import com.scaneia.ScaneiaServlet.conexao.Conexao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UsuarioViewDAO {
    public List<UsuarioViewModel> buscarTodos(){
        //variaveis gerais
        List<UsuarioViewModel> usuarios = new ArrayList<>();

        //cria a conexao
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        if (conn == null){
            return null;
        }

        //cria o script sql
        try{
            //realiza a consulta
            String sql = "SELECT * FROM USUARIOS_SETORES_CARGO";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            //itera sob o resultado
            while (rs.next()){
                String nome = rs.getString("nome_usuario");
                String email = rs.getString("email");
                String cpf = rs.getString("cpf_usuario");
                String setor = rs.getString("setor");
                String cargo = rs.getString("cargo");
                String urlFoto = rs.getString("url_foto_usuario");
                String registro = rs.getString("registro_usuario"); //ainda vai adicionar no banco

                usuarios.add(new UsuarioViewModel(
                        nome, email, cpf, setor, cargo, urlFoto, formatarHora(registro)
                ));
            }

            return usuarios;
        }catch (SQLException sqle){
            return null;
        }finally {
            conexao.desconectar();
        }
    }

    public String formatarHora(String hora){
        //pega a hora no modelo ceto
        DateTimeFormatter formatterEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSX");
        OffsetDateTime horaNormal = OffsetDateTime.parse(hora, formatterEntrada);

        //cria o formatter da saida
        DateTimeFormatter formatterSaida = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return horaNormal.format(formatterSaida);
    }
}
