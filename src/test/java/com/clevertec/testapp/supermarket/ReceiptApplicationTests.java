package com.clevertec.testapp.supermarket;

import com.clevertec.testapp.supermarket.builder.ReceiptBuilder;
import com.clevertec.testapp.supermarket.dao.*;
import com.clevertec.testapp.supermarket.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
@AutoConfigureMockMvc
class ReceiptApplicationTests {

    private static SupermarketDAO supermarketDAO = SupermarketDAO.getInstance();
    private static ProductDAO productDAO = ProductDAO.getInstance();
    private static CashierDAO cashierDAO = CashierDAO.getInstance();
    private static ReceiptItemDAO receiptItemDAO = ReceiptItemDAO.getInstance();
    private static DiscountDAO discountDAO = DiscountDAO.getInstance();
    private static ReceiptDAO receiptDAO = ReceiptDAO.getInstance();

    @Test
    void testAddSupermarket() {
        supermarketDAO.add(new Supermarket(2L, "test market", "test address", "23423234"));
        Assert.notNull(supermarketDAO.findById(2L), "market id=2 not found");
    }

    @Test
    void testAddCashier() {
        cashierDAO.add(new Cashier(2L, "test cachier"));
        Assert.notNull(cashierDAO.findById(2L), "cashier id=2 not found");
    }

    @Test
    void testAddDiscountCard() {
        discountDAO.add(new DiscountCard(2L, "test card", 50));
        Assert.notNull(discountDAO.findById(2L), "discount card id=2 not found");
    }


    @Test
    void testAddProduct() {
        productDAO.add(new Product(2L, "test product", 20.0, false));
        Assert.notNull(productDAO.findById(2L), "product card id=2 not found");
    }

    @Test
    void testDeleteProduct() {
        Assert.isTrue(productDAO.delete(2L), "delete product id=2 not found");
    }


    @Test
    void testReceipt1() {
        Supermarket supermarket = supermarketDAO.findById(1L);
        Cashier cashier = cashierDAO.findById(1L);

        Product product1 = productDAO.findById(2L); // акционный

        ReceiptItem receiptItem1 = new ReceiptItem(1L, product1, 10, "", 0.0);

        ReceiptBuilder receiptBuilder = new ReceiptBuilder();
        receiptBuilder.add(supermarket).add(cashier).add(receiptItem1);
        receiptBuilder.build();

        Assert.isTrue(receiptItem1.getSum() == 90, "must be 90");
    }

}
