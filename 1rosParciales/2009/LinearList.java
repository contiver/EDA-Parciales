public class LinearList<S,T>{
    private Node<S,T> first;
    private Node<S,T> last;

    public LinearList(){
        first = last = null;
    }

    public void add(S key, T value){
        if(first == null){
            first = last = new Node(key, value);
            return;
        }
        last.next = new Node(key, value);
        last = last.next;
    }

    public T get(S key){
        Node<S,T> previous = current = first;
        while(current != null){
            if(current.key.equals(key)){
                previous.tail = current.tail;
                current.tail = first;
                first = current;
                return first.value;
            }
            previous = current;
            current = current.next;
        }
        return null;
    }

    private static class Node<S,T>{
        S key;
        T value;
        Node<S,T> next;

        public Node(S key, T value){
            this.key = key;
            this.value = value;
        }
    }
}
