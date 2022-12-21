package com.clevertec.testapp.supermarket.dao;

import com.clevertec.testapp.supermarket.entity.Receipt;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReceiptDAO implements DAO<Receipt> {

    private static ReceiptDAO instance;

    public static ReceiptDAO getInstance() {
        if (instance == null) {
            instance = new ReceiptDAO();
        }
        return instance;
    }

    private static HashMap<Long, Receipt> map = new HashMap<>();

    @Override
    public boolean add(Receipt obj) {
        map.put(obj.getId(), obj);
        return true;
    }

    @Override
    public boolean update(Receipt obj) {
        map.put(obj.getId(), obj); // просто по ключу заменяет объект в коллекции
        return true;
    }

    @Override
    public boolean delete(Long id) throws IllegalArgumentException{
        Receipt deletedObj = map.remove(id);
        Assert.notNull(deletedObj, "receipt id = " + id + " not found");
        return true;
    }

    @Override
    public Receipt findById(Long id) throws IllegalArgumentException{
        Receipt findedObj = map.get(id);
        Assert.notNull(findedObj, "receipt id = " + id + " not found");
        return findedObj;
    }

    @Override
    public List<Receipt> findAll() {
        return new ArrayList<Receipt>(map.values());
    }

    @Override
    public List<Receipt> find(String text) {
        List<Receipt> list = new ArrayList<>();
        for (Receipt obj : map.values()) {
            if (obj.getSupermarket().getName().contains(text)) {
                list.add(obj);
            }
        }

        return list;
    }
}

