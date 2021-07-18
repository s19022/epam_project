package com.epam.collections;

import org.junit.Test;

import static org.junit.Assert.*;

public class CustomHashMapTest {

    @Test
    public void size() {
        var map = new CustomHashMap<Integer, String>();
        assertEquals(0, map.size());
        map.put(1, "1");
        assertEquals(1, map.size());
        map.put(1, "1_");
        assertEquals(1, map.size());
        map.put(2, "1");
        assertEquals(2, map.size());
        map.remove(1);
        assertEquals(1, map.size());
        map.put(null, "");
        assertEquals(2, map.size());

    }

    @Test
    public void isEmpty() {
        var map = new CustomHashMap<Integer, String>();
        assertTrue(map.isEmpty());
        map.put(1, "1");
        assertFalse(map.isEmpty());
        map.remove(1);
        assertTrue(map.isEmpty());
    }

    @Test
    public void containsKey() {
        var map = new CustomHashMap<Integer, String>();
        assertFalse(map.containsKey(1));
        map.put(1, "1");
        assertTrue(map.containsKey(1));
        map.put(null, "");
        assertTrue(map.containsKey(null));

    }

    @Test
    public void containsValue() {
        var map = new CustomHashMap<Integer, String>();
        assertFalse(map.containsValue("1"));
        map.put(1, "1");
        assertTrue(map.containsValue("1"));
        map.put(null, null);
        assertTrue(map.containsValue(null));
    }

    @Test
    public void get() {
        var map = new CustomHashMap<Integer, String>();
        map.put(1, "1");
        assertEquals("1", map.get(1));
        assertNull(map.get(2));
        map.put(null, "1");
        assertEquals("1", map.get(null));
    }

    @Test
    public void put() {
        var map = new CustomHashMap<Integer, String>();
        assertEquals("10", map.put(10, "10"));
        assertEquals("10", map.put(10, "11"));
        assertTrue(map.containsKey(10));
    }

    @Test
    public void remove() {
        var map = new CustomHashMap<Integer, String>();
        map.put(10, "10");
        assertNull(map.remove(11));
        assertEquals("10", map.remove(10));
    }

    @Test
    public void clear() {
        var map = new CustomHashMap<Integer, String>();
        for (int i = 0; i < 100; i++) {
            map.put(i, i + "");
        }
        assertEquals(100, map.size());
        map.clear();
        assertEquals(0, map.size());
        assertFalse(map.containsKey(10));
    }

    @Test
    public void keySet() {
        var map = new CustomHashMap<Integer, String>();
        for (int i = 0; i < 100; i++) {
            map.put(i, i + "");
        }
        int i = 0;
        for (Integer item : map.keySet()) {
            assertEquals(Integer.valueOf(i), item);
            i++;
        }
    }

    @Test
    public void name() {
    }
}