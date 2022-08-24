package com.epam.rd;

import com.epam.rd.file_research.util.FileResearchMethods;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class FindRepeatingSequenceTest {

    @Test
    public void findRepeatingSequenceWhenNoRepeat() {
        byte[] bytes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        int[] actual = FileResearchMethods.findRepeatingSequence(bytes, new Random().nextInt(5) + 1);
        Assert.assertNull(actual);
    }

    @Test
    public void findRepeatingSequenceWithParticularLength() {
        byte[] bytes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 7, 8};

        int[] actual = FileResearchMethods.findRepeatingSequence(bytes, 2);
        int[] expected = {7, 9};

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void findRepeatingSequenceWithWrongLength() {
        byte[] bytes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 7, 8};

        int[] actual = FileResearchMethods.findRepeatingSequence(bytes, 3);

        Assert.assertNull(actual);
    }
}
