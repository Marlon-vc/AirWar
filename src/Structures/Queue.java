package Structures;

public class Queue<T> {

    private int size;
    private Node<T> front;
    private Node<T> end;

    public Queue() {
        this.size = 0;
        this.front = this.end = new Node<>();
    }

    public void enqueue(T value) {
        Node<T> newNode = new Node<>(value);
        this.end.setNext(newNode);
        this.end = newNode;
        this.size++;
    }

    public T dequeue() {
        if (this.size > 0) {
            Node<T> node = this.front.getNext();
            this.front.setNext(node.getNext());
            this.size--;
            return node.getValue();
        }
        return null;
    }

    public int getSize() {
        return this.size;
    }

    public T get(int i) {
        Node<T> temp = this.front.getNext();
        for (int j=0; j<this.size; j++){
            if (i == j){
                return temp.getValue();
            }
            temp = temp.getNext();
        }

        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("<-[");
        Node<T> temp = this.front.getNext();
        for (int i=0; i<this.size; i++) {
            builder.append(temp.getValue());
            if (i != this.size-1) {
                builder.append(", ");
            }
            temp = temp.getNext();
        }
        builder.append("]<-");
        return builder.toString();
    }

    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<>();

        queue.enqueue(5);
        queue.enqueue(12);
        queue.enqueue(7);
        queue.enqueue(21);

        System.out.println(queue);

        System.out.println(queue.dequeue());

        System.out.println(queue);
        System.out.println(queue.dequeue());

        System.out.println(queue);
        System.out.println(queue.dequeue());

        System.out.println(queue);
        System.out.println(queue.dequeue());

        System.out.println(queue);

        System.out.println(queue.dequeue());

    }
}
