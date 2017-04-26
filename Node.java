

public class Node implements Comparable <Node> {

	public int value;
    public double weight;
    public Node leftTree;
    public Node rightTree;
    public Node parent;

    // constructors
    public Node() {
        parent = null;
    }

    public Node( int v, double w, Node lTree, Node rTree, Node par ) {
        value = v;
        weight = w;
        leftTree = lTree;
        rightTree = rTree;
        parent = par;
     }
    
    // setters/getters
    
    @Override
    public int compareTo(Node rhs) {
        return (int) (weight - rhs.weight);
    }

    @Override
    public String toString() {
        String str = "";
        str += this.value;
        return str;
    }
}
