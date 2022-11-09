package com.epam.rd.servlet;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.context.util.BeanNames;
import com.epam.rd.entity.*;
import com.epam.rd.service.ICartService;
import com.google.gson.*;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

@WebServlet("/cart/*")
public class CartServlet extends HttpServlet {
    private ICartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        cartService = (ICartService) ApplicationContext.getInstance().getAttribute(BeanNames.CART_SERVICE);
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");

        Map<Product, Integer> cart = cartService.getCart(user.getId());
        req.setAttribute("cart", cart);
        req.setAttribute("fullPrice", cartService.getFullPrice(user.getId()));

        req.getRequestDispatcher("jsp/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        User user = (User) req.getSession().getAttribute("user");
        String jsonParameters = parseRequestBody(req);

        JsonElement jsonElement = JsonParser.parseString(jsonParameters);
        Map<String, JsonElement> map = jsonElement.getAsJsonObject().asMap();

        int productId = Integer.parseInt(map.get("productId").toString());
        int number = Integer.parseInt(map.get("number").toString());
        if (number < 1) {
            resp.getWriter().write(
                    new Gson().toJson(
                            Map.of(
                                    "result", Boolean.FALSE,
                                    "message", "You can't add zero of a specific product to your cart")));
            resp.flushBuffer();
            return;
        }
        boolean result = cartService.addProduct(user.getId(), productId, number);
        if (!result) {
            resp.getWriter().write(new Gson().toJson(Map.of("result", Boolean.FALSE, "message", "Something goes wrong")));
            resp.flushBuffer();
        }

        resp.getWriter().write(new Gson().toJson(Map.of("result", result, "message", "You successfully added products to your cart")));
        resp.flushBuffer();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        User user = (User) req.getSession().getAttribute("user");
        String jsonParameters = parseRequestBody(req);

        JsonElement jsonElement = JsonParser.parseString(jsonParameters);
        Map<String, JsonElement> map = jsonElement.getAsJsonObject().asMap();

        int productId = Integer.parseInt(map.get("productId").toString());
        int newNumber = Integer.parseInt(map.get("newNumber").toString());
        if (newNumber < 1) {
            resp.getWriter().write(new Gson().toJson(Map.of("result", Boolean.FALSE, "message", "You can't have zero items of a product in your cart")));
            return;
        }

        boolean result = cartService.setProductQuantity(user.getId(), productId, newNumber);
        String message = "Quantity of the item was successfully changed";

        resp.getWriter().write(new Gson().toJson(Map.of("result", Boolean.TRUE, "message", message)));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=utf-8");
        User user = (User) req.getSession().getAttribute("user");

        String path = req.getRequestURI();
        int productId = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));

        boolean result = cartService.removeProduct(user.getId(), productId);
        String message = "The product was successfully removed from the cart";

        resp.getWriter().write(new Gson().toJson(Map.of("result", result, "message", message)));
    }

    /**
     * We need parse requests' bodies because Tomcat doesn't parse it itself for application/json content-type
     * @param request
     * @return
     * @throws IOException
     */
    private static String parseRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            char[] buffer = new char[128];
            for (int count; (count = bufferedReader.read(buffer)) > 0;) {
                stringBuilder.append(buffer, 0, count);
            }
        }
        return stringBuilder.toString();
    }
}
