package com.epam.collections;

import java.util.Objects;
import java.util.Optional;

public class CustomLinkedList<T>{
    private int size;
    private Entry<T> head;
    private Entry<T> tail;

    public int size() {
        return size;
    }

    public boolean contains(Object o) {
        var tmp = head;
        while (tmp != null){
            if (Objects.equals(o, tmp.value)){
                return true;
            }
            tmp = tmp.next;
        }
        return false;
    }

    public void add(T t) {
        var newEntry = new Entry<>(t, null, tail);
        if (tail == null){      //happens when first element is added
            head = newEntry;
        }else {
            tail.next = newEntry;
        }
        tail = newEntry;
        size++;
    }

    public boolean remove(T t) {
        var toRemove = getEntry(t);
        if (toRemove.isEmpty()){
            return false;
        }
        removeEntry(toRemove.get());
        return true;
    }

    public void clear() {
        var tmp = head;
        while (tmp != null){
            var next = tmp.next;
            tmp.next = null;
            tmp.value = null;
            tmp.prev = null;
            tmp = next;
        }
        head = null;
        tail = null;
        size = 0;
    }

    public T get(int index) {
        return getEntry(index).value;
    }

    public T set(int index, T element) {
        var item = getEntry(index);
        var oldValue = item.value;
        item.value = element;
        return oldValue;
    }

    public void add(int index, T element) {
        validate(index);
        var next = getEntry(index);
        var prev = next.prev;
        var newEntry = new Entry<>(element, next, prev);
        if (prev == null){
            head = newEntry;
        }else{
            prev.next = newEntry;
        }
        next.prev = newEntry;
        size++;
    }

    public T remove(int index) {
        var entry = getEntry(index);
        var value = entry.value;
        removeEntry(entry);
        return value;
    }

    public int indexOf(T o) {
        var tmp = head;
        int i = 0;
        while (tmp != null){
            if (Objects.equals(tmp.value, o)){
                return i;
            }
            tmp = tmp.next;
            i++;
        }
        return -1;
    }

    public int lastIndexOf(T o) {
        var tmp = tail;
        int i = 0;
        while (tmp != null){
            if (Objects.equals(tmp.value, o)){
                return size - i - 1;
            }
            tmp = tmp.prev;
            i++;
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        var tmp = head;
        while (tmp != null){
            sb.append(tmp.value);
            if (tmp.next != null){
                sb.append(", ");
            }
            tmp = tmp.next;
        }
        sb.append(']');
        return sb.toString();
    }

    private void removeEntry(Entry<T> toRemove){
        var prev = toRemove.prev;
        var next = toRemove.next;
        if (prev != null){
            prev.next = next;
        }
        if (next != null){
            next.prev = prev;
        }
        if (toRemove == head){
            head = next;
        }
        if (toRemove == tail){
            tail = prev;
        }
        toRemove.prev = null;
        toRemove.next = null;
        toRemove.value = null;
        size--;
    }

    private Entry<T> getEntry(int index) {
        validate(index);
        var tmp = head;
        for(int i = 0; i < index; i++){
            tmp = tmp.next;
        }
        return tmp;
    }

    private Optional<Entry<T>> getEntry(T t) {
        var tmp = head;
        while(tmp != null){
            if(Objects.equals(t, tmp.value)){
                return Optional.of(tmp);
            }
            tmp = tmp.next;
        }
        return Optional.empty();
    }

    private void validate(int index){
        if(index < 0){
            throw new IllegalArgumentException("Capacity should be greater or equal to zero");
        }
        if (index >= size){
            throw new IllegalArgumentException("Capacity should be smaller than collection size");
        }
    }

    private static class Entry<T>{
        T value;
        Entry<T> next;
        Entry<T> prev;

        Entry(T value, Entry<T> next, Entry<T> prev)
        {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
