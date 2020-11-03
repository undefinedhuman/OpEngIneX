package de.undefinedhuman.core.utils;

import java.util.Arrays;

public class DynamicArray<T> {

    private T[] data;
    private int size = 0;

    public DynamicArray() {
        this(16);
    }

    @SuppressWarnings("unchecked")
    public DynamicArray(int size) {
        this.data = (T[]) new Object[size];
    }

    public void add(T element) {
        if(size == data.length)
            grow();
        data[size++] = element;
    }

    public void set(int index, T element) {
        if(index >= data.length)
            grow(index * 2);
        data[size = index] = element;
        size++;
    }

    public boolean contains(T element) {
        for (int i = 0; i < size; i++)
            if (data[i] == element)
                return true;
        return false;
    }

    public T get(int index) {
        return data[index];
    }

    public T remove(int index) {
        T element = data[index];
        data[index] = data[--size];
        data[size] = null;
        return element;
    }

    public void clear() {
        for(int i = 0; i < size; i++)
            data[i] = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int maxSize() {
        return data.length;
    }

    public int size() {
        return size;
    }

    private void grow() {
        grow(data.length / 3 * 2 + 1);
    }

    private void grow(int capacity) {
        T[] oldData = data;
        data = Arrays.copyOf(oldData, capacity);
    }

}
