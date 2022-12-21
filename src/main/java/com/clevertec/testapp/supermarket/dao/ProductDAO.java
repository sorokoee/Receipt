package com.clevertec.testapp.supermarket.dao;

import com.clevertec.testapp.supermarket.entity.Product;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductDAO implements DAO<Product> {

    private static ProductDAO instance;

    public static ProductDAO getInstance() {
        if (instance == null) {
            instance = new ProductDAO();
        }
        return instance;
    }

    private static HashMap<Long, Product> map = new HashMap<>();

    @Override
    public boolean add(Product obj) {
        map.put(obj.getId(), obj);
        return true;
    }

    @Override
    public boolean update(Product obj) {
        map.put(obj.getId(), obj); // просто по ключу заменяет объект в коллекции
        return true;
    }

    @Override
    public boolean delete(Long id) throws IllegalArgumentException{
        Product deletedObj = map.remove(id);
        Assert.notNull(deletedObj, "product id = " + id + " not found");
        return true;
    }

    @Override
    public Product findById(Long id) throws IllegalArgumentException{
        Product findedObj =findedObj = map.get(id);
        Assert.notNull(findedObj, "product id = " + id + " not found");
        return findedObj;
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<Product>(map.values());
    }

    @Override
    public List<Product> find(String text) {
        List<Product> list = new ArrayList<>();
        for (Product obj : map.values()) {
            if (obj.getName().contains(text)) {
                list.add(obj);
            }
        }

        return list;
    }
}

