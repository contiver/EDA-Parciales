public class Graph<V>{
    private HashMap<V, Node> nodes = new HashMap<V, Node>();
    private List<Node> nodeList = new ArrayList<Node>();

    public void addVertex(V vertex){
        if (!nodes.containsKey(vertex)){
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
    public boolean isBFS(List<V> values){
        if(values.size() != nodeList.size()){
            return false;
        }
        Queue<Node> q = new LinkedList<Node>();
        for(Node n : nodeList){
            n.tag = -1;         // Aprovecho los tags como marca
        }
        Node n = nodes.get(values.get(0));
        n.tag = 0;
        q.push(n);

        while( !q.isEmpty() ){
            Node node = q.poll();
            for(Arc arc : node.adj){
                if( arc.neighbor.tag == -1 ){
                    q.push(a.neighbor);
                    arc.neighbor.tag = node.tag + 1;
                }
            }
        }

        clearMarks();
        int previousLevel = 0;
        for(V v : values){
            Node aux = nodes.get(v);
            if(aux == null || aux.visited) return false;
            aux.visited = true;  // Con visited chequeo valores repetidos
            int currentLevel = aux.tag;
            if(previousLevel > currentLevel) return false;
            previousLevel = currentLevel;
        }
        return true;
    }

    public void clearMarks(){
        for(Node n : nodeList){
            n.visited = false;
        }
    }
}
