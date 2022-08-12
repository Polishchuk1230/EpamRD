package com.epam.rd.filters;

import com.epam.rd.dto.FilterRequest;

import java.io.File;
import java.util.List;
import java.util.function.Predicate;

public class FileFilterByName extends AbstractFileFilter {

    public FileFilterByName(AbstractFileFilter nextFilter) {
        super(nextFilter);
    }

    @Override
    public Predicate<File> getPredicate(List<File> files, FilterRequest filterRequest) {
        String parameter = filterRequest.getFileName();
        if (parameter == null) {
            return null;
        }

        return file -> !file.getName().equalsIgnoreCase(parameter);
    }
}
