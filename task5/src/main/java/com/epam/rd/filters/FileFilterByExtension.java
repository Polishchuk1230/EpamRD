package com.epam.rd.filters;

import com.epam.rd.dto.FilterRequest;

import java.io.File;
import java.util.List;
import java.util.function.Predicate;

public class FileFilterByExtension extends AbstractFileFilter {

    public FileFilterByExtension(AbstractFileFilter filterChain) {
        super(filterChain);
    }

    @Override
    public Predicate<File> getPredicate(List<File> files, FilterRequest filterRequest) {
        String parameter = filterRequest.getFileExtension();
        if (parameter == null) {
            return null;
        }

        return file -> !file.getName().endsWith("." + parameter);
    }
}
