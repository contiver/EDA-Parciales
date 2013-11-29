public class CircularListImpl<T> implements CircularList<T> {
    private Node<T> last, current;
    private boolean splitInvalid = true;

    public void addLast(T elem){
        if(last == null){
            last = current = new Node<T>(elem, null);
            last.next = last;
        }else{
            last.next = new Node<T>(elem,last.next);
            last = last.next;
            if(splitInvalid) current = last;
        }
    }

    public CircularListImpl(){
    }

    private CircularListImpl(Node<T> last){
        this.last = this.current = last;
    }

    public T getNext(){
        if(last == null) throw new NoSuchElementException();
        splitInvalid = false;
        current = current.next;
        return current.elem;
    }

    public void reset(){
        current = last;
    }

    public CircularList<T> split(){
        if(splitInvalid) throw new IllegalStateException();
        if(current == last) return new CircularListImpl<T>(null);

        CircularList<T> ret = new CircularListImpl<T>(last);
        Node<T> first = last.next;
        last.next = current.next;
        current.next = first;
        return ret;
    }

    private static class Node<T> {
        T elem;
        Node<T> next;

        public Node(T elem, Node<T> next){
            this.elem = elem;
            this.next = next;
        }
    }
