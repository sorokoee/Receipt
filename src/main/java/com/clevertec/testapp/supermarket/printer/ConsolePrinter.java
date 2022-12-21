package com.clevertec.testapp.supermarket.printer;

// печатает чек в консоль
public class ConsolePrinter implements IReceiptPrinter {

    // подсчет и печать чека
    public void print(String str) {
        System.out.println("Печатаю чек в консоль");
        System.out.println(str);
    }

}

