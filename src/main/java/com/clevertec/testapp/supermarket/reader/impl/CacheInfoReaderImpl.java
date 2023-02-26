package com.clevertec.testapp.supermarket.reader.impl;

import com.clevertec.testapp.supermarket.reader.Reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CacheInfoReaderImpl implements Reader {
    @Override
    public String [] read() {
        String[] data = new String[2];
        try (BufferedReader reader = new BufferedReader(
                new FileReader("src\\main\\resources\\application.txt"));){
            String className = reader.readLine();
            String capacity = reader.readLine();
            data[0] = className;
            data[1] = capacity;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
