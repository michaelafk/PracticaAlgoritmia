package com.example.practicafinalalgoritmia;

public class Node {
    private Character key;
    private UnsortedLinkedList<Integer> value;

    public Node(Character key,UnsortedLinkedList<Integer> value){
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
