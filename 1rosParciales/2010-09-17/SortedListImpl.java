public class SortedListImpl<T> implements SortedList<T> {
    private Node<T> first = null,
                    nodeToUndo = null;
    private Comparator<? super T> cmp;

    public SortedListImpl(Comparator<? super T> cmp){
        this.cmp = cmp;
    }

    public void add(T elem){
        if(elem == null) throw new IllegalArgumentException();

        Node<T> current = first;
        while(current != null){
            if(cmp.compare(current.elem, elem) < 0){
                if(current.next != null && cmp.compare(current.next.elem, elem) < 0 ){
                    current = current.next;
                }else{
                    nodeToUndo = new Node<T>(elem, current.next, current, nodeToUndo);
                    current.next = nodeToUndo;
                    return;
                }
            }else{
                nodeToUndo = new Node<T>(elem, current, null, nodeToUndo);
                first = nodeToUndo;
                current.previous = nodeToUndo;
                return;
            }
        }
        first = nodeToUndo = new Node<T>(elem, null, null, null);
    }

    public void undo(){
        if(nodeToUndo != null){
            if(nodeToUndo.previous != null) nodeToUndo.previous.next = nodeToUndo.next;
            if(nodeToUndo.next != null){
                if(nodeToUndo == first) first = nodeToUndo.next;
                nodeToUndo.next.previous = nodeToUndo.previous;
            }
            nodeToUndo = nodeToUndo.nextToUndo;
        }
    }

    public void print(){
        Node<T> current = first;
        while(current != null){
            System.out.println(current.elem);
            current = current.next;
        }
    }

    private static class Node<T> {
        T elem;
        Node<T> next,
                previous,
                nextToUndo;

        public Node(T elem, Node<T> next, Node<T> previous, Node<T> nextToUndo){
            this.elem = elem;
            this.next = next;
            this.previous = previous;
            this.nextToUndo = nextToUndo;
        }
    }
/* Para testear el ejercicio 
    public static void main(String args[]){
        SortedList<String> list = new SortedListImpl<String>(new Comparator<String>(){
            public int compare(String o1, String o2){
                return o1.compareTo(o2);
            }
        });

        list.add("C"); list.add("A"); list.add("D"); list.add("B");
        list.print();
        list.undo();
        list.print();
        list.undo();
        list.undo();
        list.print();

    }
*/
}