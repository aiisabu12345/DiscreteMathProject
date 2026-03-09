
import java.util.HashSet;
import java.util.Set;

public class Vertex {
    private String name;                        
    private Set<Edge> allEdge;                  // allEdge เก็บ edge ทั้งหมดของ vertex นั้น

    public Vertex(String name) {                // constructor โดยจะสร้าง HashSet ใหม่เก็บไว้ใน allEdge
        this.name = name;
        this.allEdge = new HashSet<>();
    }

    public String getName() {                   // method นี้คืน attribute name
        return name;
    }

    public Set<Edge> getAllEdge() {             // method นี้คืน attribute allEdge
        return allEdge;
    }

    @Override
    public String toString() {                  //  override method ที่ print attribute name ออกมา
        return name;
    }

    @Override
    public boolean equals(Object obj) {         // override method ให้สามารถใช้การตรวจสอบการเท่ากันของ object ด้วย equals ได้
        if (this == obj)                        // เงื่อนไขตรวจสอบการเป็น self ของ obj 
            return true;
        if  (obj == null)                       // เงื่อนไขตรวจสอบ null 
            return false;
        if (getClass() != obj.getClass())       // เงื่อนไขตรวจสอบประเภท class ของ object
            return false;
        Vertex other = (Vertex) obj;            // obj ใช้ downcast เพื่อเรียกใช้ attribute name
        return name.equals(other.name);         // เงื่อนไข attribute name ตรงคืน true และไม่ตรงคืน false
    }
}