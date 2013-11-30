public class ExpChecker{
    public static boolean isBalanced(char[] exp){
        CustomStack<Character> stack = new CustomStack<Character>();

        for(Character c : exp){
            if(c.equals('{') || c.equals('(') || c.equals('[')){
                stack.push(c);
            }else if(c.equals('}') || c.equals(')') || c.equals(']')){
                if(stack.isEmpty() || !stack.pop.equals(c)){
                    return false;
                }
            }
        }
        if( !stack.isEmpty()) return false;
        return true;
    }

    private static class CustomStack<T>{
        Node first;

        public boolean isEmpty(){
            return first == null;
        }

        public void push(T elem){
            first = new Node<T>(elem, first);
        }

        public T pop(){
            if(isEmpty()) throw new NoSuchElementException();
            T ret = first.value;
            first = first.tail;
            return ret;
        }
    }

    private static class Node<T>{
        T value;
        Node<T> next;

        public Node(T value, Node<T> next){
            this.value = value;
            this.next = next;
        }
    }

}
