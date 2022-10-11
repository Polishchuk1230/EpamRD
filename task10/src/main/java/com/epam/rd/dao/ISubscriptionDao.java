package com.epam.rd.dao;

import com.epam.rd.entity.Subscription;

import java.util.List;

public interface ISubscriptionDao extends IDao {

    List<Subscription> findAll();

    List<Subscription> findByUserId(int userId);

    boolean subscribeUser(int userId, List<Subscription> subscriptions);
}
