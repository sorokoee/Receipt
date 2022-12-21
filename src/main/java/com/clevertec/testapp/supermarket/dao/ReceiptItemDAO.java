package com.clevertec.testapp.supermarket.dao;

import com.clevertec.testapp.supermarket.entity.ReceiptItem;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReceiptItemDAO implements DAO<ReceiptItem> {

    private static ReceiptItemDAO instance;

    public static ReceiptItemDAO getInstance() {
        if (instance == null) {
            instance = new ReceiptItemDAO();
        }
        return instance;
    }

    private static HashMap<Long, ReceiptItem> map = new HashMap<>();

    @Override
    public boolean add(ReceiptItem obj) {
        map.put(obj.getId(), obj);
        return true;
    }

    @Override
    public boolean update(ReceiptItem obj) {
        map.put(obj.getId(), obj); // просто по ключу заменяет объект в коллекции
        return true;
    }

    @Override
    public boolean delete(Long id) throws IllegalArgumentException{
        ReceiptItem deletedObj = map.remove(id);
        Assert.notNull(deletedObj, "receiptItem id = " + id + " not found");
        return true;
    }

    @Override
    public ReceiptItem findById(Long id) throws IllegalArgumentException{
        ReceiptItem findedObj = map.get(id);
        Assert.notNull(findedObj, "receiptItem id = " + id + " not found");
        return findedObj;
    }

    @Override
    public List<ReceiptItem> findAll() {
        return new ArrayList<ReceiptItem>(map.values());
    }

    @Override
    public List<ReceiptItem> find(String text) {
        List<ReceiptItem> list = new ArrayList<>();
        for (ReceiptItem obj : map.values()) {
            if (obj.getProduct().getName().contains(text)) {
                list.add(obj);
            }
        }

        return list;
    }
}

