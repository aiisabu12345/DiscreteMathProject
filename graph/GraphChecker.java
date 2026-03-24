
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

    public void findAllSpanningTrees() {                                                //method นี้ใช้หา spanning tree ทั้งหมดของ graph
        int needEdge = allVertex.size() - 1;                                            //โดยใช้วิธี backtracking เพื่อเลือก edge ทีละเส้น
        List<Edge> current = new ArrayList<>();                                         //หลักการคือ spanning tree ต้องมีจำนวน edge = vertex - 1
                                                                                        //ดังนั้นจะสร้างชุดของ edge ที่มีขนาด vertex-1
        backtrackSpanningTree(0, current, needEdge);                             //แล้วส่งไปตรวจสอบว่าเป็น spanning tree จริงหรือไม่
                                                                                        //โดยการเรียก method backtrackSpanningTree()
    }

    private void backtrackSpanningTree(int index, List<Edge> current, int needEdge) {   //method นี้ใช้สร้างชุด edge ทุกแบบที่เป็นไปได้
        if (current.size() == needEdge) {                                               //โดยใช้ backtracking ไล่เลือก edge จาก allEdge
                                                                                        //เมื่อจำนวน edge ใน current เท่ากับ vertex-1
            Queue<Edge> tree = new LinkedList<>(current);                               //จะนำชุด edge นี้ไปตรวจสอบว่าเป็น spanning tree หรือไม่
                                                                                        //โดยตรวจสอบว่า graph connected และไม่มี circle
            if (isConnectedTree(tree)) {
                System.out.println("Spanning Tree: " + tree);
            }
            return;
        }

        if (index >= allEdge.size()) {
            return;
        }

        Edge e = allEdge.get(index);

        Queue<Edge> temp = new LinkedList<>(current);

        if (!isCircle(temp, e)) {                                                       //ตรวจสอบว่าถ้าเพิ่ม edge e เข้าไปในชุด edge ปัจจุบันแล้วจะเกิด circle หรือไม่
            current.add(e);                                                             //ถ้าไม่เกิด circle จึงสามารถเพิ่ม edge นี้ใน spanning tree ได้
            backtrackSpanningTree(index + 1, current, needEdge);                        //เพิ่ม edge e ลงในชุด edge ปัจจุบัน
            current.remove(current.size() - 1);                                         //เรียก recursion เพื่อเลือก edge ถัดไป
        }                                                                               //ลบ edge ที่เพิ่มออก เพื่อลอง edge อื่น

        backtrackSpanningTree(index + 1, current, needEdge);
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

    private boolean isConnectedTree(Queue<Edge> edges) {                    //method นี้ใช้ตรวจสอบว่า edge ที่เลือกมาสามารถสร้าง spanning tree ได้หรือไม่
                                                                            //โดย spanning tree ต้องมีจำนวน edge = vertex - 1
                                                                            //และ graph ต้อง connected คือสามารถเดินไปได้ครบทุก vertex
                                                                            //การตรวจสอบจะใช้ BFS โดยเริ่มจากหนึ่งใน vertex ที่อยู่ใน edge
                                                                            //แล้วไล่เดินตาม edge ที่มีอยู่ใน parameter edges

        if (edges.size() != allVertex.size() - 1) {
            return false;
        }

        Set<Vertex> visited = new HashSet<>();
        Queue<Vertex> q = new LinkedList<>();

        Edge first = edges.peek();
        if (first == null) {
            return false;
        }

        Vertex start = first.getAllVertex().iterator().next();
        visited.add(start);
        q.add(start);

        while (!q.isEmpty()) {

            Vertex v = q.poll();

            for (Edge e : edges) {

                if (e.getAllVertex().contains(v)) {

                    for (Vertex next : e.getAllVertex()) {

                        if (!visited.contains(next)) {
                            visited.add(next);
                            q.add(next);
                        }
                    }
                }
            }
        }

        return visited.size() == allVertex.size();
    }

    public void dijkstra (String root){

        Set<Vertex> visitedVertex = new HashSet<>();
        Queue<Vertex> vertexs = new LinkedList<>();
        vertexs.add(findVertexByName(root));

        Map<Vertex,Integer> score = new HashMap<>();
        for(Vertex cur : allVertex){
            score.put(cur, Integer.MAX_VALUE);
        }

        score.put(findVertexByName(root),0);

        while(!vertexs.isEmpty()){
            Vertex cur = vertexs.poll();
            visitedVertex.add(cur);
            Queue<Edge> edges = new PriorityQueue<>(cur.getAllEdge());
            while(!edges.isEmpty()){
                Edge curEdge = edges.poll();
                Vertex targetVertex = getOtherVertex(curEdge, visitedVertex);

                if(targetVertex == null){
                    continue;
                }
                vertexs.add(targetVertex);

                int curScore = score.get(targetVertex);
                int newScore = score.get(cur)+curEdge.getValue();
                if (curScore > newScore){
                    score.put(targetVertex, newScore);
                }
            }
        }

        for(Vertex v : score.keySet()){
            System.out.printf("%s : %d\n",v.getName(),score.get(v));
        }
    }
}