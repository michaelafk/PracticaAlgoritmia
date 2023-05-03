package com.example.practicafinalalgoritmia.EDyAII;

public class UnsortedArraySet <E>  implements Set<E>{
    private final E[] array;
    private int n;

    public UnsortedArraySet(int max){
        n=0;
        array = (E[]) new Object[max];
    }
    @Override
    public boolean contains(E elem){
        int i =0;
        boolean isElement= false;
        while (!isElement && i<n){
            isElement= elem.equals(array[i]);
            i++;
        }
        return isElement;
    }

    @Override
    public boolean add(E elem){
        if(!contains(elem) && n < array.length){
            array[n]= elem;
            n++;
            return true;
        }else{return false;}
    }
    @Override
    public boolean remove(E elem){
        int i =0;
        boolean isElement= false;
        while (!isElement && i<n){
            isElement = elem.equals(array[i]);
            i++;
        }
        if (isElement){
            array[i-1]= array [n-1];
            n--;
        }
        return isElement;

    }
    @Override
    public boolean isEmpty(){
        return 0 == n;
    }
}
