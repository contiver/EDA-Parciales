public class Graph<V> {
    private HashMap<V, Node> nodes = new HashMap<V, Node>();
    private List<Node> nodeList = new ArrayList<Node>();
    public void addVertex(V vertex) {
        if (!nodes.containsKey(vertex)) {
            Node node = new Node(vertex);
            nodes.put(vertex, node);
            nodeList.add(node);
        }
    }

    public void addArc(V v, V w) {
        Node origin = nodes.get(v);
        Node dest = nodes.get(w);
        if (origin != null && dest != null && !origin.equals(dest)) {
            for (Arc arc : origin.adj) {
                if (arc.neighbor.info.equals(w)) {
                    return;
                }
            }
            origin.adj.add(new Arc(dest));
            dest.adj.add(new Arc(origin));
        }
    }

    private class Node {
        V info;
        boolean visited = false;
        int tag = 0;
        List<Arc> adj = new ArrayList<Arc>();
        public Node(V info) {
            this.info = info;
        }
        public int hashCode() {
            return info.hashCode();
        }
        public boolean equals(Object obj) {
            if (obj == null || (obj.getClass() != getClass())) {
                return false;
            }
            return info.equals(((Node)obj).info);
        }
    }

    private class Arc {
        Node neighbor;
        public Arc(Node neighbor) {
            this.neighbor = neighbor;
        }
    }

/* Comienzo del ejercicio 3 */
    public int radio(){
        int radio = max = 0;
        clearMarks();
        for(Node n : nodeList){
            max = maxDistance(n, 0, 0);
            if(max < radio){
                radio = max;
            }
        }
        return radio;
    }

    private int maxDistance(Node node, int sum, int max){
        if(node.visited) return max;

        node.visited = true;
        for(Arc arc : node.adj){
            max = maxDistance(arc.neighbor, sum + 1, max);
        }
        node.visited = false;
        return sum > max ? sum : max;
    }
/* Fin del ejercicio 3 */
}
