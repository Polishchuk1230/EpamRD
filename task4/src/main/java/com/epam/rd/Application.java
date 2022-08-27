package com.epam.rd;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.controller.Controller;
import com.epam.rd.controller.IController;
import com.epam.rd.pojo.GamingChair;
import com.epam.rd.pojo.RockingChair;
import com.epam.rd.util.Reflection;

import java.util.Scanner;

public class Application {
    public static void start() {
        String startInfo = "Existing commands:\n" +
                "product list\n[displays all available products]\n\n" +

                "cart add -id PRODUCT_ID -x AMOUNT" +
                " \n[adds specified product to the cart]\n\n" +

                "cart list" +
                "\n[displays the cart's content]\n\n" +

                "cart list N" +
                "\n[displays the information about the last N items in the cart]\n\n" +

                "order create" +
                "\n[purchases everything inside the bucket]\n\n" +

                "order list --date dd.MM.yyyy hh:mm:ss" +
                "\n[displays the closest order after the provided date]\n\n" +

                "order list --period dd.MM.yyyy - dd.MM.yyyy" +
                "\n[displays the information about all the orders between two date points]\n\n" +

                "exit" +
                "\n[closes the program and save data]";
        System.out.println(startInfo);
        Scanner sc = new Scanner(System.in);

        // choose random\handle way to fill parameters of new products
        System.out.println("Do you prefer to fill product parameters randomly? Type: true. Handle way otherwise.");
        boolean randomInput = sc.nextLine().equals("true");
        if (randomInput) {
            System.out.println("You have chosen random way. Use:\nproduct add -t PRODUCT_TYPE");
        } else {
            System.out.println("You have chosen handle way. Use the following templates:" +
                    "\nproduct add -t GamingChair --parameters locale=true, " + Reflection.getTypedFieldsAsString(GamingChair.class, "id") +
                    "\nproduct add -t RockingChair --parameters locale=true, " + Reflection.getTypedFieldsAsString(RockingChair.class, "id"));
        }

        IController controller = new Controller(randomInput);
        while ((boolean) ApplicationContext.getInstance().find("running") && sc.hasNextLine()) {
            System.out.println(controller.processRequest(sc.nextLine()));
        }
    }
}
