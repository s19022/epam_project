package com.epam.collections;

import org.junit.Test;

import static org.junit.Assert.*;

public class CustomLinkedListTest {

    @Test
    public void size() {
        var intList = new CustomLinkedList<Integer>();
        intList.add(5);
        assertEquals(1, intList.size());
        intList.add(5);
        assertEquals(2, intList.size());
        intList.add(7);
        assertEquals(3, intList.size());
    }

    @Test
    public void contains() {
        var intList = new CustomLinkedList<Integer>();
        intList.add(5);
        assertTrue(intList.contains(5));
        intList.add(5);
        assertTrue(intList.contains(5));
        intList.add(7);
        assertTrue(intList.contains(7));
        assertFalse(intList.contains(8));
    }

    @Test
    public void clear() {
        var intList = new CustomLinkedList<Integer>();
        for (int i = 0; i < 10; i++){
            intList.add(i);
            assertEquals(i + 1, intList.size());
        }
        intList.clear();
        assertEquals(0, intList.size());
        intList.add(5);
        assertEquals(1, intList.size());
        intList.add(5);
        assertEquals(2, intList.size());
        intList.add(7);
        assertEquals(3, intList.size());
    }

    @Test
    public void get() {
        var intList = new CustomLinkedList<Integer>();
        for (Integer i = 0; i < 10; i++){
            intList.add(i);
            assertEquals(i, intList.get(i));
        }
    }

    @Test
    public void removeByValue() {
        var intList = new CustomLinkedList<Integer>();
        intList.add(5);
        intList.add(5);
        intList.add(7);
        assertTrue(intList.remove(Integer.valueOf(7)));
        assertFalse(intList.contains(7));
        assertFalse(intList.remove(Integer.valueOf(6)));
        intList.add(5);
    }


    @Test
    public void set() {
        var intList = new CustomLinkedList<Integer>();
        intList.add(5);
        intList.add(5);
        intList.add(7);
        assertThrows(IllegalArgumentException.class, () -> intList.set(-1, 5));
        assertEquals(Integer.valueOf(7), intList.set(2, 6));
        assertFalse(intList.contains(7));
        assertTrue(intList.contains(6));
    }


    @Test
    public void addByIndex() {
        var intList = new CustomLinkedList<Integer>();
        intList.add(4);
        intList.add(5);
        intList.add(7);
        assertThrows(IllegalArgumentException.class, () -> intList.add(3, 8));
        intList.add(2, 8);
        assertEquals(Integer.valueOf(5), intList.get(1));
        assertEquals(Integer.valueOf(8), intList.get(2));
        assertEquals(Integer.valueOf(7), intList.get(3));

    }

    @Test
    public void removeByIndex() {
        var intList = new CustomLinkedList<Integer>();
        intList.add(5);
        intList.add(5);
        intList.add(7);
        assertEquals(Integer.valueOf(7), intList.remove(2));
        assertFalse(intList.contains(7));
        assertFalse(intList.remove(Integer.valueOf(6)));
    }

    @Test
    public void indexOf() {
        var intList = new CustomLinkedList<Integer>();
        intList.add(5);
        intList.add(5);
        intList.add(7);
        assertEquals(0, intList.indexOf(5));
        assertEquals(2, intList.indexOf(7));
        assertEquals(-1, intList.indexOf(8));
    }

    @Test
    public void lastIndexOf() {
        var intList = new CustomLinkedList<Integer>();
        intList.add(5);
        intList.add(5);
        intList.add(7);
        intList.add(5);
        assertEquals(3, intList.lastIndexOf(5));
        assertEquals(2, intList.lastIndexOf(7));
    }
}