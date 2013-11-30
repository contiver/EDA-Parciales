public class BST<T> implements BinarySearchTree<T> {
    private Node root;
    private Comparator<? super T> cmp;

    public BST(Comparator<? super T> cmp) {
        this.root = null;
        this.cmp = cmp;
    }
    public void add(T value) {
        root = add(root, value);
    }
    public boolean contains(T value) {
        return contains(root, value);
    }
    public void remove(T value) {
        root = remove(root, value);
    }

    private class Node {
        T value;
        Node left;
        Node right;
        Node(T value) {
            this.value = value;
        }
    }

/* Ejercicio 1 */
    public boolean pBalanced(double p){
        if(p < 0) return false;
        return pBalanced(p, root) == -1 ? false : true;
    }

    private int pBalanced(double p, Node<T> node){
        if(node == null) return 0;
        int left = pBalanced(p, node.left);
        if(left == -1 ) return -1;
        int right = pBalanced(p, node.right);
        int w = left + right +1;
        if(right == -1 || left > p*w || right > p*w) return -1;
        return w;
    }
}
