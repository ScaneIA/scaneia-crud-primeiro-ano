import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Scanner;

public class FilaPilha {
    public static void main(String[] args) {
        Scanner input= new Scanner(System.in);
        System.out.println("Digite uma palavra: ");
        String palavra= input.next();
        if(palindromo(palavra)){
            System.out.println("Essa palavra é um palíndromo");
        }
        else{
            System.out.println("Não é um palíndromo");
        }

    }
    public static boolean palindromo(String ex){
        Stack<Character> pilha= new Stack<>();
        Queue<Character> fila= new LinkedList<>();
        for(int i=0; i < ex.length(); i++) {
            char c= ex.charAt(i);
             pilha.push(c);
             fila.offer(c);
        }
        while(!pilha.isEmpty() && !fila.isEmpty()){
             if(pilha.peek().equals(fila.peek())){
                 pilha.pop();
                 fila.poll();
                 return true;
             }
             else{
                 return false;
             }
        }
        if(pilha.isEmpty() && fila.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }
}
