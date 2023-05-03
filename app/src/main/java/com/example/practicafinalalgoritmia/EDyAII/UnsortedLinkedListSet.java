package com.example.practicafinalalgoritmia.EDyAII;


import java.util.Iterator;

public class UnsortedLinkedListSet<E> implements Set<E> {
    private Node first;

    public UnsortedLinkedListSet() {
        this.first = null;
    }

    public boolean add(E elem) {
        boolean find = contains(elem);
        if (!find) {
            Node n = new Node(elem,first);
            first = n;
        }

        return !find;
    }

    public boolean contains(E elem) {
         Node p= first;
         boolean trobat = false;
         while (p!= null && !trobat){
             trobat = p.item.equals(elem);
             p = p.next;
         }
         return trobat;
    }

    public boolean remove(E elem) {
        Node p = first; Node pp = null; boolean trobat = false;
        while (p != null && !trobat) {
            trobat = p.item.equals(elem);
            if (!trobat) {
                pp = p;
                p = p.next;
            }
        }
        if (trobat) {
            if (pp == null) {
                first = p.next;
            } else {
                pp.next = p.next;
            }
        }
        return trobat;
    }

    public boolean isEmpty() {
        return first == null;
    }

    private class Node {
        E item;
        Node next;
        Node prev;

        Node(Node prev, E element, Node next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
        Node( E element, Node next) {
            this.item = element;
            this.next = next;
        }

    }
    public Iterator iterator(){
        return new IteratorUnsortedLinkedListSet();
    }
    private class IteratorUnsortedLinkedListSet implements Iterator{
        private  Node indexIterator;

        private IteratorUnsortedLinkedListSet(){
            this.indexIterator = first;
        }
        @Override
        public boolean hasNext() {
            return  this.indexIterator!= null;
        }

        @Override
        public Object next() {
            E elem = indexIterator.item;
            indexIterator= indexIterator.next;

            return elem;
        }
    }
}

