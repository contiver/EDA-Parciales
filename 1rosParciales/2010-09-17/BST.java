public class BST<T> {
    private Node<T> root;
    private Comparator<? super T> cmp;

    public BST(Comparator<? super T> cmp){
        this.cmp = cmp;
    }

    public List<T> getInOrder(T inf, T sup){
        if(cmp.compare(inf, sup) > 0) return null;
        List<T> list = new LinkedList<T>();
        getInOrder(inf, sup, root, list);
        return list;
    }

    private void getInOrder(T inf, T sup, Node<T> node, List<T> list){
        if(node == null) return;
        if(cmp.compare(node.elem, inf) >= 0){
            getInOrder(inf, sup, node.left, list);
            if(cmp.compare(node.elem, sup) <= 0){
                list.add(node.elem);
                getInOrder(inf, sup, node.right, list);
            }
        }else{
            getInOrder(inf, sup, node.right, list);
        }
    }
	 
    public void add(T elem){
        root = add(elem, root);
    }

    private Node<T> add(T elem, Node<T> node){
        if(node == null) return new Node<T>(elem);
        int comparison = cmp.compare(node.elem, elem);
        if(comparison > 0){
            node.left = add(elem, node.left);
        }
        if(comparison < 0){
            node.right = add(elem, node.right);
        }
        return node;
    }
	
		private static class Node<T>{
        T elem;
        Node<T> left;
        Node<T> right;

        public Node(T elem){
            this.elem = elem;
        }
    }
/* Test del ejercicio

    public static void main(String args[]){
        BST<Integer> bst = new BST<Integer>(new Comparator<Integer>(){
            public int compare(Integer i1, Integer i2){
                return i1.compareTo(i2);
            }
        });
        bst.add(5); bst.add(2); bst.add(3); bst.add(8); bst.add(7); bst.add(10); bst.add(1);
        System.out.println(bst.getInOrder(1,7));
    }
*/
}