
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
}