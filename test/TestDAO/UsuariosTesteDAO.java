package TestDAO;

import DAO.UsuariosDAO;
import Model.Usuarios;

public class UsuariosTesteDAO {
    public static void main(String[] args) {
        //variaveis
        UsuariosDAO usuariosDAO = new UsuariosDAO();
        Usuarios usuario = new Usuarios("Gustavo Costa", "Gustavo.costa@gmail.com", "34331294321", "teste", 4);
        usuario.setId(17);

        //cadastra o usuario
        System.out.print("Cadastrando usuario: ");
        if (usuariosDAO.cadastrar(usuario)){
            System.out.print("Feito Com sucesso\n");
        }else {
            System.out.print("Erro no processo\n");
        }

        //troca o nome do usuario
        System.out.print("Alterando nome do usuario: ");
        if (usuariosDAO.atualizarNome(usuario, "Eduardo Costa")){
            System.out.print("Feito com sucesso\n");
        }else {
            System.out.print("Erro no processo\n");
        }

        //troca o id cargo
        System.out.print("Alterando o id cargo: ");
        if (usuariosDAO.atualizarIdCargo(usuario, 2)){
            System.out.print("Feito com sucesso\n");
        }else{
            System.out.println("Erro no processo\n");
        }

        //
    }
}
