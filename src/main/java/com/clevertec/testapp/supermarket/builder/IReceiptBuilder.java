package com.clevertec.testapp.supermarket.builder;

import com.clevertec.testapp.supermarket.entity.*;

import java.util.List;

// все возможные поля для чека, которые доступны для заполнения
// поля с суммами - не даем заполнять, т.к. они высчитываются автоматически (на основании скидки, ндс и пр.)
public interface IReceiptBuilder {

    int PROMOTE_MIN_COUNT = 6; // минимальное кол-во товаров для доп. скидки
    int PROMOTE_DISCOUNT = 10; // доп. скидка в % для акционных товаров

    // все возможные поля, которые мы заполняем для чека в зависимости от условий
    IReceiptBuilder add(Supermarket supermarket); // ссылка на магазин
    IReceiptBuilder add(Cashier cashier); // ссылка на кассира
    IReceiptBuilder add(List<ReceiptItem> receiptItemList); // коллекция строк из товаров и кол-ва
    IReceiptBuilder add(DiscountCard discountCard); // ссылка на скидочную карту
    IReceiptBuilder add(Integer var); // НДС в % - прибавляется к общей сумме чека
    IReceiptBuilder add(ReceiptItem receiptItem); // строка чека с товаром и кол-вом

    Receipt build(); // создает итоговый объект

}

