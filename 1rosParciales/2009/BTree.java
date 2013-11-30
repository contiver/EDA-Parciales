public class BTree<T implements Comparable<? super T>>{
    private Node<T> root;

    public BTree(){
        root = null;
    }

    public List<T> range(T low, T upp){
        List<T> list = new LinkedList<T>();
        range(root, low, upp, list);
        return list;
    }

    // ????????????? ver esto
    private void range(Node<T> node, T low, T upp, List<T> list){
        if(node == null) return;
        for(int i = 0; i < node.getKeys().size(); i++){
            if(node.getKeys().get(i).compareTo(low) > 0){
                range(node.getChildren().get(i), low, upp, list);
            }
            if(node.getKeys().get(i).comprareTo(low) >= 0 &&
                    node.getKeys().get(i).compareTo(upp) <= 0){
                list.add(node.getKeys().get(i));
            }
            if(node.getKeys().get(i).compareTo(upp) > 0) return;
        }
        range(node.getChildren().get(node.getKeys().size())), low, upp, list);
    }
}

