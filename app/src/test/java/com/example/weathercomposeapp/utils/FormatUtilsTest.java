package com.example.weathercomposeapp.utils;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class FormatUtilsTest {

    @Test
    public void formatUtils_FormatDate_ReturnsFormattedString() {
        String expected = "Mon, Feb 12";

        int input = 1707770812;
        String result = FormatUtils.formatDate(input);

        assertEquals(expected, result);
    }
}
