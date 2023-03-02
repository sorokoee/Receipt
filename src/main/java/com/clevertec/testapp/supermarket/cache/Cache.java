package com.clevertec.testapp.supermarket.cache;

import com.clevertec.testapp.supermarket.entity.BaseModel;

public interface Cache {

    BaseModel get(Long key);
    void put(BaseModel obj);
    //void remove(BaseModel obj);
}
