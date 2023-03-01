package com.clevertec.testapp.supermarket;

import com.clevertec.testapp.supermarket.data.TestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;

@SpringBootApplication
public class ReceiptApplication implements CommandLineRunner {

    private static ApplicationContext context;

    @Autowired
    public void context(ApplicationContext context) {
        this.context = context;
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        SpringApplication.run(ReceiptApplication.class, args);
    }

    @Override
    public void run(String... args) {
        TestData.init();
        if (args.length > 0) {
            new ReceiptHandler().startReceipt(args);
        }
    }
}
