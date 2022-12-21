package com.clevertec.testapp.supermarket.format;

import com.clevertec.testapp.supermarket.entity.Receipt;

// форматирует данные чека в нужный текстовый вид
public interface ReceiptStringFormat {
    String format(Receipt receipt);
}
