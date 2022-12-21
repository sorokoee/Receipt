package com.clevertec.testapp.supermarket.dao;

import com.clevertec.testapp.supermarket.entity.Supermarket;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SupermarketDAO implements DAO<Supermarket> {

    private static SupermarketDAO instance;

    public static SupermarketDAO getInstance() {
        if (instance == null) {
            instance = new SupermarketDAO();
        }
        return instance;
    }

    private static HashMap<Long, Supermarket> map = new HashMap<>();

    @Override
    public boolean add(Supermarket obj) {
        map.put(obj.getId(), obj);
        return true;
    }

    @Override
    public boolean update(Supermarket obj) {
        map.put(obj.getId(), obj); // просто по ключу заменяет объект в коллекции
        return true;
    }

    @Override
    public boolean delete(Long id) throws IllegalArgumentException {
        Supermarket deletedObj = map.remove(id);
        Assert.notNull(deletedObj, "market id = " + id + " not found");
        return true;
    }

    @Override
    public Supermarket findById(Long id) throws IllegalArgumentException {
        Supermarket findedObj = map.get(id);
        Assert.notNull(findedObj, "market id = " + id + " not found");
        return findedObj;
    }

    @Override
    public List<Supermarket> findAll() {
        return new ArrayList<Supermarket>(map.values());
    }

    @Override
    public List<Supermarket> find(String text) {
        List<Supermarket> list = new ArrayList<>();
        for (Supermarket obj : map.values()) {
            if (obj.getName().contains(text)) {
                list.add(obj);
            }
        }

        return list;
    }
}
