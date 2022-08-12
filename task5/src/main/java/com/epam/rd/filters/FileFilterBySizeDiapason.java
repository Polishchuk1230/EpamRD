package com.epam.rd.filters;

import com.epam.rd.dto.FilterRequest;
import com.epam.rd.util.Parser;

import java.io.File;
import java.util.List;
import java.util.function.Predicate;

public class FileFilterBySizeDiapason extends AbstractFileFilter {

    public FileFilterBySizeDiapason(AbstractFileFilter filterChain) {
        super(filterChain);
    }

    @Override
    public Predicate<File> getPredicate(List<File> files, FilterRequest filterRequest) {
        long[] parameter = Parser.parseSizeDiapason(filterRequest.getFileSizeDiapason());
        if (parameter == null) {
            return null;
        }

        long from = parameter[0];
        long to = parameter[1];

        return file -> file.length() < from || file.length() > to;
    }
}
