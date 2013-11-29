public class BinaryTree<T> {
    private T value;
    private BinaryTree<T> left, right;

    public BinaryTree(T value, BinaryTree<T> left, BinaryTree<T> right){
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public T getValue(){
        return this.value;
    }

    public BinaryTree<T> getLeft(){
        return this.left;
    }

    public BinaryTree<T> getRight(){
        return this.right;
    }

    public static <T> boolean checkPostorder(BinaryTree<T> tree, List<T> values){
        Iterator<T> it = values.iterator();
        return checkPostorder(tree, it) && !it.hasNext();
    }

    private static <T> boolean checkPostorder(BinaryTree<T> tree, Iterator<T> it){
        if( tree == null ) return true;
        if( !checkPostorder(tree.left, it) || !checkPostorder(tree.right, it)) return false;
        if( !it.hasNext() ) return false;
        return tree.value.equals(it.next());
    }
}