package com.epam.rd.dao.impl;

import com.epam.rd.dao.IProductDao;
import com.epam.rd.dto.ProductsFilterDto;
import com.epam.rd.entity.Product;
import com.epam.rd.entity.Supplier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ProductDao implements IProductDao {
    public static Product mapProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product(
                resultSet.getInt("products.id"),
                resultSet.getString("products.name"),
                resultSet.getString("products.category"),
                resultSet.getBigDecimal("products.price"),
                resultSet.getString("products.description"));

        product.setImage(resultSet.getString("products.image"));

        Supplier supplier = new Supplier(
                resultSet.getInt("suppliers.id"),
                resultSet.getString("suppliers.name"));
        product.setSupplier(supplier);

        return product;
    }

    @Override
    public List<Product> findAllByCriteria(ProductsFilterDto criteria) {
        String query = collectFilteredQuery(criteria);
        List<String> stringParameters = criteria.getStringFilters().values().stream()
                .flatMap(Arrays::stream)
                .filter(str -> !str.isEmpty())
                .collect(Collectors.toList());

        System.out.println(query);
        System.out.println(stringParameters);

        FunctionThrowsSQLExc<PreparedStatement, List<Product>> findAllByCriteriaFunction = statement -> {
            for (int i = 1; i <= stringParameters.size(); i++) {
                statement.setString(i, stringParameters.get(i-1));
            }

            statement.execute();
            ResultSet resultSet = statement.getResultSet();

            List<Product> result = new ArrayList<>();
            while (resultSet != null && resultSet.next()) {
                result.add(
                        mapProduct(resultSet));
            }

            return result;
        };

        return providePrepStatementForFunction(findAllByCriteriaFunction, query);
    }

    /**
     * This method collects filtered SQL Query for PreparedStatement depending on criteria of provided ProductsFilterDto.
     *
     * Pattern: "SELECT * FROM products WHERE price >= 100 AND price <= 200 AND name IN (?, ?) AND category IN (?) AND supplier IN (?, ?, ?)"
     * @param criteria ProductsFilterDto object which encapsulates all filtration criteria inside
     * @return
     */
    private String collectFilteredQuery(ProductsFilterDto criteria) {
        String stringCriteria = criteria.getStringFilters().entrySet().stream()
                .filter(entry -> entry.getValue().length > 0 && !entry.getValue()[0].isEmpty())
                .map(entry -> " " + normalizeParameterName(entry.getKey()) + " IN (" +
                        Arrays.stream(entry.getValue())
                                .map(e -> "?")
                                .collect(Collectors.joining(", ")) + ")")
                .collect(Collectors.joining(" AND"));

        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM (products JOIN suppliers ON products.supplier_id = suppliers.id)");
        queryBuilder.append(" WHERE price >= ").append(Math.max(0, criteria.getMinPrice()));
        if (criteria.getMaxPrice() > 0) {
            queryBuilder.append(" AND price <= ").append(criteria.getMaxPrice());
        }
        if (!stringCriteria.isEmpty()) {
            queryBuilder.append(" AND").append(stringCriteria);
        }

        return queryBuilder.toString();
    }

    private String normalizeParameterName(String paramName) {
        Map<String, String> unclearParams = new HashMap<>(Map.of(
                "name", "products.name",
                "supplier", "suppliers.name"
        ));

        return unclearParams.getOrDefault(paramName, paramName);
    }
}
