package com.clevertec.testapp.supermarket.cache.impl;

import com.clevertec.testapp.supermarket.cache.Cache;
import com.clevertec.testapp.supermarket.entity.BaseModel;


import java.util.HashMap;
import java.util.LinkedHashSet;

public class LFUCacheImpl implements Cache {
    HashMap<Long, BaseModel> casheObjects;
    HashMap<Long, Integer> counters;
    HashMap<Integer, LinkedHashSet<Object>> lists;
    int capacity;
    int min = -1;

    public LFUCacheImpl(int capacity) {
        this.capacity = capacity;
        casheObjects = new HashMap<>();
        counters = new HashMap<>();
        lists = new HashMap<>();
        lists.put(1, new LinkedHashSet<>());
    }

    @Override
    public BaseModel get(Long key) {
        if (!casheObjects.containsKey(key)) {
            return null;
        }
        BaseModel cacheObject = casheObjects.get(key);
        int count = counters.get(key);
        counters.put(key, count + 1);
        lists.get(count).remove(cacheObject);
        if (count == min && lists.get(count).size() == 0) {
            min++;
        }
        if (!lists.containsKey(count + 1)) {
            lists.put(count + 1, new LinkedHashSet<>());
        }
        lists.get(count + 1).add(cacheObject);
        return casheObjects.get(key);
    }

    @Override
    public void put(BaseModel obj) {
        if (capacity <= 0)
            return;
        if (casheObjects.containsKey(obj.getId())) {
            casheObjects.put(obj.getId(), obj);
            get(obj.getId());
            return;
        }
        if (casheObjects.size() >= capacity) {
            BaseModel evit = (BaseModel) lists.get(min).iterator().next();
            lists.get(min).remove(evit);
            counters.remove(evit.getId());
            casheObjects.remove(evit.getId());
        }
        casheObjects.put(obj.getId(), obj);
        counters.put(obj.getId(), 1);
        min = 1;
        lists.get(1).add(obj);
    }

}
