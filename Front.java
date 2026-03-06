import java.util.Scanner;

public class Front{
    public void start(){
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("Enter your choice:\n1.find prefix,postfix,alu\n2.find minimum spanning tree\nPress x to exit program");
            char op = sc.next().charAt(0);
            switch (op) {
                case '1':
                    callInfixTransfromer(sc);
                    break;
                case '2':

                    break;
                case 'x':
                default:
                    sc.close();
                    System.exit(0);
            }
        }
    }

    public void callInfixTransfromer(Scanner sc){
        InfixTranfromer t = new InfixTranfromer();
        String input;
        while(true){
            System.out.println("Enter your choice:\n1.find prefix\n2.find postfix\n3.find Alu\nPress x to back to menu");
            char op = sc.next().charAt(0);
            switch (op) {
                case '1':
                    System.out.println("Enter infix:");
                    input = sc.next();
                    System.out.println("Prefix: "+t.findInToPre(input));
                    break;
                case '2':
                    System.out.println("Enter infix:");
                    input = sc.next();
                    System.out.println("Postfix: "+t.findInToPost(input));
                    break;
                case '3':
                    System.out.println("Enter infix:");
                    input = sc.next();
                    System.out.println("Sum With Alu: "+t.callLikeAlu(input));
                    break;
                case 'x':
                    start();
                    break;
            }
        }
    }
}