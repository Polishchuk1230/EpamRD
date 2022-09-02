package com.epam.rd.dao.impl;

import com.epam.rd.dao.IOrderDao;
import com.epam.rd.entity.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Optional;

public class OrderDao implements IOrderDao {
    private TreeMap<LocalDateTime, Order> map = new TreeMap<>();

    @Override
    public boolean insert(Order order) {
        map.put(order.getTimePoint(), order);
        return true;
    }

    @Override
    public Order findByTime(LocalDateTime time) {
        return Optional.ofNullable(map.ceilingEntry(time))
                .map(Map.Entry::getValue)
                .orElse(null);
    }

    @Override
    public List<Order> findByPeriod(LocalDateTime from, LocalDateTime to) {
        return new ArrayList<>(map.subMap(from, to).values());
    }
}
