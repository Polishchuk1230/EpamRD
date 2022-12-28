package com.epam.rd.service;

import com.epam.rd.entity.User;

public interface ISecurityService {

    boolean isResourcePublic(String reqURI);

    boolean isAccessDenied(String reqURI, User user);
}
