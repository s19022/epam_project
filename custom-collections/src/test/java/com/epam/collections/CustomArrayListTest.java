package com.epam.collections;

import static org.junit.Assert.*;

public class CustomArrayListTest {

    @org.junit.Test
    public void contains() {
        var intList = new CustomArrayList<Integer>();
        intList.add(5);
        intList.add(5);
        intList.add(7);
        assertTrue(intList.contains(5));
        assertTrue(intList.contains(7));
        assertFalse(intList.contains(8));
    }

    @org.junit.Test
    public void testEnsureCapacity() {
        var intList = new CustomArrayList<Integer>();
        for(int i = 0; i < 20; i++){
            System.out.println(intList);
            intList.add(i);
        }
    }

    @org.junit.Test
    public void add() {
        var intList = new CustomArrayList<Integer>();
        intList.add(5);
        assertEquals(1, intList.size());
        intList.add(5);
        assertEquals(2, intList.size());
        intList.add(7);
        assertEquals(3, intList.size());
    }

    @org.junit.Test
    public void removeByElementValue() {
        var intList = new CustomArrayList<Integer>();
        intList.add(5);
        intList.add(5);
        intList.add(7);
        assertTrue(intList.remove(Integer.valueOf(7)));
        assertFalse(intList.contains(7));
        assertFalse(intList.remove(Integer.valueOf(6)));
    }

    @org.junit.Test
    public void clear() {
        var intList = new CustomArrayList<Integer>();
        intList.add(5);
        intList.add(5);
        intList.add(7);
        assertEquals(3, intList.size());
        intList.clear();
        assertEquals(0, intList.size());
    }

    @org.junit.Test
    public void get() {
        var intList = new CustomArrayList<Integer>();
        intList.add(5);
        intList.add(5);
        intList.add(7);
        assertEquals(Integer.valueOf(5), intList.get(0));
        assertEquals(Integer.valueOf(7), intList.get(2));
        assertThrows(IllegalArgumentException.class, () -> intList.get(-1));
    }

    @org.junit.Test
    public void set() {
        var intList = new CustomArrayList<Integer>();
        intList.add(5);
        intList.add(5);
        intList.add(7);
        assertThrows(IllegalArgumentException.class, () -> intList.set(-1, 5));
        assertEquals(Integer.valueOf(7), intList.set(2, 6));
        assertFalse(intList.contains(7));
        assertTrue(intList.contains(6));
    }

    @org.junit.Test
    public void addByIndex() {
        var intList = new CustomArrayList<Integer>();
        intList.add(5);
        intList.add(5);
        intList.add(7);
        intList.add(1, 6);
        assertEquals(Integer.valueOf(5), intList.get(0));
        assertEquals(Integer.valueOf(6), intList.get(1));
        assertEquals(Integer.valueOf(5), intList.get(2));
        assertEquals(Integer.valueOf(7), intList.get(3));
    }

    @org.junit.Test
    public void removeByIndex() {
        var intList = new CustomArrayList<Integer>();
        intList.add(5);
        intList.add(5);
        intList.add(7);
        assertEquals(Integer.valueOf(7), intList.remove(2));
        assertFalse(intList.contains(7));
        assertFalse(intList.remove(Integer.valueOf(6)));
    }

    @org.junit.Test
    public void indexOf() {
        var intList = new CustomArrayList<Integer>();
        intList.add(5);
        intList.add(5);
        intList.add(7);
        assertEquals(0, intList.indexOf(5));
        assertEquals(2, intList.indexOf(7));
    }

    @org.junit.Test
    public void lastIndexOf() {
        var intList = new CustomArrayList<Integer>();
        intList.add(5);
        intList.add(5);
        intList.add(7);
        assertEquals(1, intList.lastIndexOf(5));
        assertEquals(2, intList.lastIndexOf(7));
    }
}