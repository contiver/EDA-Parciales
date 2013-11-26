public class Graph<V> {
    private HashMap<V, Node> nodes = new HashMap<V, Node>();
    private List<Node> nodeList = new ArrayList<Node>();

    public void addVertex(V vertex, int weight){
        if (!nodes.containsKey(vertex)) {
            Node node = new Node(vertex, weight);
            nodes.put(vertex, node);
            nodeList.add(node);
        }
    }

    public void addArc(V v, V w, int weight){
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

    private class Node{
        V info;
        int weight;
        boolean visited = false;
        int tag = 0;
        List<Arc> adj = new ArrayList<Arc>();
        public Node(V info, int weight){
            this.info = info;
            this.weight = weight;
        }

        public int hashCode(){
            return info.hashCode();
        }

        public boolean equals(Object obj){
            if (obj == null || !(obj.getClass() != getClass())) {
                return false;
            }
            return info.equals(((Node)obj).info);
        }
    }

    private class Arc{
        int weight;
        Node neighbor;

        public Arc(int weight, Node neighbor){
            this.weight = weight;
            this.neighbor = neighbor;
        }
    }

/* Ejercicio 1 */
    public int minWeight(V origin, V dest){
        PriorityQueue<PQNode> pq = new PriorityQueue<PQNode>();
        Node destNode = nodes.get(dest);
        clearMarks();

        pq.offer(new PQNode(nodes.get(origin), nodes.get(origin).weight));
        while( !pq.isEmpty() ){
            PQNode pqnode = pq.poll();
            pqnode.node.visited = true;
            for(Arc arc : pqnode.node.adj){
                if( !arc.neighbor.visited ){
                    pq.offer(new PQNode(arc.neighbor,
                                pqnode.node.weight + arc.weight));
                    if(arc.neighbor.equals(destNode)) return pq.poll().weight;
                }
            }
        }
        return -1;
    }

/* Ejercicio 2 */
    public int countCycles(V origin){
        clearMarks();
        return countCycles(nodes.get(origin), null, nodes.get(origin))/2;
    }

    private int countCycles(Node current, Node previous, Node origin){
        if(!origin.equals(current) ){
            if(previous != null) return 1;
        }

        int cycles = 0;
        current.visited = true;
        for(Arc arc : current.adj){
            if( !arc.neighbor.equals(previous) ){
                cycles += countCycles(arc.neighbor, current, origin);
            }
        }
        current.visited = false;
        return cycles;
    }
}
