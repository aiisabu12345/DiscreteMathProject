
import java.util.HashSet;
import java.util.Set;

public class Vertex {
    private String name;
    private Set<Edge> allEdge;

    public Vertex(String name) {
        this.name = name;
        this.allEdge = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public Set<Edge> getAllEdge() {
        return allEdge;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vertex other = (Vertex) obj;
        return name.equals(other.name);
    }
}