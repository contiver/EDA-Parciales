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

    public void addArc(V v, V w){
        Node origin = nodes.get(v);
        Node dest = nodes.get(w);
        if (origin != null && dest != null && !origin.equals(dest)){
            for (Arc arc : origin.adj){
                if (arc.neighbor.info.equals(w)){
                    return;
                }
            }
            origin.adj.add(new Arc(dest));
            dest.adj.add(new Arc(origin));
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

        public Arc(Node neighbor){
            this.neighbor = neighbor;
        }
    }

/* Ejercicio 1 */
/* INCOMPLETO ! */
    public List<V> eulerianCycle(){
        if( !clearAndCheck() ) return null;
        List<V> l = new LinkedList<V>();
        eulerianCycle(l, nodeList.get(0), null, nodeList.get(0));
        boolean thereAreUnvisitedArcs;
        for(Node node : nodeList){

        }
        while()
        return l.size() == 0 ? null : l;
    }

    private boolean clearAndCheck(){
        for(Node node : nodeList){
            node.visited = false;
            if(node.adj.size() % 2 != 0) return false;
        }
        return true;
    }

    private boolean eulerianCycle(List<V> l, Node curr, Node prev, Node dest){
        l.add(curr.info);
        if(curr.info.equals(dest.info)){
            if(prev != null) return true;
        }
        curr.visited = true;
        for(Arc arc : curr.adj){
            if( !arc.neighbor.visited && arc.neighbor != prev ){
                if(eulerianCycle(l, arc.neighbor, curr, dest)) return true;
            }
        }
    }
}
