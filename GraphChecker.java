
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class GraphChecker {

    private List<Vertex> allVertex;
    private List<Edge> allEdge;
    private String[] route;
    private Queue<Edge> graph;

    private int[][] adjMatrix;

    public GraphChecker() {
        allVertex = new ArrayList<>();
        allEdge = new ArrayList<>();
        graph = new PriorityQueue<>();
    }

    public List<Vertex> getAllVertex() {
        return allVertex;
    }

    public List<Edge> getAllEdge() {
        return allEdge;
    }

    public void setAllVertex(String data) {
        for (String s : data.split(" ")) {
            allVertex.add(new Vertex(s));
        }
    }

    public void setAllEdge(String data) {
        for (String s : data.split(" ")) {
            allEdge.add(new Edge(s));
        }
    }

    public void setTransitionFunc(String data) {
        for (String s : data.split(" ")) {
            String[] s2 = s.split(",");

            Edge e = findEdgeByName(s2[0]);
            Vertex v1 = findVertexByName(s2[1]);
            Vertex v2 = findVertexByName(s2[2]);
            int weight = Integer.parseInt(s2[3]);

            v1.getAllEdge().add(e);
            v2.getAllEdge().add(e);
            e.addVertex(v1);
            e.addVertex(v2);
            e.setWeight(weight);
            graph.add(e);
        }
    }

    public Edge findEdgeByName(String name) {
        for (Edge e : allEdge) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }

    public Vertex findVertexByName(String name) {
        for (Vertex v : allVertex) {
            if (v.getName().equals(name)) {
                return v;
            }
        }
        return null;
    }

    public void setAdjMetrix() {
        adjMatrix = new int[allVertex.size()][allVertex.size()];
        for (int i = 0; i < allVertex.size(); i++) {
            for (int j = i; j < allVertex.size(); j++) {
                int count = 0;
                Vertex v1 = allVertex.get(i);
                Vertex v2 = allVertex.get(j);
                Set<Edge> e1 = v1.getAllEdge();
                for (Edge e : e1) {
                    Set<Vertex> setV = e.getAllVertex();
                    if (i == j) {
                        if (setV.size() == 1) {
                            count++;
                        }
                    } else {
                        if (setV.contains(v2)) {
                            count++;
                        }
                    }
                }
                adjMatrix[i][j] = count;
                adjMatrix[j][i] = count;
            }
        }

    }

    public void findAdjMatrixN(int n) {
        int[][] newAdjMatrix = adjMatrix.clone();
        int[][] temAdjMatrix;

        while (--n > 0) {
            temAdjMatrix = new int[newAdjMatrix.length][newAdjMatrix[0].length];
            for (int i = 0; i < newAdjMatrix.length; i++) {
                for (int j = i; j < newAdjMatrix[0].length; j++) {
                    int count = 0;
                    for (int k = 0; k < newAdjMatrix[0].length; k++) {
                        count += newAdjMatrix[i][k] * adjMatrix[k][j];
                    }
                    temAdjMatrix[i][j] = count;
                    temAdjMatrix[j][i] = count;
                }
            }
            newAdjMatrix = temAdjMatrix.clone();
        }

        for (int i = 0; i < newAdjMatrix.length; i++) {
            for (int j = 0; j < newAdjMatrix[0].length; j++) {
                System.out.print(newAdjMatrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean CheckContinue() {
        Set<Vertex> passedVertex = new HashSet<>();
        Set<Edge> passEdges = new HashSet<>();
        Queue<Edge> temGraph = new PriorityQueue<>(graph);
        temGraph.add(graph.peek());
        while (!temGraph.isEmpty()) {
            Edge cur = temGraph.poll();
            cur.getAllVertex()
                    .forEach(v -> {
                        passedVertex.add(v);
                        v.getAllEdge()
                                .forEach(e -> {
                                    if (!passEdges.contains(e)) {
                                        temGraph.add(e);
                                        passEdges.add(e);
                                    }
                                });
                    });
        }
        return passedVertex.size() == allVertex.size();
    }

    public void spanningTree() { // เช็ค spanning tree
        if (allEdge.size() != allVertex.size() - 1) { // ถ้า edge ไม่เท่ากับ vertex-1 แสดงว่าไม่ใช่ spanning tree
            System.out.println("Graph is NOT a Spanning Tree");
            return;
        }

        Set<Vertex> visited = new HashSet<>(); // เก็บ vertex ตัวที่ไปถึงแล้ว

        Queue<Vertex> queue = new LinkedList<>(); // เก็บ vertex ที่จะไปตรวจต่อ

        Vertex start = allVertex.get(0); // เลือกจุดเริ่ม เริ่มจาก vertex ตัวแรก แล้วเพิ่มใน visited กับ queue
        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()) { // ทำการลูปจนกว่า queue จะว่าง

            Vertex current = queue.poll(); // ดึง vertex ปัจจุบันออกมา

            for (Edge e : current.getAllEdge()) { // วนดู Edge ทุกเส้นที่เชื่อมกับ current
                for (Vertex v : e.getAllVertex()) { // วนดู Vertex ที่อยู่ใน Edge นั้น

                    if (!visited.contains(v)) { // ถ้า vertex นี้ ยังไม่เคย visited เพิ่มเข้า visited กับ queue
                                                // แล้วไปต่อ
                        visited.add(v);
                        queue.add(v);
                    }
                }
            }
        }

        if (visited.size() == allVertex.size()) { // ตรวจว่าครบทุก vertex ไหม ถ้าเท่ากันแปลว่า graph เชื่อมต่อกันทั้งหมด
                                                  // แสดงว่าเป็น spanning tree
            System.out.println("Graph is a Spanning Tree");
        }

        else { // ถ้าไม่เท่ากัน แปลว่า graph ไม่ connected แสดงว่าไม่เป็น spanning tree
            System.out.println("Graph is NOT a Spanning Tree");
        }
    }

    public void prim() {
        Set<Vertex> passVertexs = new HashSet<>();
        Queue<Edge> mst = new LinkedList<>();
        Queue<Edge> connectedEdges = new PriorityQueue<>();
        Vertex root = allVertex.get(0);
        passVertexs.add(root);
        root.getAllEdge().forEach(connectedEdges::add);
        while (!connectedEdges.isEmpty()) {
            Edge cur = connectedEdges.poll();
            Vertex v2 = getOtherVertex(cur, passVertexs);
            if (v2 == null) {
                continue;
            }
            v2.getAllEdge().forEach(connectedEdges::add);
            passVertexs.add(v2);
            mst.add(cur);
        }

        System.out.println(mst);
        System.out.println(mst.stream()
                .map(Edge::getValue)
                .reduce(0, (a, b) -> a + b));
    }

    public Vertex getOtherVertex(Edge e, Set<Vertex> passVertexs) {
        for (Vertex v : e.getAllVertex()) {
            if (!passVertexs.contains(v)) {
                return v;
            }
        }
        return null;
    }

    public void kruskal() {
        Queue<Edge> pqEdge = new PriorityQueue<>(graph);
        Set<Vertex> passedVertex = new HashSet<>();
        Queue<Edge> mst = new LinkedList<>();

        while (!pqEdge.isEmpty()) {
            Edge cur = pqEdge.poll();
            Set<Vertex> passedVertexs = new HashSet<>();

            if (isCircle(mst, cur)) {
                continue;
            }

            cur.getAllVertex().forEach(passedVertexs::add);
            mst.add(cur);
        }
        System.out.println(mst);
        System.out.println(mst.stream()
                .map(Edge::getValue)
                .reduce(0, (a, b) -> a + b));
    }

    public boolean isCircle(Queue<Edge> oriEdges, Edge edge) {
        Vertex vStart = edge.getAllVertex().iterator().next();
        Set<Edge> temEdges = new HashSet<>(oriEdges);
        temEdges.add(edge);
        Queue<Edge> temQueue = new LinkedList<>();
        Set<Vertex> passedVertex = new HashSet<>();
        Set<Edge> passedEdges = new HashSet<>();

        vStart.getAllEdge().forEach(e -> {
            if (temEdges.contains(e)) {
                temQueue.add(e);
            }
        });
        passedVertex.add(vStart);

        // debug
        if (edge.getValue() == 4) {
            System.out.println("temEdges = " + temEdges);
        }

        while (!temQueue.isEmpty()) {
            Edge cur = temQueue.poll();
            passedEdges.add(cur);

            Vertex v = getOtherVertex(cur, passedVertex);
            if (v != null) {
                passedVertex.add(v);
                v.getAllEdge().forEach(e -> {
                    if (temEdges.contains(e) && !passedEdges.contains(e)) {
                        temQueue.add(e);
                    }
                });
            } else {
                return true;
            }
        }

        return false;
    }
}
