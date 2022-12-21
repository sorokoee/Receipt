package com.clevertec.testapp.supermarket.data;

import com.clevertec.testapp.supermarket.dao.*;
import com.clevertec.testapp.supermarket.entity.Cashier;
import com.clevertec.testapp.supermarket.entity.DiscountCard;
import com.clevertec.testapp.supermarket.entity.Product;
import com.clevertec.testapp.supermarket.entity.Supermarket;

// тестовые данные, если не используется БД
public class TestData {

    // объекты доступа к данным
    private static SupermarketDAO supermarketDAO = SupermarketDAO.getInstance();
    private static ProductDAO productDAO = ProductDAO.getInstance();
    private static CashierDAO cashierDAO = CashierDAO.getInstance();
    private static ReceiptItemDAO receiptItemDAO = ReceiptItemDAO.getInstance();
    private static DiscountDAO discountDAO = DiscountDAO.getInstance();
    private static ReceiptDAO receiptDAO = ReceiptDAO.getInstance();


    public static void init() {

        // тестовые объекты (аналог строк в таблицах)  --------------
        Supermarket supermarket = new Supermarket(1L, "Supermarket", "1 Main str", "235-23-52");
        Cashier cashier = new Cashier(1L, "#12345");

        Product bread = new Product(1L, "bread", 5.0, false);
        Product water = new Product(2L, "water", 10.0, true); // акционный товар
        Product milk = new Product(3L, "milk", 8.0, false);
        Product banana = new Product(4L, "banana", 1.0, false);

        DiscountCard card1 = new DiscountCard(1L, "Card-50", 50);
        DiscountCard card2 = new DiscountCard(2L, "Card-10", 10);

        // добавляем тестовые объекты в хранилище (коллекции в памяти)

        supermarketDAO.add(supermarket);
        cashierDAO.add(cashier);

        // продукты
        productDAO.add(bread);
        productDAO.add(water);
        productDAO.add(milk);
        productDAO.add(banana);

        // карты скидки
        discountDAO.add(card1);
        discountDAO.add(card2);


    }


}