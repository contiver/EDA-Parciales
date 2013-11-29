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

/* Necesario para ejercicios 1 y 3 */
    public void clearMarks(){
        for(Node n : nodeList){
            n.visited = false;
        }
    }

/* Ejercicio 1 */
    public List<V> tonianCycle(){
        LinkedList<V> l = new LinkedList<V>(),
                      better = new LinkedList<V>();

        for(Node node : nodeList){
            clearkMarks();
            tonianCycle(node, null, node, l, better);
            if( better.size() -1 >= nodeList.size()/2 ) return better;
        }
        return null;
    }

    private boolean tonianCycle(Node current, Node prev, Node dest,
                            LinkedList<V> l, LinkedList<V> best){
        if(current.equals(dest) && prev != null){
            l.addLast(current.info);
            if(better.size() < l.size()){
                better.clear();
                better.addAll(l);
            }
            return true;
        }
        if(current.visited) return false;
        l.addLast(current.info);
        current.visited = true;
        for(Arc arc : current.adj){
            if(arc.neighbor != prev &&
               tonianCycle(arc.neighbor, current, dest, l, better)){
                return true;
            }
        }
        current.visited = false;
        l.removeLast();
        return false;
    }

/* Ejercicio 2 */
    public double maxPathAprox(long maxtime, V from, V to){
        long start = System.currentTimeMilis();
        double best = 0;
        Node from = nodes.get(from);
        Node to = nodes.get(to);
        if(from == null || to == null){
            // throw exception
        }

        while(System.currentTimeMilis() - start < maxtime){
            List<Node> localBestPath = initialSolution(from, to);
            double localBest = evaluate(localBestPath);
            boolean localMax = false;
            while( !localMax ){
                localMax = true;
                if(System.currentTimeMilis() - start > maxtime){
                    if(localBest > best){
                        return localBest;
                    }
                    return best;
                }
                List<Node> auxPath = localBestPath.clone();
                for(List<Node> neighbor : neighbors(auxPath)){
                    if(System.currentTimeMilis() - start > maxtime){
                        if(localBest > best) return localBest;
                        return best;
                    }
                    double curr = evaluate(neighbor);
                    if(curr > localBest){
                        localBest = curr;
                        localBestPath = neighbor;
                        localMax = false;
                    }
                }
            }
            if(localBest > best) best = localBest;
        }
        return best;
    }

    private List<List<Node>> neighbors(List<Node> list){
        List<List<Node>> neighbors = new ArrayList<List<Node>>();

        for(int i = 0; i < list.size(); i++){
            neighbors.addAll(addPaths(list, list.get(i), list.get(i+1)));
        }
        return neighbors;
    }

    private List<List<Node>> addPaths(List<Node> list, Node from, Node to){
        List<List<Node>> paths = new ArrayList<List<List<Node>>();

        for(Arc a : from.adj){
            if(a.neighbor != to){
                for(Arc a2 : a.neighbor.adj){
                    if(a2.neighbor == to){
                        List<Node> aux = list.clone();
                        aux.add(list.indexOf(to), a.neighbor);
                        paths.add(aux);
                    }
                }
            }
        }
        return paths;
    }

    private List<Node> initialSolution(Node from, Node to){
        List<Node> list = new ArrayList<Node>();
        list.add(from);
        Node n = from;
        while( n != to){
            n.visited = true;
            int rand = ((int)Math.random()*n.adj.size());
            Node aux = n.adj.get(rand).neighbor;
            if( !aux.visited ){
                n = aux;
                list.add(n);
            }
        }
        return list;
    }

    private double evaluate(List<Node> list){
        double ret = 0;

        for(int i = 0; i < list.size() - 1; i++){
            for(Arc arc : list.get(i).adj){
                if(a.neighbor == list.get(i+1)){
                    ret += arc.weight;
                    break;
                }
            }
        }
        return ret;
    }


/* Ejercicio 3 */
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
}
