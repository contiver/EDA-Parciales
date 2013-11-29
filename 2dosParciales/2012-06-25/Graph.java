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

    public void addArc(V v, V w, int weight){
        Node origin = nodes.get(v);
        Node dest = nodes.get(w);
        if (origin != null && dest != null && !origin.equals(dest)){
            for (Arc arc : origin.adj){
                if (arc.neighbor.info.equals(w)){
                    return;
                }
            }
            origin.adj.add(new Arc(weight, dest));
            dest.adj.add(new Arc(weight, origin));
        }
    }

    private class Node{
        V info;
        boolean visited = false;
        List<Arc> adj = new ArrayList<Arc>();

        public Node(V info){
            this.info = info;
        }

        public int hashCode(){
            return info.hashCode();
        }

        public boolean equals(Object obj){
            if (obj == null || obj.getClass() != getClass()){
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

/* Necesario para ejercicios 1 y 2 */
    private void clearMarks(){
        for(Node node : nodeList){
            node.visited = false;
        }
    }

/* Ejercicio 1 */
    public boolean isDFS(List<V> values){
        clearMarks();
        Iterator<V> it = values.iterator();
        if(!it.hasNext()){
            return nodes.size() == 0 ? true : false;
        }
        Node firstNode = nodes.get(it.next());
        return isDFS(firstNode, it) && !it.hasNext();
    }

    private boolean isDFS(Node current, Iterator<V> it){
        current.visited = true;
        boolean unvisitedNodesRemain = false;
        boolean valueNotFound = true;

        for(Arc arc : current.adj){
            if( !arc.neighbor.visited ){
                unvisitedNodesRemain = true;
                break;
            }
        }

        while(unvisitedNodesRemain){
            if( !it.hasNext() ) return false;

            V nextValue = it.next();
            for(Arc arc : current.adj){
                if( !arc.neighbor.visited && arc.neighbor.info.equals(nextValue)){
                    valueNotFound = false;
                    if( !isDFS(arc.neighbor, it)) return false;
                }
            }
            if(valueNotFound) return false;
            unvisitedNodesRemain = false;
            for(Arc arc : current adj){
                if( !arc.neighbor.visited ){
                    unvisitedNodesRemain = true;
                    break;
                }
            }
        }
        return true;
    }

/* Ejercicio 2 */
    public Graph<V> subgraph(V v, int n){
        PriorityQueue<PQNode> pq = new PriorityQueue<PQNode>();
        Graph<V> ret = new Graph<V>();
        clearMarks();
        pq.offer(new PQNode(nodes.get(v), 0);

        while( !pq.isEmpty() ){
            PQNode pqnode = pq.poll();
            pqnode.node.visited = true;
            ret.addVertex(pqnode.node);
            for(Arc arc : pqnode.node.adj){
                int accumWeight = arc.weight + pqnode.distance;
                if( !arc.neighbor.visited && accumWeight <= n){
                    pq.offer(new PQNode(arc.neighbor, accumWeight))
                    ret.addVertex(pqnode.node);
                    ret.addArc(pqnode.node.info, arc.neighbor.info, arc.weight);
                }
            }
        }
        for(Node n : ret.nodes){
            for(Arc arc : n.adj){
                if(ret.nodes.containsKey(arc.neighbor.info)){
                    ret.addVertex(n.info, arc.neighbor.info, arc.weight);
                }
            }
        }
        return ret;
    }

    private class PQNode implements Comparable<PQNode>{
        Node node;
        int distance;

        public PQNode(Node node, int distance){
            this.node = node;
            this.node = node;
        }

        public int compareTo(PQNode n){
            return Integer.valueOf(distance).compareTo(n.distance);
        }
    }
}
