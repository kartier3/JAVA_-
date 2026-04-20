package main.java.utils;



public class CustomLinkedList<T> {


    private Node<T> first;

    public CustomLinkedList() {
        this.first = null;
    }

    public void add(T data) {
        Node<T> newData = new Node<>(data);

        if (this.first == null) {
            this.first = newData;
        } else {
            Node<T> current = this.first;

            while (current.getNext() != null) {
                current = current.getNext();
            }

            current.setNext(newData);
        }
    }

    public int size() {
        Node<T> current = this.first;
        int count = 0;

        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    public T get(int index) {

        Node<T> current = this.first;

        if (index < 0 || index >= size()) {
            return null;
        }

        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getData();
    }

    public T remove(int index) {
        if (index < 0 || index >= size()) {
            return null;
        }

        if (index == 0) {
            T data = this.first.getData();
            this.first = this.first.getNext();
            return data;
        }

        Node<T> current = this.first;
        for (int i = 0; i < index-1; i++) {
            current = current.getNext();
        }

        Node<T> removeNext = current.getNext();
        T data = removeNext.getData();
        current.setNext(removeNext.getNext());
        return data;
    }
}
