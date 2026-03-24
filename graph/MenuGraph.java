import java.util.Scanner;

public class MenuGraph{
    public void start(){                                                    //method นี้ใช้แสดง menu เพื่อเลือกใช้ methodใน GraphChecker
        Scanner sc = new Scanner(System.in);                                //ตามความต้องการของUser
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
                    g.findAllSpanningTrees();
                    break;
                case '2':
                    System.out.println("Enter root vertex:");
                    sc.nextLine();
                    String primRoot = sc.nextLine();
                    g.prim(primRoot);
                    break;
                case '3':
                    g.kruskal();
                    break;
                case '4':
                    System.out.println("Enter root vertex:");
                    sc.nextLine();
                    String dijkRoot = sc.nextLine();
                    g.dijkstra(dijkRoot);
                case 'x':
                default:
                    sc.close();
                    System.exit(0);
            }
        }
    }
}