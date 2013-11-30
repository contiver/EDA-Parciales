public class MaxHeapTree<T>{
    private Node root;
    private Comparator<? super T> cmp;

    public MaxHeapTree(Comparator<? super T> cmp){
        this.root = null;
        this.cmp = cmp;
    }

/*
 * Faltaria implementar el insert creo
 */

    public T delete(){
        Node ret = root;
        root = delete(root);
        return ret.value;
    }

    private Node<T> delete(Node<T> node){
        if(node == null || node.left == null && node.right == null){
            return null;
        }
        if(node.left != null){
            if(node.right == null ||
               cmp.compare(node.left.value, node.right.value) > 0){
                node.value = node.left.value;
                node.left = delete(node.left);
            }
        }else{
            node.value = node.right.value;
            node.right = delete(node.right);
        }
        return node;
    }

    private static class Node{
        T value;
        Node left;
        Node right;

        Node(T value){
            this.value = value;
        }
    }

}
