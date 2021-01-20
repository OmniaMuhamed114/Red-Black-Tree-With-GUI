import java.awt.*;

public class Node implements Comparable<Node> {
    int value;
    Node parent;
    Node left;
    Node right;
    Color color;
    @Override
    public int compareTo(Node o) {
        return ((Comparable) o.value).compareTo(value);
    }
    public Node() {
    }
}
