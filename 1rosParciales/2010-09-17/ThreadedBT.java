public class ThreadedBT<T>{
    private Node<T> root;
    private Comparator<? super T> cmp;

    public ThreadedBT(Comparator<? super T> cmp){
        this.cmp = cmp;
    }

    public void add(T elem){
        root = add(root, elem);
    }

    private Node<T> add(Node<T> node, T elem){
        if(node == null) return new Node<T>(elem);

        int comparison = cmp.compare(node.elem, elem)
        if(comparison > 0){
            node.left = add(node.left, elem);
            node.left.inOrderSuc = node;
            node.left.inOrderPred = node.inOrderPred;
        }
        if(comparison < 0){
            node.right = add(node.right, elem);
            node.right.inOrderSuc = node.inOrderSuc;
            node.right.inOrderPred = node;
        }
        return node;
    }

    private void inOrder(Node<T> node){
        if(node != null){
            inOrder(node.left);
            System.out.println(node.elem);
            inOrder(node.right);
        }
    }

    public void inOrder(){
        inOrder(node);
    }

    private static class Node<T>{
        T elem;
        Node<T> left = null,
                right = null,
                inOrderSuc = null,
                inOrderPred = null;

        public Node(T elem){
            this.elem = elem;
        }
    }
}
