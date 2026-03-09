
import java.util.HashSet;
import java.util.Set;

public class Vertex {
    private String name;                        
    private Set<Edge> allEdge;                  // เก็บ edge ทั้งหมดของ vertex นั้น

    public Vertex(String name) {                // constructor
        this.name = name;
        this.allEdge = new HashSet<>();
    }

    public String getName() {                   // คืน attribute name
        return name;
    }

    public Set<Edge> getAllEdge() {             // คืน attribute allEdge
        return allEdge;
    }

    @Override
    public String toString() {                  // เขียน override method เพื่อให้ print attribute name ออกมา
        return name;
    }

    @Override
    public boolean equals(Object obj) {         // เขียน override method ให้สามารถใช้การตรวจสอบการเท่ากันของ object ด้วย equals ได้
        if (this == obj)                        // check obj เป็น self ไหม
            return true;
        if  (obj == null)                       // check obj เป็น null ไหม
            return false;
        if (getClass() != obj.getClass())       // check type class ของ object
            return false;
        Vertex other = (Vertex) obj;            // downcast 
        return name.equals(other.name);         // check attribute name ตรงกันไหม
    }
}