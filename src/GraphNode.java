import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.hash;

public class GraphNode {
    int key;
    List<GraphNode> neighbors;

    public GraphNode(int key) {
        this.key = key;
        this.neighbors = new ArrayList<>();
    }

    @Override
    public int hashCode() {
        return hash(key);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GraphNode)) {
            return false;
        }
        GraphNode g = (GraphNode)obj;
        return g.key == this.key;
    }
}
