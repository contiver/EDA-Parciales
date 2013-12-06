public class Graph<V> {
    private HashMap<V, Node> nodes = new HashMap<V, Node>();
    private List<Node> nodeList = new ArrayList<Node>();

    public void addVertex(V vertex){
        if (!nodes.containsKey(vertex)){
            Node node = new Node(vertex);
            nodes.put(vertex, node);
            nodeList.add(node);
        }
    }

    public void addArc(V v, V w, Double d){
        Node origin = nodes.get(v);
        Node dest = nodes.get(w);

        if (origin != null && dest != null && !origin.equals(dest)){
            for (Arc arc : origin.adj){
                if (arc.neighbor.info.equals(w)){
                    return;
                }
            }
            origin.adj.add(new Arc(dest, d));
            dest.adj.add(new Arc(origin, d));
        }
    }

    private class Node{
        V info;
        boolean visited = false;
        int tag = 0;
        List<Arc> adj = new ArrayList<Arc>();

        public Node(V info){
            this.info = info;
        }

        public int hashCode(){
            return info.hashCode();
        }

        public boolean equals(Object obj){
            if (obj == null || (obj.getClass() != getClass())){
                return false;
            }
            return info.equals(((Node)obj).info);
        }
    }

    private class Arc{
        Node neighbor;
        Double weight;

        public Arc(Node neighbor, Double weight){
            this.neighbor = neighbor;
            this.weight = weight;
        }
    }

/* Ejercicio 1 */
    public double hamPathWeight(){
        double aux, min = -1;
        for(Node n : nodeList){
            aux = hamPathWeight(n, nodeList.size(), 0, -1);
            if(min == -1 || aux != -1 && min > aux){
                min = aux;
            }
        }
        return min;
    }

    private double hamPathWeight(Node node, int count,
                                 double weight, double minWeight){
        if(node.visited) return minWeight;
        if(count == 1){
            if(minWeight == -1 || minWeight > weight){
                return weight;
            }
            return minWeight;
        }

        node.visited = true;
        for(Arc arc : node.adj){
            minWeight = hamPathWeight(arc.neighbor, count -1,
                                    weight + arc.weight, minWeight);
        }
        node.visited = false;
        return minWeight;
    }

/* Necesarios para ejercicios 2 y 3 */
    private void clearMarks(){
        for(Node n : nodeList){
            n.visited = false;
        }
    }

    /* Ejercicio 2 */
    public V mostPathWith(Double p){
        if(p <= 0) return null;
        Node ret = null;
        int max = 0;
        for(Node n : nodeList){
            int aux = reachableNodes(n, p);
            if(max < aux){
                max = aux;
                ret = n;
            }
        }
        return ret == null ? null : ret.info;
    }

    private int reachableNodes(Node start, Double p){
        PriorityQueue<WNode> pq = new PriorityQueue<WNode>();
        clearMarks();
        pq.offer(new WNode(start, p));
        int reachables = 0;

        while( !pq.isEmpty() ){
            WNode pqnode = pq.poll();
            pqnode.node.visited = true;
            for(Arc arc : pqnode.node.adj){
                if( !arc.neighbor.visited ){
                    double remainingWeight = pqnode.weight - arc.weight;
                    if(remainingWeight <= 0) return reachables;
                    pq.offer(new WNode(arc.neighbor, remainingWeight));
                    reachables++;
                }
            }
        }
        return reachables;
    }

    private class WNode implements Comparable<WNode>{
        Node node;
        Double weight;

        public WNode(Node node, Double weight){
            this.node = node;
            this.weight = weight;
        }

        public int compareTo(WNode pqNode) {
            return pqNode.weight.compareTo(this.weight);
        }
    }

/* Ejercicio 3 */
    public V centralNode(){
        Node node = null;
        Double min = -1d;

        for(Node n : nodeList){
            Double aux = singularityOf(n);
            if(min == -1 || min > aux){
                min = aux;
                node = n;
            }
        }
        return node == null ? null : node.info;
    }

    private Double singularityOf(Node node){
        PriorityQueue<PQNode> pq = new PriorityQueue<PQNode>();
        clearMarks();
        Double singularity = 0d;
        pq.offer(new PQNode(node, 0d));

        while( !pq.isEmpty() ){
            PQNode pqnode = pq.poll();
            pqnode.node.visited = true;
            for(Arc arc : pqnode.node.adj){
                if( !arc.neighbor.visited ){
                    Double aux = pqnode.distance + arc.weight;
                    pq.offer(new PQNode(arc.neighbor, aux));
                    if(singularity < aux) singularity = aux;
                }
            }
        }
        return singularity;
    }

    private class PQNode implements Comparable<PQNode>{
        Node node;
        Double distance;

        public PQNode(Node node, Double distance){
            this.node = node;
            this.distance = distance;
        }

        public int compareTo(PQNode pqnode){
            return distance.compareTo(pqnode.distance);
        }
    }
}