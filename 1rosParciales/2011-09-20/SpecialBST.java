public class SpecialBST<T>{
    Node<T> root;
    Comparator<? super T> cmp;

    private static abstract class Node<T>{
        public abstract boolean belongs(T elem);
    }

    private static InnerNode<T> extends Node<T>{
        T elem;
        Node<T> left, right;

        public InnerNode(T elem){
            this.elem = elem;
        }

        public boolean belongs(T elem){
            if(cmp.compare(this.elem, elem) > 0){
                return left == null ?  false : left.belongs(elem);
            }else{
                return right == null ? false : right.belongs(elem);
            }
        }
    }

    private static class Leaf<T> extends Node<T>{
        List<T> values = new ArrayList<T>();

        public boolean belongs(T elem){
            return values.contains(elem);
        }
    }

    public boolean belongs(T elem){
        return root.belongs(elem);
    }
}
