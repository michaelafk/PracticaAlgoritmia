package com.example.practicafinalalgoritmia.EDyAII;

import com.example.practicafinalalgoritmia.Node;

import java.util.Iterator;

public class UnsortedArrayMapping<K,V> implements Iterator,Map<K,V>{
    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
        private K[] keys;
        private V[]values;
        private int n;

        public UnsortedArrayMapping(int max) {
            n = 0;
            keys = (K[]) new Object[max];
            values = (V[]) new Object[max];
        }

        public V get(K key) {
            int i = 0;
            boolean trobat = false;
            while (trobat && i < n) {
                trobat = key.equals(keys[i]);
                i++;
            }
            if (trobat) {
                return values[i - 1];
            } else {
                return null;
            }
        }

        public V put(K key, V value) {
            if (get(key) != null) {
                keys[n] = key;
                values[n] = value;
                n++;
                return null;
            } else {
                return get(key);
            }
        }

        public V remove(K key) {
            int i = 0;
            boolean trobat = false;
            while (trobat && i < n) {
                trobat = key.equals(keys[i]);
                i++;
            }
            if(trobat){
                V valor = values[i-1];
                keys[i-1] = keys[n-1];
                values[i-1] = values[n-1];
                n--;
                return valor;
            }else{
                return null;
            }
        }

        public boolean isEmpty() {
            return n == 0;
        }

    @Override
    public boolean hasNext() {
            return i!=n;
    }

    @Override
    public  Object next() {
        Node node = new Node(keys[i],values[i]);
        i++;
        return node;
    }


    private class Node {
        private K key;
        private UnsortedLinkedList<Integer> value;

        public Node(Character key,UnsortedLinkedListSet<Integer> value){
            this.key = key;
            this.value = value;
        }
        public Character getKey(){
            return key;
        }
        public UnsortedLinkedList<Integer> getValue() {
            return value;
        }
    }



}
