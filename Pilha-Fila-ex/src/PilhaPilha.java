import java.util.Stack;
import java.util.Scanner;


public class PilhaPilha {
    public static void main(String[] args) {
        Scanner input= new Scanner(System.in);
        System.out.println("Digite a expressão matemática: ");
        String ex= input.next();
        System.out.println("O resultado é: " + expressao(ex));
    }
    public static int expressao(String ex){
        Stack<Character> pilha= new Stack<>();
        Stack<Integer> num= new Stack<>();

        for(int i=0; i<ex.length();i++){
            char c =ex.charAt(i);
            if(c=='*' || c== '+'){
                if(c=='*'){
                    int a=num.pop();
                    int b= Character.getNumericValue(ex.charAt(++i));
                    num.push(a*b);
                }
                else{
                    pilha.push(c);
                }

            }
            else{
                num.push(Character.getNumericValue(c));
            }
        }
        int result= 0;
        while(!pilha.isEmpty() && !num.isEmpty()){
            result+=num.pop();
        }
        return result;

    }
}
