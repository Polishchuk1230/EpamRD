package com.epam.rd.command.impl;

import com.epam.rd.command.ICommand;
import com.epam.rd.entity.Order;
import com.epam.rd.service.IOrderService;
import com.epam.rd.service.impl.OrderService;
import com.epam.rd.util.ApplicationContext;
import com.epam.rd.entity.OrderItem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class OrderListCommand implements ICommand {
    private final Pattern ORDER_BY_DATE_PATTERN = Pattern.compile(
            "^order list --date ?([0-9]{2}.[0-9]{2}.[0-9]{4} [0-9]{2}:[0-9]{2}:[0-9]{2})$");
    private final Pattern ORDER_BY_PERIOD_PATTERN = Pattern.compile(
            "^order list --period ?" +
                    "([0-9]{2}.[0-9]{2}.[0-9]{4} [0-9]{2}:[0-9]{2}:[0-9]{2}) ?" +
                    "- ?([0-9]{2}.[0-9]{2}.[0-9]{4} [0-9]{2}:[0-9]{2}:[0-9]{2})$");
    private IOrderService orderService = (OrderService) ApplicationContext.getInstance().find("orderService");

    @Override
    public String execute(String command) {
        Matcher orderWithDateMatcher = ORDER_BY_DATE_PATTERN.matcher(command);
        Matcher orderWithPeriodMatcher = ORDER_BY_PERIOD_PATTERN.matcher(command);

        // execute a command parametrised by --date
        if (orderWithDateMatcher.find()) {
            String date = orderWithDateMatcher.group(1);
            return findByDate(date);
        }
        // execute a command parametrised by --period
        else if (orderWithPeriodMatcher.find()) {
            String from = orderWithPeriodMatcher.group(1);
            String to = orderWithPeriodMatcher.group(2);
            return findByPeriod(from, to);
        }

        return """
                Unknown parameters for the command order list. It can accept:
                order list --date dd.MM.yyyy hh:mm:ss
                order list --period dd.MM.yyyy hh:mm:ss - dd.MM.yyyy hh:mm:ss""";
    }

    private String findByDate(String date) {
        try {
            LocalDateTime localDateTime =
                    LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm:ss"));

            Order order = orderService.findByDate(localDateTime);

            if (order == null) {
                return "there is no products.";
            }

            return "The closest order after " + date + "\n" +
                    order.getTimePoint() + "\n" +
                    order.getItems().stream()
                            .map(OrderItem::toString)
                            .collect(Collectors.joining("\n"));

        } catch (DateTimeParseException e) {
            return "Wrong --date parameter in the command order list. Appropriate format: --date dd.MM.yyyy hh:mm:ss";
        }
    }

    private String findByPeriod(String from, String to) {
        try {
            LocalDateTime fromPoint = LocalDateTime.parse(from, DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm:ss"));
            LocalDateTime toPoint = LocalDateTime.parse(to, DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm:ss"));

            List<Order> orders = orderService.findByPeriod(fromPoint, toPoint);

            return orders.stream()
                    .map(order -> order.getTimePoint() + "\n" + order.getItems().stream()
                            .map(OrderItem::toString)
                            .collect(Collectors.joining("\n")))
                    .collect(Collectors.joining("\n\n"));
        } catch (DateTimeParseException e) {
            return "Wrong --period parameter in the command order list. Appropriate format: " +
                    "--period dd.MM.yyyy hh:mm:ss - dd.MM.yyyy hh:mm:ss";
        }
    }
}
