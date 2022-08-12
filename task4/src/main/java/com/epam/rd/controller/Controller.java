package com.epam.rd.controller;

import com.epam.rd.command.ICommand;
import com.epam.rd.command.impl.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller implements IController {
    private final Pattern COMMAND_PATTERN = Pattern.compile("^([a-zA-Z]*(?: [a-zA-Z]*)?)(?: [a-zA-Z0-9 \".,=:-]*)?$");
    private Map<String, ICommand> commands = new HashMap<>();

    public Controller(boolean randomInput) {
        commands.put("exit", new ExitCommand());
        commands.put("cart add", new CartAddCommand());
        commands.put("cart list", new CartListCommand());
        commands.put("order create", new OrderCreateCommand());
        commands.put("order list", new OrderListCommand());
        commands.put("product add", randomInput ? new ProductAddRandomCommand() : new ProductAddHandleCommand());
        commands.put("product list", new ProductListCommand());
    }

    @Override
    public String processRequest(String command) {
        Matcher matcher = COMMAND_PATTERN.matcher(command);

        ICommand commandExecutor;
        if (matcher.find() && (commandExecutor = commands.get(matcher.group(1))) != null) {
            return commandExecutor.execute(command);
        }
        return "Unknown command";
    }
}
