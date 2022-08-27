package com.epam.rd.net.socket_controller;

import com.epam.rd.controller.IController;

public interface ISocketController extends IController {

    String processRequest(String command);
}
