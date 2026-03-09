
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

    public GraphChecker() {
        allVertex = new ArrayList<>();
        allEdge = new ArrayList<>();
        graph = new PriorityQueue<>();
    }

    public List<Vertex> getAllVertex() {                                //method นี้ใช้รับ List allVertex
        return allVertex;
    }

    public List<Edge> getAllEdge() {                                    //method นี้ใช้รับ List allEdge
        return allEdge;
    }

    public void setAllVertex(String data) {                             //method นี้ใช้กำหนด List allVertex
        for (String s : data.split(" ")) {
            allVertex.add(new Vertex(s));
        }
    }

    public void setAllEdge(String data) {                               //method นี้ใช้กำหนด List allEdge
        for (String s : data.split(" ")) {
            allEdge.add(new Edge(s));
        }
    }

    public void setTransitionFunc(String data) {                        //method นี้ใช้เชื่อม vertex กับ edge และใส่weightให้edge
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

    public Edge findEdgeByName(String name) {                              //method นื้ใช้หาEdge ในList allEdge
        for (Edge e : allEdge) {                                           //ที่มีname == parameter name
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }

    public Vertex findVertexByName(String name) {                          //method นี้ใช้หาVertex ในList allVertex
        for (Vertex v : allVertex) {                                       //ที่มีname == parameter name
            if (v.getName().equals(name)) {
                return v;
            }
        }
        return null;
    }

    public boolean CheckContinue() {                                        //method นี้ใช้ตรวจสอบว่าgraphนี้เป็นgraphต่อเนื่องหรือไม่
        Set<Vertex> passedVertex = new HashSet<>();                         //ลองไล่ทุกEdge ใน List allEdges
        Set<Edge> passEdges = new HashSet<>();                              //แล้วเก็บVertexที่เคยผ่านในset
        Queue<Edge> temGraph = new PriorityQueue<>(graph);                  //แล้วตรวจสอบว่า set.size() == allEdges.size()หรือไม่
        temGraph.add(graph.peek());                                         //ถ้าใช่จะได้ว่า graphนี้เป็นgraphต่อเนื่อง
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

    public void prim(String rootString) {                                   //method นี้ใช้หา minimum spanning tree โดยใช้prim algorithm
        Set<Vertex> passVertexs = new HashSet<>();                          //โดย root vertex คือ vertex ที่มีname เท่ากับ parameter rootString
        Queue<Edge> mst = new LinkedList<>();                               //method นี้จะทำการเก็บEdge ที่สามารถเดินไปได้ไว้ใน PriorityQueue connectedEdges
        Queue<Edge> connectedEdges = new PriorityQueue<>();                 //โดยเริ่มแรกจะเก็บEdge ที่เชื่อมกับ root vertex ก่อน
        Vertex root = findVertexByName(rootString);                         //เพื่อให้ poll edge ที่มี weight น้อยสุดเสมอ
        passVertexs.add(root);                                              //แล้วตรวจสอบว่าถ้าใช้edgeนี้แล้วจะทำให้ เป็นcircleหรือไม่
        root.getAllEdge().forEach(connectedEdges::add);                     //ด้วยการตรวจว่า ปลายทางนี้เป็น vertex ที่เคยผ่านหรือไม่
        while (!connectedEdges.isEmpty()) {                                 //ถ้าใช่ก็จะไม่ใช้edgeนี้ โดยจะทำจนกว่า connectedEdgesจะว่าง
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
        System.out.println("Weight = "+mst.stream()
                .map(Edge::getValue)
                .reduce(0, (a, b) -> a + b));
    }

    public Vertex getOtherVertex(Edge e, Set<Vertex> passVertexs) {         //method ใช้หาvertexที่ถูกเชื่อมด้วยparameter e
        for (Vertex v : e.getAllVertex()) {                                 //และไม่ได้อยู่ใน parameter passVertexs
            if (!passVertexs.contains(v)) {                                 //ถ้าไม่มี return null
                return v;
            }
        }
        return null;
    }

    public void kruskal() {                                                 //method นี้ใช้หา minimum spanning tree โดยใช้kruskal algorithm
        Queue<Edge> pqEdge = new PriorityQueue<>(graph);                    //โดยเก็บทุกedgeในgraph ไว้ในPriorityQueue pqEdge
        Queue<Edge> mst = new LinkedList<>();                               //เมื่อpollแล้วจะได้edgeที่มีweightน้อยสุดก่อน
                                                                            //เพื่อที่จะไล่ edge จากweightน้อยสุดไปมากสุด ตามkruskal algorithm
        while (!pqEdge.isEmpty()) {                                         //แล้วตรวจสอบว่าถ้าใช้edgeนี้แล้วจะทำให้ เป็นcircleหรือไม่
            Edge cur = pqEdge.poll();                                       //ด้วยการตรวจว่า ปลายทางนี้เป็น vertex ที่เคยผ่านหรือไม่
            Set<Vertex> passedVertexs = new HashSet<>();                    //ถ้าใช่ก็จะไม่ใช้edgeนี้ โดยจะทำจนกว่า pqEdgeจะว่าง

            if (isCircle(mst, cur)) {
                continue;
            }

            cur.getAllVertex().forEach(passedVertexs::add);
            mst.add(cur);
        }
        System.out.println(mst);
        System.out.println("Weight = "+mst.stream()
                .map(Edge::getValue)
                .reduce(0, (a, b) -> a + b));
    }

    public boolean isCircle(Queue<Edge> oriEdges, Edge edge) {              //method นี้ใช้ตรวจสอบว่าถ้าใช้ parameter edge ในspanning tree
        Vertex vStart = edge.getAllVertex().iterator().next();              //จะทำให้เป็นcircleหรือไม่
        Set<Edge> temEdges = new HashSet<>(oriEdges);                       //โดยการไล่เดินตามedgeทั้งหมดที่อยู่ใน temEdges
        temEdges.add(edge);                                                 //โดยก่อนไล่จะเพิ่มparameter edge ใน temEdgesก่อน
        Queue<Edge> temQueue = new LinkedList<>();                          //เพื่อเป็นการจำลองกรณีที่ใช้edgeนี้
        Set<Vertex> passedVertex = new HashSet<>();                         //แล้วถ้าเดินกลับมาvertexที่เคยผ่านจะได้ว่ากรณีนี้เป็นcircle
        Set<Edge> passedEdges = new HashSet<>();

        vStart.getAllEdge().forEach(e -> {
            if (temEdges.contains(e)) {
                temQueue.add(e);
            }
        });
        passedVertex.add(vStart);

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
