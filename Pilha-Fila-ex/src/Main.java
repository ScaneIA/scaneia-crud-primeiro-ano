import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner input= new Scanner(System.in);
        System.out.println("Qual é a expressão");
        String expressao= input.next();
        if(balanceamento(expressao)){
            System.out.println("O balanceamento está correto");
        }
        else{
            System.out.println("O balancemento não está correto");
        }

    }
    public static boolean balanceamento(String ex){
        Stack<Character> pilha= new Stack<>();

        for(int i=0; i<ex.length();i++){
            char c =ex.charAt(i);
            if(c == '(' || c== '{' || c=='['){
                pilha.push(c);
            } else if (c== ')' || c=='}' || c==']'){
                if(pilha.isEmpty()){
                    return false;
                }
                int temp= pilha.peek();
                if((c==')' && temp!= '(') || (c=='}' && temp!= '{') || (c==']' && temp!= '[')){
                    return false;
                }
                else{
                    pilha.pop();
                }

            }
        }
        if(pilha.isEmpty()){
            return true;
        }
        else{
            return false;
        }

    }
}