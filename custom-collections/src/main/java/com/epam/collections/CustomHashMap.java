package com.epam.collections;

import java.util.*;
import java.util.stream.Collectors;

public class CustomHashMap<K, V>{
    private final int DEFAULT_CAPACITY = 16;
    private int size;
    @SuppressWarnings("unchecked")
    private final Node<K, V>[] table = (Node<K,V>[])new Node[DEFAULT_CAPACITY];

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(K key) {
        if (key == null){
            return containsNullKey();
        }
        int hash = hash(key.hashCode());
        int index = indexFor(hash, table.length);
        return getNode(index, hash, key).isPresent();
    }

    public boolean containsValue(V value) {
        return values().contains(value);
    }

    public V get(K key) {
        if (key == null){
            return getForNullKey();
        }
        int hash = hash(key.hashCode());
        int index = indexFor(hash, table.length);
        var nodeOptional = getNode(index, hash, key);
        if (nodeOptional.isEmpty()){
            return null;
        }
        return nodeOptional.get().value;
    }

    public V put(K key, V value) {
        if(key == null){
            return putForNullKey(value);
        }
        int hash = hash(key.hashCode());
        int index = indexFor(hash, table.length);
        var nodeOptional = getNode(index, hash, key);
        if (nodeOptional.isPresent()){
            var node = nodeOptional.get();
            var old = node.value;
            node.value = value;
            return old;
        }
        size++;
        return addEntry(hash, key, value, index).value;
    }

    public V remove(K key) {
        if (key == null){
            return removeForNullKey();
        }
        int hash = hash(key.hashCode());
        int index = indexFor(hash, table.length);
        var nodeOptional = getNode(index, hash, key);
        if (nodeOptional.isEmpty()){
            return null;
        }
        var node = nodeOptional.get();
        var value = node.value;
        remove(node, index);
        size--;
        return value;
    }

    public void clear() {
        for (int i = 0; i < table.length; i++){
            var tmp = table[i];
            while (tmp != null){
                var next = tmp.next;
                tmp.next = null;
                tmp.value = null;
                tmp = next;
            }
            table[i] = null;
        }
        size = 0;
    }

    public Set<K> keySet() {
        return entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    public Collection<V> values() {
        return entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    public Set<Map.Entry<K, V>> entrySet() {
        var entrySet = new HashSet<Map.Entry<K, V>>();
        for (Node<K, V> item : table){
            var tmp = item;
            while (tmp != null){
                entrySet.add(tmp);
                tmp =  tmp.next;
            }
        }
        return entrySet;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (Map.Entry<K, V> item : entrySet()){
            sb.append("{").append(item).append("}, ");
        }
        sb.delete(sb.length() - 2, sb.length()).append(']');
        return sb.toString();
    }

    private void remove(Node<K, V> node, int index){
        var head = table[index];
        Node<K,V> previous = null;
        while (head != null){
            if (node.equals(head.next)){
                previous = head.next;
                break;
            }
            head = head.next;
        }
        if (previous== null){
            table[index] = null;
        }else{
            previous.next = head.next;
        }
        node.value = null;
    }

    private V putForNullKey(V value){
        var head = table[0];
        while (head != null){
            if (head.key == null){
                return head.setValue(value);
            }
            head = head.next;
        }
        addEntry(0, null, value, 0);
        size++;
        return value;
    }

    private V getForNullKey(){
        var head = table[0];
        while (head != null){
            if (head.key == null){
                return head.value;
            }
            head = head.next;
        }
        return null;
    }

    private V removeForNullKey(){
        var head = table[0];
        while (head != null){
            if (head.key == null){
                var oldValue = head.value;
                remove(head, 0);
                return oldValue;
            }
            head = head.next;
        }
        return null;
    }

    private Node<K, V> addEntry(int hash, K key, V value, int index) {
        Node<K, V> e = table[index];
        table[index] = new Node<>(hash, key, value, e);
        return table[index];
    }

    private Optional<Node<K, V>> getNode(int index, int hash, K key){
        Node<K, V> head = table[index];
        while (head != null){
            if(head.hash == hash && Objects.equals(head.key, key)){
                return Optional.of(head);
            }
            head = head.next;
        }
        return Optional.empty();
    }

    private boolean containsNullKey(){
        var head = table[0];
        while (head != null){
            if (head.key == null){
                return true;
            }
            head = head.next;
        }
        return false;
    }

    private static int hash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    private static int indexFor(int hash, int length){
        return hash & (length - 1);
    }

    private static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> next;

        Node(int hash, K key, V value, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final K getKey() {
            return key;
        }
        public final V getValue() {
            return value;
        }
        public final String toString() {
            return key + "=" + value;
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }
    }
}
