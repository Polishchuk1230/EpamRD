package com.epam.rd.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * List implementation that can contain only unique objects.
 * @param <E>
 */
public class SetList<E> extends ArrayList<E> {

    public SetList() {}
    public SetList(Collection<? extends E> collection) {
        super(Optional.of(collection)
                .filter(col -> col.stream().distinct().count() == col.size())
                .orElseThrow(IllegalArgumentException::new));
    }

    /**
     * Set an element at particular index and returns an element which was there before.
     * This method checks this collection would not contain a duplicate. But this method will NOT throw any exceptions
     * if we try to put the same object which there already is at that index.
     *
     * @param index index of the element to replace
     * @param element element to be stored at the specified position
     * @return
     */
    @Override
    public E set(int index, E element) {
        if (contains(element) && !get(index).equals(element)) {
            throw new IllegalArgumentException();
        }
        return super.set(index, element);
    }

    @Override
    public boolean add(E element) {
        add(size(), element);
        return true;
    }

    @Override
    public void add(int index, E element) {
        if (contains(element)) {
            throw new IllegalArgumentException();
        }
        super.add(index, element);
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return addAll(size(), collection);
    }

    /**
     * This method adds all elements into this collection if:
     * 1) all elements in a provided collection are unique
     * 2) no one element of the provided collection already presents in this collection
     *
     * @param index index at which to insert the first element from the
     *              specified collection
     * @param collection collection containing elements to be added to this list
     * @return boolean result of the operation
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        long count = collection.stream().distinct().filter(elem -> !contains(elem)).count();
        if (count != collection.size()) {
            throw new IllegalArgumentException();
        }
        return super.addAll(index, collection);
    }

    /**
     * This method applies a provided UnaryOperator to every existing object.
     * It can throw IllegalArgumentException if the operator could cause existing duplicates in the collection.
     * @param operator the operator to apply to each element
     */
    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        long count = stream().map(operator).distinct().count();
        if (count != size()) {
            throw new IllegalArgumentException();
        }
        super.replaceAll(operator);
    }
}
