package com.clevertec.testapp.supermarket.dao;

import com.clevertec.testapp.supermarket.entity.DiscountCard;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DiscountDAO implements DAO<DiscountCard> {

    private static DiscountDAO instance;

    public static DiscountDAO getInstance() {
        if (instance == null) {
            instance = new DiscountDAO();
        }
        return instance;
    }

    private static HashMap<Long, DiscountCard> map = new HashMap<>();

    @Override
    public boolean add(DiscountCard obj) {
        map.put(obj.getId(), obj);
        return true;
    }

    @Override
    public boolean update(DiscountCard obj) {
        map.put(obj.getId(), obj); // просто по ключу заменяет объект в коллекции
        return true;
    }

    @Override
    public boolean delete(Long id) {
        DiscountCard deletedObj = map.remove(id);
        Assert.notNull(deletedObj, "discount id = " + id + " not found");
        return true;
    }

    @Override
    public DiscountCard findById(Long id) throws IllegalArgumentException {
        DiscountCard findedObj = map.get(id);
        Assert.notNull(findedObj, "discount id = " + id + " not found");
        return findedObj;
    }

    @Override
    public List<DiscountCard> findAll() {
        return new ArrayList<DiscountCard>(map.values());
    }

    @Override
    public List<DiscountCard> find(String text) {

        List<DiscountCard> discountCardList = new ArrayList<>();
        for (DiscountCard obj : map.values()) {
            if (obj.getName().equalsIgnoreCase(text)) {
                discountCardList.add(obj);
            }
        }

        return discountCardList;
    }
}

