package com.example.practicafinalalgoritmia.EDyAII;



public class UnsortedLinkedListSet<E> implements Set<E> {

    public UnsortedLinkedListSet(){

    }
    public boolean add(E elem) {
        boolean find = contains(elem);
        if(!find){

        }

        return false;
    }

    public boolean contains(E elem) {
        return false;
    }

    public boolean remove(E elem) {
        return false;
    }

    public boolean isEmpty() {
        return false;
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}

