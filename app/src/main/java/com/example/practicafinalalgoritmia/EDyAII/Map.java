package com.example.practicafinalalgoritmia.EDyAII;

public interface Map <K,V>{

    public V put(K key, V value);
    public V get(K key);
    public V remove(K key);
    boolean isEmpty();

}