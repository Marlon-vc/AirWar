package Structures;

public class LinkedList<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public LinkedList() {
        this.size = 0;
        this.head = this.tail = null;
    }

    public static void main(String[] args) {
        LinkedList<Integer> integerLinkedList = new LinkedList<>();
        integerLinkedList.add(1);
        integerLinkedList.remove(1);
    }

    public void add(T value) {
        Node<T> newNode = new Node<>(value);

        if (this.head == null) {
            this.head = this.tail = newNode;
        } else {
            this.tail.setNext(newNode);
            this.tail = newNode;
        }
        this.size++;
    }

    public void remove(T value) {

        if (this.size > 0) {

            if (value == this.head.getValue()) {
                this.head = this.head.getNext();
                this.size--;
            } else {
                Node<T> prev = this.head;
                Node<T> current = this.head.getNext();

                for (int i = 0; i < this.size - 1; i++) {
                    if (current.getValue().equals(value)) {
                        prev.setNext(current.getNext());
                        if (current == this.tail) {
                            this.tail = prev;
                        }
                        this.size--;
                        break;
                    } else {
                        prev = current;
                        current = current.getNext();
                    }
                }
            }
        }
    }

    public T get(int index) {
        if (index < 0 || index > this.size - 1) {
            return null;
        } else {

            Node<T> temp = this.head;
            for (int i = 0; i < index; i++) {
                temp = temp.getNext();
            }
            return temp.getValue();
        }
    }

    public void set(int index, T value) {
        if (index < 0 || index > this.size - 1) {
            throw new IndexOutOfBoundsException(index);
        } else {
            Node<T> temp = this.head;
            for (int i = 0; i < this.size; i++) {
                if (i == index) {
                    temp.setValue(value);
                    break;
                }
                temp = temp.getNext();
            }
        }
    }

    public void insert(int index, T value) {
        if (index < 0 || index > this.size - 1) {
            throw new IndexOutOfBoundsException(index);
        } else {
            Node<T> newNode = new Node<>(value);

            Node<T> prev = null;
            Node<T> current = this.head;

            for (int i = 0; i < this.size; i++) {
                if (i == index) {
                    if (current == head) {
                        newNode.setNext(this.head);
                        this.head = newNode;
                    } else {
                        prev.setNext(newNode);
                        newNode.setNext(current);
                    }
                    this.size++;
                    break;
                }
                prev = current;
                current = current.getNext();
            }
        }
    }

    public int getSize() {
        return this.size;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        Node<T> temp = this.head;
        while (temp != null) {
            builder.append(temp.getValue());
            temp = temp.getNext();
            if (temp != null) {
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }
}
