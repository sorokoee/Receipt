package com.clevertec.testapp.supermarket;

import com.clevertec.testapp.supermarket.builder.ReceiptBuilder;
import com.clevertec.testapp.supermarket.dao.*;
import com.clevertec.testapp.supermarket.entity.*;
import com.clevertec.testapp.supermarket.format.DefaultReceiptFormat;
import com.clevertec.testapp.supermarket.printer.ConsolePrinter;
import com.clevertec.testapp.supermarket.printer.FilePrinter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReceiptHandler {

    // объекты доступа к данным
    private static SupermarketDAO supermarketDAO = SupermarketDAO.getInstance();
    private static ProductDAO productDAO = ProductDAO.getInstance();
    private static CashierDAO cashierDAO = CashierDAO.getInstance();
    private static ReceiptItemDAO receiptItemDAO = ReceiptItemDAO.getInstance();
    private static DiscountDAO discountDAO = DiscountDAO.getInstance();
    private static ReceiptDAO receiptDAO = ReceiptDAO.getInstance();

    private static final String FILENAME = "result.txt";


    // через регулярное выражение понимает, какие параметры передали и как их обрабатывать
    public void startReceipt(String... args) {

        Pattern pattern = Pattern.compile("^[0-9]+(-[0-9]+)"); // цифры-дефис-цифры
        Matcher matcher = pattern.matcher(args[0]); // по первому параметру определяем был передан файл или сразу значение товара для чека
        boolean productFound = matcher.find(); // подходит паттерн или нет

        String[] params;

        // первым параметром передали продукт - значит берем значения не из файла, а из параметров
        if (productFound) {
            params = args;
        } else { // передали название файла, считываем параметры из него
            params = readFile(args[0]);
        }

        createReceipt(params);

    }

    private void createReceipt(String... params) {
        Supermarket supermarket = supermarketDAO.findById(1L);
        Cashier cashier = cashierDAO.findById(1L);

        ReceiptBuilder receiptBuilder = new ReceiptBuilder();
        receiptBuilder.add(supermarket).add(cashier);

        // данные чека берутся из параметров запуска приложения

        for (String param : params) {

            if (param.contains("card")) {
                System.out.println("Применяем скидку card = " + param);

                List<DiscountCard> cardList = DiscountDAO.getInstance().find(param);

                if (!cardList.isEmpty()) {
                    receiptBuilder.add(cardList.get(0)); // берем первый элемент
                } else {
                    System.err.println("card not found " + param);
                }
            } else { // добавляем товар в чек
                String[] values = param.split("-");
                Long id = Long.valueOf(values[0]); // id товара
                Integer quantity = Integer.valueOf(values[1]); // кол-во для добавления в чек
                try {
                    Product product = productDAO.findById(id);
                    System.out.println("Добавляем товар " + product.getName() + " в чек в кол-ве " + quantity);

                    ReceiptItem receiptItem = new ReceiptItem();
                    receiptItem.setProduct(product);
                    receiptItem.setQuantity(quantity);

                    receiptBuilder.add(receiptItem);

                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        receiptBuilder.add(15); // ндс

        Receipt receipt = receiptBuilder.build(); // создаем чек на основе переданных данных

        // текстовое представление чека
        DefaultReceiptFormat receiptFormat1 = new DefaultReceiptFormat();

        // печать чека в консоль и файл (паттерн декоратор)
        FilePrinter filePrinter = new FilePrinter(new ConsolePrinter(), FILENAME);
        filePrinter.print(receiptFormat1.format(receipt));

    }


    private String[] readFile(String fileName) {

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            return line.split(" ");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

