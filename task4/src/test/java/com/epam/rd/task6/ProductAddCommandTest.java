package com.epam.rd.task6;

import com.epam.rd.controller.Controller;
import com.epam.rd.controller.IController;
import com.epam.rd.dao.IProductDaoFile;
import com.epam.rd.pojo.Product;
import com.epam.rd.pojo.RockingChair;
import com.epam.rd.context.ApplicationContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ProductAddCommandTest {

    @Test
    public void productAddHandleCommandTest() {
        IProductDaoFile productDao = (IProductDaoFile) ApplicationContext.getInstance().find("productDao");
        List<Product> expected = productDao.findAll();
        expected.add(new RockingChair("Asus super model", 2222.22, 222, 22));

        IController controller = new Controller(false);
        controller.processRequest("product add -t RockingChair --parameters назва=\"Asus super model\", вартість=2222.22, максимальна вага=222, максимальний нахил=22");

        List<Product> actual = productDao.findAll();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void productAddRandomCommandTest() {
        IProductDaoFile productDao = (IProductDaoFile) ApplicationContext.getInstance().find("productDao");
        List<Product> expected = productDao.findAll();

        IController controller = new Controller(true);
        controller.processRequest("product add -t RockingChair");

        List<Product> actual = productDao.findAll();

        Assert.assertNotEquals(expected, actual);

        Product newProduct = actual.stream()
                .filter(p -> !expected.contains(p)).findFirst().orElseThrow(AssertionError::new);
        Assert.assertTrue(newProduct.getId() > 0);
        Assert.assertTrue(newProduct.getName().startsWith("Random model #"));
    }
}
