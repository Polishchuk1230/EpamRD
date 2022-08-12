package com.epam.rd.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final Pattern PATTERN_SIZE = Pattern.compile("^([0-9]+) ?- ?([0-9]+)$");
    private static final Pattern PATTERN_CHANGE = Pattern.compile(
            "^([0-9]{2}.[0-9]{2}.[0-9]{4} [0-9]{2}:[0-9]{2}:[0-9]{2}) ?" +
                    "- ?([0-9]{2}.[0-9]{2}.[0-9]{4} [0-9]{2}:[0-9]{2}:[0-9]{2})$");

    /**
     * Pares: 06.08.2022 17:06:22 - 06.08.2022 17:06:23 ==> new long[] { 1659794782000L, 1659794783000L }
     * @param parameter
     * @return
     */
    public static long[] parseDateTimePeriod(String parameter) {
        Matcher matcher = PATTERN_CHANGE.matcher(Optional.ofNullable(parameter).orElse(""));

        try {
            if (matcher.find()) {
                return new long[] {
                        LocalDateTime.parse(matcher.group(1), DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm:ss"))
                                .toEpochSecond(ZoneOffset.of("+3")) * 1000,
                        LocalDateTime.parse(matcher.group(2), DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm:ss"))
                                .toEpochSecond(ZoneOffset.of("+3")) * 1000
                };
            }
        } catch (DateTimeParseException e) {
            // leave the result null
        }
        return null;
    }

    /**
     * Parse: 1234 - 12345 ==> new long[] { 1234L, 12345L }
     * @param parameter
     * @return
     */
    public static long[] parseSizeDiapason(String parameter) {
        Matcher matcher = PATTERN_SIZE.matcher(Optional.ofNullable(parameter).orElse(""));

        if (matcher.find()) {
            return new long[] {
                    Long.parseLong(matcher.group(1)),
                    Long.parseLong(matcher.group(2))
            };
        }

        return null;
    }
}
