package TestDAO;

import DAO.Conexao;

public class ConexaoTeste {
    public static void main(String[] args) {
        Conexao conexao = new Conexao();
        if (conexao.getConnection() == null){
            System.out.println("Não foi possivel conectar");
        }else {
            System.out.println("conectado com sucesso");
        }
        TestDAO test= new TestDAO();
        test.inserir();
        conexao.desconectar();
    }
}
