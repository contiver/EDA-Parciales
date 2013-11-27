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

    public void addArc(V v, V w, int weight) {
        Node origin = nodes.get(v);
        Node dest = nodes.get(w);
        if (origin != null && dest != null && !origin.equals(dest)) {
            for (Arc arc : origin.adj) {
                if (arc.neighbor.info.equals(w)) {
                    return;
                }
            }
            origin.adj.add(new Arc(weight, dest));
            dest.adj.add(new Arc(weight, origin));
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
            if (obj == null || obj.getClass() != getClass()) {
                return false;
            }
            return info.equals(((Node)obj).info);
        }
    }

    private class Arc {
        int weight;
        Node neighbor;

        public Arc(int weight, Node neighbor) {
            this.weight = weight;
            this.neighbor = neighbor;
        }
    }

/* Ejercicio 1 */
    public double maxDistance(V start, V end){
        clearMarks();
        return maxDistance(nodes.get(start), nodes.get(end), 0, 1);
    }

    private double maxDistance(Node from, Node to, double sum, double max){
        if(from == to) return sum > max ? sum : max;
        if(from.visited) return max;
        from.visited = true;
        for(Arc arc : from.adj){
            max = maxDistance(arc.neighbor, to, sum, max);
        }
        from.visited = false;
        return max;
    }
}
