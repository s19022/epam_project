package com.epam.collections;

import java.util.Arrays;
import java.util.Objects;

public class CustomArrayList<T>{
    private static final int DEFAULT_CAPACITY = 16;

    private int size;
    private Object[] elements;

    public CustomArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public CustomArrayList(int capacity) {
        if (capacity < 0){
            throw new IllegalArgumentException("Capacity should be greater or equal to zero");
        }
        elements = new Object[capacity];
    }

    public int size() {
        return size;
    }

    public boolean contains(T o) {
        return indexOf(o) >= 0;
    }

    public void add(T o) {
        ensureCapacity(size + 1);
        elements[size++] = o;
    }

    public boolean remove(T o) {
        int index = indexOf(o);
        if (index < 0){
            return false;
        }
        remove(index);
        return true;
    }

    public void clear() {
        for (int i = 0; i < size; i++){
            elements[i] = null;
        }
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        validate(index);
        return (T) elements[index];
    }

    @SuppressWarnings("unchecked")
    public T set(int index, T element) {
        validate(index);
        var old = elements[index];
        elements[index] = element;
        return (T) old;
    }

    public void add(int index, T element) {
        validate(index);
        ensureCapacity(size + 1);
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    @SuppressWarnings("unchecked")
    public T remove(int index) {
        validate(index);
        int numMoved = size - index - 1;
        System.arraycopy(elements, index + 1, elements, index, numMoved);
        size--;
        T deleted = (T) elements[size];
        elements[size] = null;
        return deleted;
    }

    public int indexOf(T o) {
        for(int i = 0; i < size; i++){
            if (Objects.equals(o, elements[i])){
                return i;
            }
        }
        return -1;
    }

    public int lastIndexOf(T o) {
        for(int i = size; i >= 0 ; i--){
            if (Objects.equals(o, elements[i])){
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for(int i = 0; i < size; i++){
            sb.append(elements[i]);
            if (i < size - 1){
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }

    private void ensureCapacity(int minCapacity){
        if (minCapacity <= elements.length){
            return;
        }
        int newCapacity = elements.length * 3 / 2 + 1;
        elements = Arrays.copyOf(elements, newCapacity);
    }

    private void validate(int index){
        if(index < 0){
            throw new IllegalArgumentException("Capacity should be greater or equal to zero");
        }
        if (index >= size){
            throw new IllegalArgumentException("Capacity should be smaller than collection size");
        }
    }
}
