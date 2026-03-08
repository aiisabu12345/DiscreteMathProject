class Main {
    public static void main(String[] args) {
        // Front f = new Front();
        // f.start();
        testPrim();
    }

    static void testPrim() {
        GraphCheckerTest g = new GraphCheckerTest();
        g.setUp();
        g.testPrim();
    }
}