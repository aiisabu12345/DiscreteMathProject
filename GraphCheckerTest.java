
public class GraphCheckerTest {

    private GraphChecker graphChecker;

    public static void main(String[] args) {
        GraphCheckerTest g = new GraphCheckerTest();

        System.out.println("--- Simple Prim Test ---");
        g.setUp();
        g.testPrim();

        System.out.println("\n--- Complex Prim Test (from Image) ---");
        g.setUp();
        g.testPrimComplex();
    }

    public void setUp() {
        graphChecker = new GraphChecker();
    }

    public void testPrim() {
        // Simple case
        graphChecker.setAllVertex("A B C D");
        graphChecker.setAllEdge("e1 e2 e3 e4 e5");
        graphChecker.setTransitionFunc("e1,A,B,1 e2,A,C,4 e3,B,C,2 e4,B,D,5 e5,C,D,3");
        graphChecker.prim("A");
    }

    public void testPrimComplex() {
        // Complex case from image
        graphChecker.setAllVertex("0 1 2 3 4 5 6 7 8");
        graphChecker.setAllEdge("e01 e02 e12 e13 e14 e23 e26 e34 e35 e36 e37 e38 e45 e57 e68 e78");

        // Edge, Vertex1, Vertex2, Weight
        graphChecker.setTransitionFunc("e01,0,1,8 e02,0,2,12 e12,1,2,13 e13,1,3,25 e14,1,4,9 " +
                "e23,2,3,14 e26,2,6,21 e34,3,4,20 e35,3,5,8 " +
                "e36,3,6,12 e37,3,7,12 e38,3,8,16 e45,4,5,19 " +
                "e57,5,7,11 e68,6,8,11 e78,7,8,9");

        graphChecker.prim("0");
    }
}
