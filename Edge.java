
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Edge other = (Edge) obj;
        return name.equals(other.name);
    }
}