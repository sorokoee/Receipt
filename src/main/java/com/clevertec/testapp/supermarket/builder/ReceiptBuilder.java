package com.clevertec.testapp.supermarket.builder;

import com.clevertec.testapp.supermarket.entity.*;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
// билдер для создания чека, в зависимости от данных чека
// также автоматиески считает все суммы исходя из скидок, ндс и пр.
public class ReceiptBuilder implements IReceiptBuilder {

    private Receipt receipt; // текущий чек, который считаем

    public ReceiptBuilder add(Supermarket supermarket) {
        this.receipt.setSupermarket(supermarket);
        return this;
    }

    public ReceiptBuilder add(Cashier cashier) {
        this.receipt.setCashier(cashier);
        return this;
    }

    public ReceiptBuilder add(Integer vat) {
        this.receipt.setVat(vat);
        return this;
    }

    public ReceiptBuilder add(DiscountCard discountCard) {
        this.receipt.setDiscountCard(discountCard);
        return this;
    }

    public ReceiptBuilder add(ReceiptItem receiptItem) {
        this.receipt.getReceiptItemList().add(receiptItem);
        return this;
    }

    public ReceiptBuilder add(List<ReceiptItem> receiptItemList) {
        this.receipt.getReceiptItemList().addAll(receiptItemList);
        return this;
    }

    public ReceiptBuilder() {
        receipt = new Receipt();
        receipt.setCreationDate(new Date()); // текущая дата и время
    }

    // возвращает готовый объект чека, готовый к печати
    public Receipt build() {
        calcTotalPrices(); // заполняем все поля с ценами
        return receipt;
    }


    // посчитать все суммы с учетом налогов, скидок
    private void calcTotalPrices() {
        // проверка параметров
        Assert.notNull(receipt.getSupermarket(), "Receipt - supermarket can not be null");
        Assert.notNull(receipt.getCashier(), "Receipt - cashier can not be null");
        Assert.notEmpty(receipt.getReceiptItemList(), "Receipt - receiptItemList can not be empty");

        if (receipt.getDiscountCard() != null) {
            Assert.isTrue(receipt.getDiscountCard().getAmount() < 100 && receipt.getDiscountCard().getId() > 0, "discountCard amount must be <100 and > 0");
        }

        // общая сумма без учета налогов и скидки
        Double priceWithoutVat = 0.0;
        for (ReceiptItem receiptItem : receipt.getReceiptItemList()) {

            Double tmpPrice = receiptItem.getProduct().getPrice() * receiptItem.getQuantity();

            // если есть акционные товары в нужном количестве - уменьшаем сумму в строке чека
            if (checkPromotion(receiptItem)) {
                tmpPrice = tmpPrice - tmpPrice * ((double) PROMOTE_DISCOUNT / 100); // скидка на акционные товары
                receiptItem.setInfo("(-" + PROMOTE_DISCOUNT + "%)"); // отражаем в чеке, что сработала доп. скидка
            }

            receiptItem.setSum(tmpPrice);
            priceWithoutVat += tmpPrice;
        }

        receipt.setPriceWithoutVat(priceWithoutVat);

        // сумма с учетом налога
        Double priceWithVat = 0.0;

        if (receipt.getVat() != null && receipt.getVat() > 0) {
            priceWithVat = priceWithoutVat + priceWithoutVat * receipt.getVat() / 100;
        } else {
            priceWithVat = priceWithoutVat;
        }
        receipt.setPriceWithVat(priceWithVat);

        Double priceTotal = 0.0;

        // итоговая сумма чека
        if (receipt.getDiscountCard() != null && receipt.getDiscountCard().getAmount() > 0) { // если был указан купон скидки (карта)
            priceTotal = priceWithVat - priceWithVat * ((double) receipt.getDiscountCard().getAmount() / 100); // double нужен, чтобы после деления получилось дробное число, а не 0
        } else {
            priceTotal = priceWithVat;
        }

        receipt.setPriceTotal(priceTotal);

    }


    // проверяет на доп. скидку, если акционных товаров больше PROMOTE_MIN_COUNT
    private boolean checkPromotion(ReceiptItem receiptItem) {
        if (receiptItem.getProduct().getPromotion()) { // если товар акционный
            if (receiptItem.getQuantity() >= PROMOTE_MIN_COUNT) { // если товаров достаточно для доп. скидки
                return true;
            }
        }

        return false;
    }


}
