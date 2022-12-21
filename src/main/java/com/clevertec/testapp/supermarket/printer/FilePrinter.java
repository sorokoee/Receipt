package com.clevertec.testapp.supermarket.printer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// печатает чек в файл
public class FilePrinter implements IReceiptPrinter {

    private IReceiptPrinter receiptPrinter;
    private String fileName;

    public FilePrinter(IReceiptPrinter receiptPrinter, String fileName) {
        this(fileName);
        this.receiptPrinter = receiptPrinter;
    }

    public FilePrinter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void print(String str) {
        if (receiptPrinter != null) {
            receiptPrinter.print(str);
        }

        saveToFile(str);
    }

    private void saveToFile(String str) {
        System.out.println("Печатаю чек в файл: " + fileName);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(str);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}

