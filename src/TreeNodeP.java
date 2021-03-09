public class TreeNodeP {
    public TreeNodeP left;
    public TreeNodeP right;
    public TreeNodeP parent;
    public int key;

    public TreeNodeP(int key, TreeNodeP parent) {
        this.key = key;
        this.parent = parent;
    }
}
