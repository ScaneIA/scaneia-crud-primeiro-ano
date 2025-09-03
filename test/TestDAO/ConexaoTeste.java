package TestDAO;

import DAO.Conexao;
import DAO.UserDAO;


public class ConexaoTeste {
    public static void main(String[] args) {
        Conexao conexao = new Conexao();
        if (conexao.getConnection() == null){
            System.out.println("Não foi possivel conectar");
        }else {
            System.out.println("Conectado com sucesso");
        }


        UserDAO test= new UserDAO();

        if(test.insert()){
            System.out.println("Inserção realizada");
        }
        else{
            System.out.println("Não foi possível inserir");
        }
        conexao.desconectar();

        if(test.update()){
            System.out.println("Atualização realizada");
        }
        else{
            System.out.println("Não foi possível atualizar");
        }
        conexao.desconectar();

//        if(test.delete()){
//            System.out.println("Deletado");
//        }
//        else{
//            System.out.println("Não foi possível detelar");
//        }
//        conexao.desconectar();
    }
}
