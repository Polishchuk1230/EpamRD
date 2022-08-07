import com.epam.rd.controller.Controller;
import com.epam.rd.controller.IController;

import java.util.Scanner;

/**
 * Provides reading commands from the console
 */
public class Main {
    public static void main(String[] args) {
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
                "\n[displays the information about all the orders between two date points]";
        System.out.println(startInfo);
        IController controller = new Controller();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            System.out.println(controller.processRequest(sc.nextLine()));
        }
    }
}
