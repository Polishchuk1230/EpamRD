package com.epam.rd.controller;

/**
 * Represents an interface to parse users' commands into action in this shop.
 */
public interface IController {
    String processRequest(String command);
}
