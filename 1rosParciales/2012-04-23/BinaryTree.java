public class BinaryTree<T> {
    private T value;
    private BinaryTree<T> left, right;

    public BinaryTree(T value, BinaryTree<T> left, BinaryTree<T> right){
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public T getValue(){
        return value;
    }

    public BinaryTree<T> getLeft(){
        return left;
    }

    public BinaryTree<T> getRight(){
        return right;
    }

    public static <T> boolean isHeap(BinaryTree<T> tree, Comparator<? super T> cmp){
        return isHeapRecursive(tree,cmp) == -1 ? false : true;
    }
    
    private static <T> int isHeapRecursive(BinaryTree<T> tree, Comparator<? super T> cmp){
        if(tree == null) return 0;
        if(tree.getLeft() != null){
            if(cmp.compare(tree.getValue(), tree.getLeft().getValue()) > 0) return -1;
        }
        if(tree.getRight() != null){
            if(cmp.compare(tree.getValue(), tree.getRight().getValue()) > 0) return -1;
        }
        if(tree.getLeft() == null && tree.getRight() != null){
            return -1;
        }
        int left = isHeapRecursive(tree.getLeft(), cmp);
        int right = isHeapRecursive(tree.getRight(), cmp);
        if(left == -1 || right == -1 || left - right <= 1){  /* si left - right <= 1, No cumple la ultima condicion*/
            return -1;
        }

        return 1+ Math.max(left, right);
    }
}