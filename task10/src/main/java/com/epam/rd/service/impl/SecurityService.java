package com.epam.rd.service.impl;

import com.epam.rd.entity.Role;
import com.epam.rd.entity.User;
import com.epam.rd.service.ISecurityService;

import java.util.List;
import java.util.Map;

public class SecurityService implements ISecurityService {
    private Map<String, List<Role>> constraints;

    public SecurityService(Map<String, List<Role>> constraints) {
        this.constraints = constraints;
    }

    @Override
    public boolean isResourcePublic(String reqURI) {
        // if the resource isn't mentioned in constraints it is open
        return constraints.keySet().stream().noneMatch(reqURI::equalsIgnoreCase);
    }

    @Override
    public boolean isAccessDenied(String reqURI, User user) {
        if (isResourcePublic(reqURI)) {
            return false;
        } else if (user == null) {
            return true;
        }

        return !constraints.get(reqURI).contains(user.getRole());
    }
}
