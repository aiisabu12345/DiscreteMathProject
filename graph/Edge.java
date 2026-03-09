
import java.util.HashSet;
import java.util.Set;

public class Edge implements Comparable<Edge> {
    private String name;
    private Set<Vertex> allVertex;                                  // attribute allVertex เก็บ vertex ทั้งหมด ของ object edge 
    private int weight;                     

    public Edge(String name) {                                      // constructor Edge
        this.name = name;
        this.allVertex = new HashSet<>();
    }

    public String getName() {                                       // method คืน attribute name
        return name;
    }

    public void addVertex(Vertex v) {                               // method เพิ่ม Vertex ใน allVertex
        allVertex.add(v);
    }

    public void setWeight(int weight) {                             // method ตั้งค่า weight
        this.weight = weight;
    }

    public int getValue() {                                         // method คืน attribute weight 
        return weight;
    }

    public Set<Vertex> getAllVertex() {                             // method คืน attribue allVertex
        return allVertex;
    }

    @Override
    public int compareTo(Edge other) {                              // override method ใช้ในการเปรียนเทียบของ object โดยมี weight เป็นเงื่อนไข
        return Integer.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {                                      // override method เพื่อ print attribute name ออกมา
        return name;
    }

    @Override
    public boolean equals(Object obj) {                             // override method ใช้ในการตรวจสอบการเท่ากันของ object ด้วย equals ได้
        if (this == obj)                                            // เงื่อนไขตรวจสอบการเป็น self ของ obj
            return true;
        if (obj == null)                                            // เงื่อนไขตรวจสอบ null
            return false;
        if (getClass() != obj.getClass())                           // เงื่อนไขตรวจสอบประเภท class ของ object
            return false;
        Edge other = (Edge) obj;                                    // obj ใช้ downcast เพื่อเรียกใช้ attribute name
        return name.equals(other.name);                             // เงื่อนไข attribute name ตรงคืน true และไม่ตรงคืน false
    }
}