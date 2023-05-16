package com.example.practicafinalalgoritmia.EDyAII;

import java.util.Iterator;

public class UnsortedArrayMapping<K,V>{
    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
        protected class Pair {
            private K key;
            private V value;
            private Pair(K key,V value){
                this.key = key;
                this.value = value;
            }
            public K getKey(){
                return key;
            }
            public V getValue(){
                return value;
            }
        }
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
            int i = 0;
            boolean trobat = false;
            while(!trobat && i<n){
                trobat = key.equals(keys[i]);
                i++;
            }
            if(trobat){
                V prevalue = values[i-1];
                values[i-1] = value;
                return prevalue;
            }
            keys[n] = key;
            values[n] = value;
            n++;
            return null;
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
        public Iterator iterator(){
            Iterator it = new IteratorUnsortedArrayMapping();
            return it;
        }
        private class IteratorUnsortedArrayMapping implements Iterator{
            private int idxIterator;
            private IteratorUnsortedArrayMapping(){
                idxIterator = 0;
            }
            @Override
            public boolean hasNext() {
                return idxIterator < n;
            }

            @Override
            public Object next() {
                Pair p = new Pair(keys[idxIterator],values[idxIterator]);
                idxIterator++;
                return p;
            }
        }
}
