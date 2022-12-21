package com.clevertec.testapp.supermarket.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Receipt  {

    private Long id;
    private Supermarket supermarket; // ссылка на магазин
    private Cashier cashier; // ссылка на кассира
    private Date creationDate; // дата и время формирования чека
    private List<ReceiptItem> receiptItemList = new ArrayList<>(); // коллекция строк из товаров и кол-ва
    private DiscountCard discountCard; // ссылка на скидочную карту
    private Integer vat; // НДС в % - прибавляется к общей сумме чека
    private Double priceTotal; // итоговая сумма чека с учетом всех скидок, налогов
    private Double priceWithoutVat; // общая сумма чека без налога
    private Double priceWithVat; // общая сумма чека с налогом

}

