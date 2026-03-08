
import java.util.HashSet;
import java.util.Set;

public class Edge implements Comparable<Edge> {
    private String name;
    private Set<Vertex> allVertex;
    private int weight;

    public Edge(String name) {
        this.name = name;
        this.allVertex = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void addVertex(Vertex v) {
        allVertex.add(v);
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getValue() {
        return weight;
    }

    public Set<Vertex> getAllVertex() {
        return allVertex;
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return name;
    }
}