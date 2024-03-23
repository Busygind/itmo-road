package org.example.task2;

import java.util.ArrayList;
import java.util.LinkedList;

public class OpenHash {
    private int capacity;

    private ArrayList<LinkedList<Integer>> hashMap;

    public OpenHash(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity should be greater than 0");
        }
        this.capacity = capacity;
        this.hashMap = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            hashMap.add(i, new LinkedList<>());
        }
    }

    public void insert(Integer element) {
        hashMap.get(countHash(element)).push(element);
    }

    public void delete(Integer element) {
        if (isInside(element)) {
            hashMap.get(countHash(element)).remove(element);
        }
    }

    public boolean isInside(int element) {
        return hashMap.get(countHash(element)).contains(element);
    }

    private int countHash(int value) {
        return value % capacity;
    }

    @Override
    public String toString() {
        return "OpenHash{" +
                "capacity=" + capacity +
                ", hashMap=" + hashMap +
                '}';
    }
}
