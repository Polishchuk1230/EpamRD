package com.epam.rd;

import com.epam.rd.dao.IProductDao;
import com.epam.rd.dao.impl.ProductDao;
import com.epam.rd.dao.util.ConnectionPool;
import com.epam.rd.dto.ProductsFilterDto;
import com.epam.rd.entity.Product;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MySQLContainer;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ProductsFilterTest {

    @ClassRule
    public static MySQLContainer container = new MySQLContainer("mysql:8.0.26");

    @BeforeClass
    public static void init() {
        System.out.println(container.getJdbcUrl());
        ConnectionPool.setUpCustomConfiguration(container.getJdbcUrl(), container.getUsername(), container.getPassword());

        String createSuppliersTableQuery = """
                CREATE TABLE suppliers (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(50)
                );
                """;

        String createProductsTableQuery = """
                CREATE TABLE products (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(50),
                    supplier_id INT,
                    category VARCHAR(50),
                    price FLOAT,
                    description VARCHAR(500),
                    image VARCHAR(100),
                                
                    CONSTRAINT suppliers_fk FOREIGN KEY (supplier_id) REFERENCES suppliers (id) ON DELETE CASCADE
                );
                """;

        String addSuppliersQuery = """
                INSERT INTO suppliers VALUES (1, 'PIXAR'), (2, 'Paramount Pictures');
                """;

        String addProductsQuery = """
                INSERT INTO products (id, name, supplier_id, category, price, description) VALUES
                    (1, 'Toy Story', 1, 'CD', 22.22, 'A movie about toys'),
                    (2, 'Toy Story 2', 1, 'DVD', 22.99, 'A sequel for a movie about toys'),
                    (3, 'Terminator 2', 2, 'CD', 49.11, 'About a time travelling machine');
                """;

        try (Statement statement = ConnectionPool.getConnection().createStatement()) {
            statement.executeUpdate(createSuppliersTableQuery);
            statement.executeUpdate(createProductsTableQuery);
            statement.executeUpdate(addSuppliersQuery);
            statement.executeUpdate(addProductsQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void filterByCategoryAndSupplierTest() {
        ProductsFilterDto filtrationCriteria = new ProductsFilterDto();
        filtrationCriteria.getStringFilters().put("category", new String[] { "DVD" });
        filtrationCriteria.getStringFilters().put("supplier", new String[] { "PIXAR" });

        IProductDao productDao = new ProductDao();
        List<Product> products = productDao.findAllByCriteria(filtrationCriteria);

        Assert.assertTrue(products.size() > 0);
        Assert.assertTrue(
                products.stream().allMatch(product ->
                        product.getCategory().equalsIgnoreCase("DVD") &&
                        product.getSupplier().getName().equalsIgnoreCase("PIXAR")));
    }

    @Test
    public void filterByCategoryTest() {
        ProductsFilterDto filtrationCriteria = new ProductsFilterDto();
        filtrationCriteria.getStringFilters().put("supplier", new String[] { "Paramount Pictures" });

        IProductDao productDao = new ProductDao();
        List<Product> products = productDao.findAllByCriteria(filtrationCriteria);

        Assert.assertTrue(products.size() > 0);
        Assert.assertTrue(
                products.stream().allMatch(product ->
                        product.getSupplier().getName().equalsIgnoreCase("Paramount Pictures")));
    }

    @Test
    public void filterByMinAndMaxPriceTest() {
        ProductsFilterDto filtrationCriteria = new ProductsFilterDto(30, 50);

        IProductDao productDao = new ProductDao();
        List<Product> products = productDao.findAllByCriteria(filtrationCriteria);

        Assert.assertTrue(products.size() > 0);
        Assert.assertTrue(
                products.stream().allMatch(product ->
                        product.getPrice().doubleValue() >= 30 && product.getPrice().doubleValue() <= 50));
    }
}
