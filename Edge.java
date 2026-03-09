
import java.util.HashSet;
import java.util.Set;

public class Edge implements Comparable<Edge> {
    private String name;
    private Set<Vertex> allVertex;          // เก็บ vertex ทั้งหมด ของ object edge 
    private int weight;                     

    public Edge(String name) {              // constructor
        this.name = name;
        this.allVertex = new HashSet<>();
    }

    public String getName() {               // คืน attribute name
        return name;
    }

    public void addVertex(Vertex v) {       // method เพิ่ม Vertex ใน allVertex
        allVertex.add(v);
    }

    public void setWeight(int weight) {     // setter weight
        this.weight = weight;
    }

    public int getValue() {                 // คืน attribute weight 
        return weight;
    }

    public Set<Vertex> getAllVertex() {     // คืน attribue allVertex
        return allVertex;
    }

    @Override
    public int compareTo(Edge other) {                      // override method ใช้ในการเปรียนเทียบของ object โดยมี weight เป็นเงื่อนไข
        return Integer.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {              // เขียน override method เพื่อให้ print attribute name ออกมา
        return name;
    }

    @Override
    public boolean equals(Object obj) {      // เขียน override method ให้สามารถใช้การตรวจสอบการเท่ากันของ object ด้วย equals ได้
        if (this == obj)                     // check obj เป็น self ไหม
            return true;
        if (obj == null)                     // check obj เป็น null ไหม
            return false;
        if (getClass() != obj.getClass())    // check type class ของ object
            return false;
        Edge other = (Edge) obj;             // downcast
        return name.equals(other.name);      // check attribute name ตรงกันไหม
    }
}