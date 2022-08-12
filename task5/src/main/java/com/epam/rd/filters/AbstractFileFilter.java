package com.epam.rd.filters;

import com.epam.rd.dto.FilterRequest;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Represents an abstract partly realized class of a file filter.
 */
public abstract class AbstractFileFilter {
    private AbstractFileFilter nextFilter;

    public AbstractFileFilter(AbstractFileFilter nextFilter) {
        this.nextFilter = nextFilter;
    }

    /**
     * This method calls the next Filter in a chain.
     * @param files
     */
    private void nextFilter(List<File> files, FilterRequest filterRequest) {
        if (nextFilter != null) {
            nextFilter.filter(files, filterRequest);
        }
    }

    /**
     * Represents main logic of this class - filtration
     * @param files
     * @param filterRequest
     */
    public void filter(List<File> files, FilterRequest filterRequest) {
        if (files.isEmpty()) {
            return;
        }

        Optional.ofNullable(getPredicate(files, filterRequest))
                .ifPresent(files::removeIf);

        nextFilter(files, filterRequest);
    }

    public abstract Predicate<File> getPredicate(List<File> files, FilterRequest filterRequest);
}