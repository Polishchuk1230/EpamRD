package com.epam.rd.command.impl;

import com.epam.rd.command.ICommand;
import com.epam.rd.context.ApplicationContext;
import com.epam.rd.service.ILocalizationService;

public class LocaleSetCommand implements ICommand {
    private ILocalizationService localizationService = (ILocalizationService) ApplicationContext.getInstance().find("localizationService");

    @Override
    public String execute(String command) {
        localizationService.setLocale(command.substring(11));
        return "Locale has been changed.";
    }

    @Override
    public String getInfo() {
        return "locale set EN\\UA\n[sets current application locale]";
    }
}
