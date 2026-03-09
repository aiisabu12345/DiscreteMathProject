import java.util.Scanner;

public class MenuExpression{
    public void start(){                                                                    //method นี้ใช้แสดง menu เพื่อเลือกใช้ methodใน InfixTranfromer
        Scanner sc = new Scanner(System.in);                                                //ตามความต้องการของUser
         InfixTranfromer t = new InfixTranfromer();
        String input;
        while (true) {
            System.out
                    .println("Enter your choice:\n1.find prefix\n2.find postfix\n3.find Alu\nPress x to exit program");
            char op = sc.next().charAt(0);
            switch (op) {
                case '1':
                    System.out.println("Enter infix:");
                    input = sc.next();
                    System.out.println("Prefix: " + t.findInToPre(input));
                    break;
                case '2':
                    System.out.println("Enter infix:");
                    input = sc.next();
                    System.out.println("Postfix: " + t.findInToPost(input));
                    break;
                case '3':
                    System.out.println("Enter infix:");
                    input = sc.next();
                    System.out.println("Result With Alu: " + t.callLikeAlu(input));
                    break;
                case 'x':
                default:
                    sc.close();
                    System.exit(0);
            }
        }
    }
}