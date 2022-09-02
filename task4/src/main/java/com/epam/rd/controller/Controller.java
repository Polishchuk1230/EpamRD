package com.epam.rd.controller;

import com.epam.rd.command.ICommand;
import com.epam.rd.command.impl.ExitCommand;
import com.epam.rd.command.impl.CartAddCommand;
import com.epam.rd.command.impl.CartListCommand;
import com.epam.rd.command.impl.OrderCreateCommand;
import com.epam.rd.command.impl.OrderListCommand;
import com.epam.rd.command.impl.ProductAddRandomCommand;
import com.epam.rd.command.impl.ProductAddHandleCommand;
import com.epam.rd.command.impl.ProductListCommand;
import com.epam.rd.command.impl.LocaleSetCommand;

import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Controller implements IController {
    private final Pattern COMMAND_PATTERN = Pattern.compile("^(\\p{L}*(?: \\p{L}*)?)(?: [\\p{L}\\d \".,=:-]*)?$");
    private Map<String, ICommand> commands = new HashMap<>();

    public Controller(boolean randomInput) {
        commands.put("exit", new ExitCommand());
        commands.put("cart add", new CartAddCommand());
        commands.put("cart list", new CartListCommand());
        commands.put("order create", new OrderCreateCommand());
        commands.put("order list", new OrderListCommand());
        commands.put("product add", randomInput ? new ProductAddRandomCommand() : new ProductAddHandleCommand());
        commands.put("product list", new ProductListCommand());
        commands.put("locale set", new LocaleSetCommand());
    }

    public String getFullInfo() {
        return commands.values().stream()
                .map(ICommand::getInfo)
                .collect(Collectors.joining("\n\n"));
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
