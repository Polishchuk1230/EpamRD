package com.epam.rd.servlet;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.context.util.BeanNames;
import com.epam.rd.dto.PaginationDto;
import com.epam.rd.dto.ProductsFilterDto;
import com.epam.rd.entity.Product;
import com.epam.rd.service.IProductService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.rd.servlet.util.Parameters.*;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private static final String ITEMS_JSP = "jsp/items.jsp";
    private static final int DEFAULT_NUM_ITEMS_PER_PAGE = 10;
    private IProductService productService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        productService = (IProductService) ApplicationContext.getInstance().getAttribute(BeanNames.PRODUCT_SERVICE);
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = productService.findAllByCriteria(collectFilterCriteria(req));

        if (req.getParameter(SORT) != null) {
            boolean isReversed = req.getParameter(REVERSED) != null &&
                    Boolean.parseBoolean(req.getParameter(REVERSED));
            sort(req.getParameter(SORT), products, isReversed);
        }

        products = paginate(products, req);

        req.setAttribute("products", products);
        req.getRequestDispatcher(ITEMS_JSP).forward(req, resp);
    }

    private static List<Product> paginate(List<Product> products, HttpServletRequest request) {
        int itemsPerPage = DEFAULT_NUM_ITEMS_PER_PAGE;
        String paramsString = request.getQueryString();
        if (request.getParameter(ITEMS_PER_PAGE) != null && !request.getParameter(ITEMS_PER_PAGE).isEmpty()) {
            itemsPerPage = Integer.parseInt(request.getParameter(ITEMS_PER_PAGE));
        }
        int page = 1;
        if (request.getParameter(PAGE) != null && !request.getParameter(PAGE).isEmpty()) {
            page = Math.max(1, Integer.parseInt(request.getParameter(PAGE)));
            paramsString = removePageParameter(paramsString);
        }
        PaginationDto pageDto = new PaginationDto();
        pageDto.setCurrentPage(page);
        pageDto.setTotalPages((int) Math.ceil((double) products.size() / itemsPerPage));
        pageDto.setUrlPrefix(request.getRequestURL().toString() + "?" + paramsString);
        request.setAttribute("pageDto", pageDto);

        return products.stream().skip((page - 1) * itemsPerPage).limit(itemsPerPage).collect(Collectors.toList());
    }

    private static String removePageParameter(String parameters) {
        if (!parameters.contains(PAGE + "=")) {
            return parameters;
        }

        return Arrays.stream(parameters.split("&"))
                .filter(param -> !param.startsWith(PAGE + "="))
                .collect(Collectors.joining("&"));
    }

    private static void sort(String parameter, List<Product> products, boolean isReversed) {
        Comparator<Product> comparator = switch (parameter) {
            case "name" -> Comparator.comparing(Product::getName);
            case "price" -> Comparator.comparing(Product::getPrice);
            default -> Comparator.comparingInt(Product::getId);
        };

        if (!isReversed) {
            products.sort(comparator);
        } else {
            products.sort(comparator.reversed());
        }
    }

    private ProductsFilterDto collectFilterCriteria(HttpServletRequest request) {
        ProductsFilterDto result = new ProductsFilterDto();

        if (request.getParameter("minPrice") != null) {
            result.setMinPrice(Double.parseDouble(request.getParameter("minPrice")));
        }
        if (request.getParameter("maxPrice") != null) {
            result.setMaxPrice(Double.parseDouble(request.getParameter("maxPrice")));
        }

        Map<String, String[]> parameterMap = request.getParameterMap();

        for (Map.Entry<String, String[]> filter : result.getStringFilters().entrySet()) {
            if (parameterMap.get(filter.getKey()) == null) {
                continue;
            }

            result.getStringFilters().put(filter.getKey(), parameterMap.get(filter.getKey()));
        }

        return result;
    }
}
