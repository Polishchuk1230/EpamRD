package com.epam.rd.container;

import java.util.*;
import java.util.function.Predicate;

public class CopyOnWriteListImpl<E> implements List<E> {
    private Object[] items = new Object[0];

    private void notNullValidation(Object obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
    }

    private void validateIndex(int index) {
        validateIndex(index, false);
    }

    private void validateIndex(int index, boolean lastIndexInclusive) {
        if (index < 0 || lastIndexInclusive
                ? index > size()
                : index >= size()) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public int size() {
        return items.length;
    }

    @Override
    public boolean isEmpty() {
        return items.length == 0;
    }

    @Override
    public boolean contains(Object object) {
        for (E item : this) {
            if (item.equals(object)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, items.length);
    }

    @Override
    public <T> T[] toArray(T[] array) {
        notNullValidation(array);

        if (array.length > items.length) {
            array[items.length] = null;
        } else if (array.length < items.length) {
            return (T[]) Arrays.copyOf(items, items.length, array.getClass());
        }

        System.arraycopy(items, 0, array, 0, items.length);
        return array;
    }

    @Override
    public synchronized boolean add(E object) {
        notNullValidation(object);

        Object[] temp = new Object[items.length + 1];
        System.arraycopy(items, 0, temp, 0, items.length);
        temp[temp.length - 1] = object;
        items = temp;
        return true;
    }

    @Override
    public synchronized boolean remove(Object object) {
        notNullValidation(object);
        int index = indexOf(object);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        notNullValidation(collection);
        collection.forEach(this::notNullValidation);

        for (Object item : collection) {
            if (!this.contains(item)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public synchronized boolean addAll(Collection<? extends E> collection) {
        notNullValidation(collection);
        collection.forEach(this::notNullValidation);

        if (collection.size() == 0) {
            return false;
        }

        Object[] temp = new Object[items.length + collection.size()];
        System.arraycopy(items, 0, temp, 0, items.length);
        System.arraycopy(collection.toArray(), 0, temp, items.length, collection.size());
        items = temp;
        return true;
    }

    @Override
    public synchronized boolean addAll(int index, Collection<? extends E> collection) {
        validateIndex(index, true);
        notNullValidation(collection);
        collection.forEach(this::notNullValidation);

        if (collection.size() == 0) {
            return false;
        }

        Object[] temp = new Object[items.length + collection.size()];

        System.arraycopy(items, 0, temp, 0, index);
        System.arraycopy(collection.toArray(), 0, temp, index, collection.size());
        System.arraycopy(items, index, temp, collection.size() + index, items.length - index);

        items = temp;
        return true;
    }

    @Override
    public synchronized boolean removeAll(Collection<?> collection) {
        notNullValidation(collection);
        collection.forEach(this::notNullValidation);

        boolean result = false;

        for (Object obj : collection) {
            if (this.remove(obj)) {
                result = true;
            }
        }

        return result;
    }

    @Override
    public synchronized boolean retainAll(Collection<?> collection) {
        notNullValidation(collection);
        collection.forEach(this::notNullValidation);

        boolean isChanged = false;

        for (int i = 0; i < items.length; i++) {
            if (collection.contains(items[i])) { // <-- outOfBounds
                continue;
            }
            this.remove(i--);
            isChanged = true;
        }

        return isChanged;
    }

    @Override
    public synchronized void clear() {
        items = new Object[0];
    }

    @Override
    public E get(int index) {
        validateIndex(index);
        return (E) items[index];
    }

    @Override
    public synchronized E set(int index, E element) {
        validateIndex(index);
        notNullValidation(element);

        E result = (E) items[index];
        items[index] = element;
        items = Arrays.copyOf(items, items.length);

        return result;
    }

    @Override
    public synchronized void add(int index, E element) {
        notNullValidation(element);
        validateIndex(index, true);

        Object[] temp = new Object[items.length + 1];
        System.arraycopy(items, 0, temp, 0, index);
        System.arraycopy(items, index, temp, index + 1, items.length - index);
        temp[index] = element;
        items = temp;
    }

    @Override
    public synchronized E remove(int index) {
        validateIndex(index);
        E result = (E) items[index];

        Object[] temp = new Object[items.length - 1];
        System.arraycopy(items, 0, temp, 0, index);
        System.arraycopy(items, index + 1, temp, index, temp.length - index);
        items = temp;

        return result;
    }

    @Override
    public int indexOf(Object object) {
        for (int i = 0; i < items.length; i++) {
            if (items[i].equals(object)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object object) {
        int i = items.length;
        while (i > 0) {
            if (items[--i].equals(object)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Simple iterator
     */
    @Override
    public Iterator<E> iterator() {
        return new IteratorImpl();
    }

    /**
     * Special iterator, that iterates only by those elements, which match some condition.
     *
     * @param predicate represents a condition for matching elements
     */
    public Iterator<E> filterIterator(Predicate<E> predicate) {
        return new IteratorImpl(predicate);
    }

    private class IteratorImpl implements Iterator<E> {
        private Object[] snapshot = items;
        private int current;
        private Predicate<E> predicate;

        public IteratorImpl() {
        }

        public IteratorImpl(Predicate<E> predicate) {
            this.predicate = predicate;
        }

        @Override
        public boolean hasNext() {
            while (current < snapshot.length) {
                if (predicate == null || predicate.test((E) snapshot[current])) {
                    return true;
                }
                current++;
            }
            return false;
        }

        @Override
        public E next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return (E) snapshot[current++];
        }
    }

    //-------------------------------------------------------------Three methods below we were allowed not to implement.
    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}
