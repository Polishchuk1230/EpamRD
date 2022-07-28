package com.epam.rd.container;

import java.util.*;

/**
 * Implementation of List interface based on two aggregated lists: an immutable one and a mutable one.
 *
 * @param <E> Any type
 */
public class DoubleBaseList<E> implements List<E> {
    private List<E> immutable;
    private List<E> mutable;

    public DoubleBaseList(List<E> immutable) {
        this(immutable, Collections.EMPTY_LIST);
    }

    public DoubleBaseList(List<E> immutable, List<E> mutable) {
        this.immutable = Optional.ofNullable(immutable).orElse(new ArrayList<>());
        this.mutable = new ArrayList<>(mutable);
    }

    private void notNullValidation(Object obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
    }

    private void validateIndex(int index) {
        validateIndex(index, false);
    }

    private void validateIndex(int index, boolean lastIndexInclusive) {
        boolean sw = lastIndexInclusive ? index > size() : index >= size();
        if (index < 0 || sw) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Returns size of this container that is equal to a sum of two aggregated collections.
     *
     * @return
     */
    @Override
    public int size() {
        return immutable.size() + mutable.size();
    }

    /**
     * Checks whether this container is empty.
     *
     * @return
     */
    @Override
    public boolean isEmpty() {
        return mutable.isEmpty() && immutable.isEmpty();
    }

    /**
     * Checks whether this container contains an object
     *
     * @param object element whose presence in this list is to be tested
     * @return boolean result
     */
    @Override
    public boolean contains(Object object) {
        notNullValidation(object);
        return immutable.contains(object) || mutable.contains(object);
    }

    /**
     * @return an instance of IteratorImpl
     * @see IteratorImpl
     */
    @Override
    public Iterator<E> iterator() {
        return new IteratorImpl();
    }

    /**
     * @return elements of this list collected into an array.
     */
    @Override
    public Object[] toArray() {
        Object[] result = immutable.size() > 0 ? Arrays.copyOf(immutable.toArray(), size()) : new Object[mutable.size()];
        if (mutable.size() > 0) {
            System.arraycopy(mutable.toArray(), 0, result, immutable.size(), mutable.size());
        }
        return result;
    }

    /**
     * @param array the array into which the elements of this list are to
     *              be stored, if it is big enough; otherwise, a new array of the
     *              same runtime type is allocated for this purpose.
     * @param <T>
     * @return an array of a required type
     */
    @Override
    public <T> T[] toArray(T[] array) {
        notNullValidation(array);

        if (array.length > size()) {
            array[size()] = null;
        } else if (array.length < size()) {
            return (T[]) Arrays.copyOf(toArray(), size(), array.getClass());
        }

        System.arraycopy(toArray(), 0, array, 0, size());
        return array;
    }

    /**
     * @param element element whose presence in this collection is to be ensured
     * @return boolean result of the operation
     */
    @Override
    public boolean add(E element) {
        notNullValidation(element);
        return mutable.add(element);
    }

    /**
     * @param object element to be removed from this list, if present
     * @return boolean result of the operation
     */
    @Override
    public boolean remove(Object object) {
        notNullValidation(object);
        if (immutable.contains(object)) {
            throw new IllegalArgumentException();
        }
        return mutable.remove(object);
    }

    /**
     * @param collection collection to be checked for containment in this list
     * @return boolean result of the checking operation
     */
    @Override
    public boolean containsAll(Collection<?> collection) {
        notNullValidation(collection);
        collection.forEach(this::notNullValidation);

        for (Object elem : collection) {
            if (!contains(elem)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param collection collection containing elements to be added to this collection
     * @return boolean result of the operation
     */
    @Override
    public boolean addAll(Collection<? extends E> collection) {
        notNullValidation(collection);
        collection.forEach(this::notNullValidation);
        return mutable.addAll(collection);
    }

    /**
     * @param index      index at which to insert the first element from the
     *                   specified collection
     * @param collection collection containing elements to be added to this list
     * @return boolean result of the operation
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        notNullValidation(collection);
        collection.forEach(this::notNullValidation);

        validateIndex(index, true);
        if (index < immutable.size()) {
            throw new IllegalArgumentException();
        }

        return mutable.addAll(index - immutable.size(), collection);
    }

    /**
     * @param collection collection containing elements to be removed from this list
     * @return boolean result of the operation
     */
    @Override
    public boolean removeAll(Collection<?> collection) {
        notNullValidation(collection);
        collection.forEach(this::notNullValidation);
        if (collection.stream().anyMatch(immutable::contains)) {
            throw new IllegalArgumentException();
        }

        return mutable.removeAll(collection);
    }

    /**
     * @param collection collection containing elements to be retained in this list
     * @return boolean result of the operation
     */
    @Override
    public boolean retainAll(Collection<?> collection) {
        notNullValidation(collection);
        collection.forEach(this::notNullValidation);
        if (!collection.containsAll(immutable)) {
            throw new IllegalArgumentException();
        }

        return mutable.retainAll(collection);
    }

    @Override
    public void clear() {
        if (immutable.size() != 0) {
            throw new IllegalArgumentException();
        }
        mutable.clear();
    }

    /**
     * @param index index of the element to return
     * @return an element at the provided index or throws IndexOutOfBoundsException
     */
    @Override
    public E get(int index) {
        validateIndex(index);
        return index < immutable.size() ? immutable.get(index) : mutable.get(index - immutable.size());
    }

    /**
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @return an element at the provided index
     * @throws NullPointerException
     * @throws IndexOutOfBoundsException
     * @throws IllegalArgumentException
     */
    @Override
    public E set(int index, E element) {
        notNullValidation(element);
        validateIndex(index);
        if (index < immutable.size()) {
            throw new IllegalArgumentException();
        }
        return mutable.set(index - immutable.size(), element);
    }

    /**
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws NullPointerException
     * @throws IndexOutOfBoundsException
     * @throws IllegalArgumentException
     */
    @Override
    public void add(int index, E element) {
        notNullValidation(element);
        validateIndex(index, true);
        if (index < immutable.size()) {
            throw new IllegalArgumentException();
        }
        mutable.add(index - immutable.size(), element);
    }

    /**
     * @param index the index of the element to be removed
     * @return an element at the index position
     * @throws IndexOutOfBoundsException
     * @throws IllegalArgumentException
     */
    @Override
    public E remove(int index) {
        validateIndex(index);
        if (index < immutable.size()) {
            throw new IllegalArgumentException();
        }
        return mutable.remove(index - immutable.size());
    }

    /**
     * @param object element to search for
     * @return first index of the searched element
     * @throws NullPointerException
     */
    @Override
    public int indexOf(Object object) {
        notNullValidation(object);
        int temp = immutable.indexOf(object);
        if (temp > -1) {
            return temp;
        }
        temp = mutable.indexOf(object);
        return temp > -1 ? temp + immutable.size() : -1;
    }

    /**
     * @param object element to search for
     * @return last index of the searched element or -1 if this list doesn't contain the element.
     */
    @Override
    public int lastIndexOf(Object object) {
        notNullValidation(object);
        int temp = mutable.lastIndexOf(object);
        return temp > -1 ? temp + immutable.size() : immutable.lastIndexOf(object);
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    /**
     * Iterator implementation.
     * Without any protection from concurrent modifications
     */
    private class IteratorImpl implements Iterator<E> {
        private Iterator<E> immutIter = immutable.iterator();
        private Iterator<E> mutIter = mutable.iterator();

        @Override
        public boolean hasNext() {
            return immutIter.hasNext() || mutIter.hasNext();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return immutIter.hasNext() ? immutIter.next() : mutIter.next();
        }
    }
}
