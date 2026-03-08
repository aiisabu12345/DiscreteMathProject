import java.util.Scanner;

public class Front {
    public void start() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println(
                    "Enter your choice:\n1.find prefix,postfix,alu\n2.find minimum spanning tree\nPress x to exit program");
            char op = sc.next().charAt(0);
            switch (op) {
                case '1':
                    callInfixTransfromer(sc);
                    break;
                case '2':
                    callGraphChecker(sc);
                    break;
                case 'x':
                default:
                    sc.close();
                    System.exit(0);
            }
        }
    }

    public void callInfixTransfromer(Scanner sc) {
        InfixTranfromer t = new InfixTranfromer();
        String input;
        while (true) {
            System.out
                    .println("Enter your choice:\n1.find prefix\n2.find postfix\n3.find Alu\nPress x to back to menu");
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
                    System.out.println("Sum With Alu: " + t.callLikeAlu(input));
                    break;
                case 'x':
                    start();
                    break;
            }
        }
    }

    public void callGraphChecker(Scanner sc) {
        GraphChecker g = new GraphChecker();

        String input;
        sc.nextLine();

        System.out.println("Enter Vertex:");
        input = sc.nextLine();
        g.setAllVertex(input);
        System.out.println("Enter Edge:");
        input = sc.nextLine();
        g.setAllEdge(input);
        System.out.println("Enter Transition Function:");
        input = sc.nextLine();
        g.setTransitionFunc(input);
        if (!g.CheckContinue()) {
            System.out.println("Graph is not continue");
            start();
            return;
        }
        while (true) {
            System.out.println(
                    "Enter your choice:\n1.find minimum spanning tree\n2.prim\n3.kruskal\nPress x to back to menu");
            char op = sc.next().charAt(0);
            switch (op) {
                case '1':
                    g.spanningTree();
                    break;
                case '2':
                    System.out.println("Enter root vertex:");
                    String root = sc.nextLine();
                    g.prim(root);
                    break;
                case '3':
                    g.kruskal();
                    break;
                case 'x':
                    start();
                    break;
            }
        }
    }
}