package com.clevertec.testapp.supermarket.dao;

import com.clevertec.testapp.supermarket.entity.Cashier;
import org.junit.Test;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CashierDAO implements DAO<Cashier> {

    private static CashierDAO instance;

    public static CashierDAO getInstance() {
        if (instance == null) {
            instance = new CashierDAO();
        }
        return instance;
    }

    private static HashMap<Long, Cashier> map = new HashMap<>();

    @Override
    public boolean add(Cashier obj) {
        map.put(obj.getId(), obj);
        return true;
    }

    @Override
    public boolean update(Cashier obj) {
        if (map.containsKey(obj.getId())){
            map.put(obj.getId(), obj);
            return true;
        }else {
            return false ;}
    }

    @Override
    public boolean delete(Long id) throws IllegalArgumentException{
        Cashier deletedObj = map.remove(id);
        Assert.notNull(deletedObj, "cashier id = " + id + " not found");
        return true;
    }

    @Override
    public Cashier findById(Long id) throws IllegalArgumentException{
        Cashier findedObj = map.get(id);
        Assert.notNull(findedObj, "cashier id = " + id + " not found");
        return findedObj;
    }

    @Override
    public List<Cashier> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public List<Cashier> find(String text) {
        List<Cashier> list = new ArrayList<>();
        for (Cashier obj : map.values()) {
            if (obj.getName().contains(text)) {
                list.add(obj);
            }
        }

        return list;
    }

}
