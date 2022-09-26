package com.epam.rd.service;

import com.epam.rd.entity.Subscription;

import java.util.List;

public interface ISubscriptionService {
    List<Subscription> findAll();

    boolean subscribeUser(int userId, List<Subscription> subscriptions);
}
