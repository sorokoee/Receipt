package com.clevertec.testapp.supermarket.format;

import com.clevertec.testapp.supermarket.entity.Receipt;
import com.clevertec.testapp.supermarket.entity.ReceiptItem;

import java.text.SimpleDateFormat;

// представление чека в виде строки (текста) - для вывода в консоль, файл и пр.
public class DefaultReceiptFormat implements ReceiptStringFormat {

    private static final String $ = "$";
    private static final String LINE_DELIMITTER = "-----------------------------------------------------\n";

    @Override
    public String format(Receipt receipt) {

        StringBuilder builder = new StringBuilder();

        String itemFormat = "%-5s %-20.20s %-20.20s %-10s";
        String totalFormat = "%-42s %10s";

        builder.append(LINE_DELIMITTER);

        builder.append("CASH RECEIPT");
        builder.append(System.getProperty("line.separator"));

        builder.append(receipt.getSupermarket().getName());
        builder.append(System.getProperty("line.separator"));

        builder.append(receipt.getSupermarket().getAddress());
        builder.append(System.getProperty("line.separator"));

        builder.append("Tel: " + receipt.getSupermarket().getPhone());
        builder.append(System.getProperty("line.separator"));

        builder.append("Cashier: " + receipt.getCashier().getName() + "\t" + "Date & time: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(receipt.getCreationDate()));
        builder.append(System.getProperty("line.separator"));


        builder.append(LINE_DELIMITTER);
        builder.append(String.format(itemFormat, "QTY", "DESC", "PRICE", "TOTAL"));
        builder.append(System.getProperty("line.separator"));


        for (ReceiptItem receiptItem : receipt.getReceiptItemList()) {

            builder.append(String.format(
                    itemFormat,
                    receiptItem.getQuantity(),
                    receiptItem.getProduct().getName() + receiptItem.getInfo(), // если будет доп. скидка - она отобразится в чеке возле названия товара
                    $ + receiptItem.getProduct().getPrice(),
                    $ + receiptItem.getSum()
            ));
            builder.append(System.getProperty("line.separator"));

        }
        builder.append(LINE_DELIMITTER);

        builder.append(String.format(
                totalFormat,
                "TAXABLE TOT.",
                $ + receipt.getPriceWithoutVat()));
        builder.append(System.getProperty("line.separator"));


        if (receipt.getVat() != null && receipt.getVat() > 0) {
            builder.append(String.format(
                    totalFormat,
                    "VAT " + receipt.getVat() + "%",
                    $ + receipt.getPriceWithVat()));
            builder.append(System.getProperty("line.separator"));

        }

        if (receipt.getDiscountCard() != null) {
            builder.append(String.format(
                    totalFormat,
                    "DISCOUNT:" + receipt.getDiscountCard().getName(),
                    receipt.getDiscountCard().getAmount() + "%"));
            builder.append(System.getProperty("line.separator"));

        }

        builder.append(String.format(
                totalFormat,
                "TOTAL",
                $ + receipt.getPriceTotal()));
        builder.append(System.getProperty("line.separator"));


        builder.append(LINE_DELIMITTER);

        return builder.toString();
    }
}
