public class test1 {
    public static void main(String[] args) {
        GraphChecker checker = new GraphChecker();

        // 1. ตั้งค่า Vertex (1 ถึง 9)
        checker.setAllVertex("1 2 3 4 5 6 7 8 9");

        // 2. ตั้งค่าชื่อ Edge ทั้งหมด (มี 14 เส้นเชื่อม)
        checker.setAllEdge("e1 e2 e3 e4 e5 e6 e7 e8 e9 e10 e11 e12 e13 e14");

        // 3. กำหนดความสัมพันธ์ (Transition) ตามรูปภาพใหม่
        // รูปแบบ: "ชื่อEdge,Vertex1,Vertex2,Weight"
        StringBuilder sb = new StringBuilder();

        // แถวบนและเส้นเชื่อมจากโหนด 1, 2
        sb.append("e1,1,2,10 ");
        sb.append("e2,1,3,9 ");
        sb.append("e3,1,4,6 ");
        sb.append("e4,2,5,8 ");
        sb.append("e5,1,5,12 "); // เส้นทแยง 1-5

        // เส้นเชื่อมจากโหนด 3, 4
        sb.append("e6,3,4,7 ");
        sb.append("e7,3,6,5 ");
        sb.append("e8,4,5,8 ");
        sb.append("e9,4,7,7 ");
        sb.append("e10,4,6,8 ");

        // เส้นเชื่อมจากโหนด 5, 6, 7, 8, 9
        sb.append("e11,5,7,4 ");
        sb.append("e12,6,7,14 ");
        sb.append("e13,6,8,6 ");
        sb.append("e14,7,8,8 ");
        sb.append("e15,7,9,8 ");
        sb.append("e16,8,9,10 ");
        sb.append("e17,5,9,13"); // เส้นเชื่อมขวาสุด 5-9

        // อัปเดตจำนวน Edge ให้ครอบคลุม e1-e17
        checker.setAllEdge("e1 e2 e3 e4 e5 e6 e7 e8 e9 e10 e11 e12 e13 e14 e15 e16 e17");
        checker.setTransitionFunc(sb.toString());

        // --- รันการทดสอบ ---

        System.out.println("=== 1. Connectivity Check ===");
        System.out.println("Is connected: " + checker.CheckContinue());

        System.out.println("\n=== 2. Adjacency Matrix (Path length 1) ===");

        System.out.println("\n=== 3. Kruskal's MST Result ===");
        // จากรูปเฉลย Total Weight ต้องได้ 51
        checker.kruskal();
        System.out.println("\n### การหา Minimum Spanning Tree (Kruskal) ###");
        // Kruskal จะเริ่มเลือกจาก e19(1), e4(2), e8(2), e1(3), e21(3) ... เป็นต้น
        checker.kruskal();
        System.out.println("\n### การหา Minimum Spanning Tree (Prim) ###");
        checker.prim("1");
    }
}