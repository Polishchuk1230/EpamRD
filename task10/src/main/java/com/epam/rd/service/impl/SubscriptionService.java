package com.epam.rd.service.impl;

import com.epam.rd.dao.ISubscriptionDao;
import com.epam.rd.entity.Subscription;
import com.epam.rd.service.ISubscriptionService;

import java.util.List;

public class SubscriptionService implements ISubscriptionService {
    ISubscriptionDao subscriptionDao;

    public SubscriptionService(ISubscriptionDao subscriptionDao) {
        this.subscriptionDao = subscriptionDao;
    }

    @Override
    public List<Subscription> findAll() {
        return subscriptionDao.findAll();
    }

    @Override
    public boolean subscribeUser(int userId, List<Subscription> subscriptions) {
        return subscriptionDao.subscribeUser(userId, subscriptions);
    }
}
