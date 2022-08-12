package com.epam.rd.command.impl;

import com.epam.rd.command.ICommand;
import com.epam.rd.dao.IProductDaoFile;
import com.epam.rd.context.ApplicationContext;

public class ExitCommand implements ICommand {
    IProductDaoFile productDao = (IProductDaoFile) ApplicationContext.getInstance().find("productDao");

    @Override
    public String execute(String command) {
        // save current state of the productDao's storage
        productDao.saveData();

        // tell the application it should stop
        ApplicationContext.getInstance().put("running", false);

        // the answer to a user
        return "Ok, goodbye.";
    }
}
