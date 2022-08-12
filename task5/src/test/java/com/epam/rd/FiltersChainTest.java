package com.epam.rd;

import com.epam.rd.dto.FilterRequest;
import com.epam.rd.filters.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FiltersChainTest {

    @Test
    public void filterFilesByNameTest() {
        FakeFile fakeFile1 = new FakeFile("readme.txt", 93, 1649611211000L);
        FakeFile fakeFile2 = new FakeFile("good movie.avi", 1337152512, 1648664253033L);
        FakeFile fakeFile3 = new FakeFile("Screenshot from 2022-08-06 17-06-22.png", 64123, 1659794782010L);
        List<File> actual = new ArrayList<>(Arrays.asList(fakeFile1, fakeFile2, fakeFile3));

        // create and set FilterRequest instance
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setFileName("readme.txt");

        // filtration process
        AbstractFileFilter filterChain = new FileFilterByName(new FileFilterByExtension(
                new FileFilterBySizeDiapason(new FileFilterByChangePeriod(null))));
        filterChain.filter(actual, filterRequest);

        // check result
        List<File> expected = new ArrayList<>(Arrays.asList(fakeFile1));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void filterFilesByExtensionTest() {
        FakeFile fakeFile1 = new FakeFile("readme.txt", 93, 1649611211000L);
        FakeFile fakeFile2 = new FakeFile("good movie.avi", 1337152512, 1648664253033L);
        FakeFile fakeFile3 = new FakeFile("Screenshot from 2022-08-06 17-06-22.png", 64123, 1659794782010L);
        List<File> actual = new ArrayList<>(Arrays.asList(fakeFile1, fakeFile2, fakeFile3));

        // create and set FilterRequest instance
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setFileExtension("png");

        // filtration process
        AbstractFileFilter filterChain = new FileFilterByName(new FileFilterByExtension(
                new FileFilterBySizeDiapason(new FileFilterByChangePeriod(null))));
        filterChain.filter(actual, filterRequest);

        // check result
        List<File> expected = new ArrayList<>(Arrays.asList(fakeFile3));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void filterFilesBySizeTest() {
        FakeFile fakeFile1 = new FakeFile("readme.txt", 93, 1649611211000L);
        FakeFile fakeFile2 = new FakeFile("good movie.avi", 1337152512, 1648664253033L);
        FakeFile fakeFile3 = new FakeFile("Screenshot from 2022-08-06 17-06-22.png", 64123, 1659794782010L);
        List<File> actual = new ArrayList<>(Arrays.asList(fakeFile1, fakeFile2, fakeFile3));

        // create and set FilterRequest instance
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setFileSizeDiapason("60000 - 70000");

        // filtration process
        AbstractFileFilter filterChain = new FileFilterByName(new FileFilterByExtension(
                new FileFilterBySizeDiapason(new FileFilterByChangePeriod(null))));
        filterChain.filter(actual, filterRequest);

        // check result
        List<File> expected = new ArrayList<>(Arrays.asList(fakeFile3));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void filterFilesByChangeDateTest() {
        FakeFile fakeFile1 = new FakeFile("readme.txt", 93, 1649611211000L);
        FakeFile fakeFile2 = new FakeFile("good movie.avi", 1337152512, 1648664253033L);
        FakeFile fakeFile3 = new FakeFile("Screenshot from 2022-08-06 17-06-22.png", 64123, 1659794782010L);
        List<File> actual = new ArrayList<>(Arrays.asList(fakeFile1, fakeFile2, fakeFile3));

        // create and set FilterRequest instance
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setFileChangeDateDiapason("06.08.2022 17:00:00 - 06.08.2022 17:15:00");

        // filtration process
        AbstractFileFilter filterChain = new FileFilterByName(new FileFilterByExtension(
                new FileFilterBySizeDiapason(new FileFilterByChangePeriod(null))));
        filterChain.filter(actual, filterRequest);

        // check result
        List<File> expected = new ArrayList<>(Arrays.asList(fakeFile3));
        Assert.assertEquals(expected, actual);
    }

    private class FakeFile extends File {
        private String name;
        private long length;
        private long modified;

        public FakeFile(String name, long length, long modified) {
            super("");
            this.name = name;
            this.length = length;
            this.modified = modified;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public long length() {
            return length;
        }

        @Override
        public long lastModified() {
            return modified;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof FakeFile)) {
                return false;
            }
            FakeFile other = (FakeFile) obj;
            return this.getName().equals(other.getName()) &&
                    this.length() == other.length() &&
                    this.lastModified() == other.lastModified();
        }

        @Override
        public String toString() {
            return "FakeFile{" +
                    "name='" + name + '\'' +
                    ", length=" + length +
                    ", modified=" + modified +
                    '}';
        }
    }
}
