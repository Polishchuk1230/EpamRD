package com.epam.rd.container;

import java.util.*;
import java.util.function.Predicate;

public class ListImpl<E> implements List<E> {
    private Object[] items = new Object[10];
    private int size;

    private void notNullValidation(Object obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void extendArray(int newLength) {
        items = Arrays.copyOf(items, newLength);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (E item : this) {
            if (item.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, size);
    }

    @Override
    public <T> T[] toArray(T[] array) {
        notNullValidation(array);

        if (array.length > size) {
            array[size] = null;
        } else if (array.length < size) {
            return (T[]) Arrays.copyOf(items, size, array.getClass());
        }

        System.arraycopy(items, 0, array, 0, size);
        return array;
    }

    @Override
    public boolean add(E object) {
        notNullValidation(object);

        if (size >= items.length) {
            extendArray(size * 2 + 1);
        }
        items[size++] = object;
        return true;
    }

    @Override
    public boolean remove(Object object) {
        Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            if ( iterator.next().equals(object) ) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object o : collection) {
            if (!this.contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        notNullValidation(collection);
        if (collection.size() == 0) {
            return false;
        }

        if (collection.size() + size > items.length) {
            extendArray((collection.size() + size) * 2);
        }

        System.arraycopy(collection.toArray(), 0, items, size, collection.size());
        size += collection.size();

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        if (index != size) {
            validateIndex(index);
        }
        notNullValidation(collection);
        if (collection.size() == 0) {
            return false;
        }

        Object[] temp = collection.size() + size > items.length ?
                new Object[(collection.size() + size) * 2] :
                new Object[items.length];

        System.arraycopy(items, 0, temp, 0, index);
        System.arraycopy(collection.toArray(), 0, temp, index, collection.size());
        System.arraycopy(items, index, temp, collection.size() + index, size - index);

        items = temp;
        size += collection.size();

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        notNullValidation(collection);
        boolean result = false;

        for (Object o : collection) {
            if (this.remove(o)) {
                result = true;
            }
        }

        return result;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        notNullValidation(collection);
        boolean wasChanged = false;

        for (int i = 0; i < size; i++) {
            if (collection.contains(items[i])) {
                continue;
            }
            this.remove(i--);
            wasChanged = true;
        }

        return wasChanged;
    }

    @Override
    public void clear() {
        size = 0;
        items = new Object[10];
    }

    @Override
    public E get(int index) {
        validateIndex(index);
        return (E) items[index];
    }

    @Override
    public E set(int index, E element) {
        validateIndex(index);
        notNullValidation(element);

        E result = (E) items[index];
        items[index] = element;

        return result;
    }

    @Override
    public void add(int index, E element) {
        notNullValidation(element);
        if (index != size) {
            validateIndex(index);
        }
        if (size >= items.length) {
            extendArray(size * 2 + 1);
        }

        for (int i = ++size; i > index;) {
            items[i] = items[--i];
        }
        items[index] = element;
    }

    @Override
    public E remove(int index) {
        validateIndex(index);
        E result = (E) items[index];

        size--;
        while (index < size) {
            items[index] = items[++index];
        }
        return result;
    }

    @Override
    public int indexOf(Object obj) {
        for (int i = 0; i < size; i++) {
            if (items[i].equals(obj)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object object) {
        int i = size;
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
        private int current;
        private int previous = -1;
        private Predicate<E> predicate;

        public IteratorImpl() {
            this.predicate = item -> true;
        }
        public IteratorImpl(Predicate<E> predicate) {
            this.predicate = predicate;
        }

        @Override
        public boolean hasNext() {
            while (current < size) {
                if (predicate.test( (E) items[current] )) {
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
            previous = current;
            return (E) items[current++];
        }

        @Override
        public void remove() {
            if (previous == -1) {
                throw new IllegalStateException();
            }

            ListImpl.this.remove( previous );
            previous = -1;
            current--;
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
