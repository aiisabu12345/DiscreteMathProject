public class test1 {
    public static void main(String[] args) {
        GraphChecker checker = new GraphChecker();

        // 1. ตั้งค่า Vertex ทั้งหมด (A ถึง J)
        checker.setAllVertex("A B C D E F G H I J");

        // 2. ตั้งค่าชื่อ Edge ทั้งหมด (มี 21 เส้นเชื่อมตามรูป)
        String edgeNames = "e1 e2 e3 e4 e5 e6 e7 e8 e9 e10 e11 e12 e13 e14 e15 e16 e17 e18 e19 e20 e21";
        checker.setAllEdge(edgeNames);

        // 3. ตั้งค่าการเชื่อมต่อ (Transition) พร้อมระบุน้ำหนัก (Weight)
        // รูปแบบ: "ชื่อEdge,Vertex1,Vertex2,Weight"
        StringBuilder sb = new StringBuilder();
        
        // เส้นเชื่อมจาก Vertex A
        sb.append("e1,A,B,3 ");
        sb.append("e2,A,D,6 ");
        sb.append("e3,A,E,9 ");
        
        // เส้นเชื่อมจาก Vertex B
        sb.append("e4,B,C,2 ");
        sb.append("e5,B,D,4 ");
        sb.append("e6,B,E,9 ");
        sb.append("e7,B,F,9 ");
        
        // เส้นเชื่อมจาก Vertex C
        sb.append("e8,C,D,2 ");
        sb.append("e9,C,F,8 ");
        sb.append("e10,C,G,9 ");
        
        // เส้นเชื่อมจาก Vertex D
        sb.append("e11,D,G,9 ");
        
        // เส้นเชื่อมจาก Vertex E
        sb.append("e12,E,F,8 ");
        sb.append("e13,E,J,18 ");
        
        // เส้นเชื่อมจาก Vertex F
        sb.append("e14,F,G,7 ");
        sb.append("e15,F,I,9 ");
        sb.append("e16,F,J,10 ");
        
        // เส้นเชื่อมจาก Vertex G
        sb.append("e17,G,H,4 ");
        sb.append("e18,G,I,5 ");
        
        // เส้นเชื่อมจาก Vertex H
        sb.append("e19,H,I,1 ");
        sb.append("e20,H,J,4 ");
        
        // เส้นเชื่อมจาก Vertex I
        sb.append("e21,I,J,3");

        checker.setTransitionFunc(sb.toString());

        // --- รันการทดสอบผลลัพธ์ ---

        System.out.println("### การตรวจสอบความเชื่อมต่อของกราฟ ###");
        System.out.println("Is graph connected? : " + checker.CheckContinue());

        System.out.println("\n### Adjacency Matrix (n=1) ###");
        checker.setAdjMetrix();
        checker.findAdjMatrixN(1);

        System.out.println("\n### การหา Minimum Spanning Tree (Kruskal) ###");
        // Kruskal จะเริ่มเลือกจาก e19(1), e4(2), e8(2), e1(3), e21(3) ... เป็นต้น
        checker.kruskal();
    }
}