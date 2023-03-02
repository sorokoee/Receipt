package com.clevertec.testapp.supermarket.cache.impl;

import com.clevertec.testapp.supermarket.entity.BaseModel;
import com.clevertec.testapp.supermarket.entity.Cashier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LRUCacheImplTest {
    private final Cashier FIRST = new Cashier(1L, "Ivanov", 'm', 200.0, "ivanov@gmail.com");
    private final Cashier SECOND = new Cashier(2L, "Petrova", 'f', 200.0, "petrova@gmail.com");
    private final Cashier THIRD = new Cashier(11L, "Sidorin", 'm', 2000.0, "sidorin@gmail.com");
    private LRUCacheImpl lruCache;
    @BeforeEach
    void setUp(){
        lruCache=new LRUCacheImpl(2);
    }

    @Test
    void checkPutObjectInCacheOrNot(){
        lruCache.put(FIRST);
        BaseModel actualResult= lruCache.get(FIRST.getId());
        assertThat(actualResult).isEqualTo(FIRST);
    }
    @Test
    void checkDeletedElementAfterCacheOverflow(){
        lruCache.put(FIRST);
        lruCache.put(SECOND);
        lruCache.put(THIRD);
        BaseModel actualResult= lruCache.get(FIRST.getId());
        assertThat(actualResult).isEqualTo(null);
    }
    @Test
    void checkQueuForDeletionIfGetElementBefore(){
        lruCache.put(FIRST);
        lruCache.put(SECOND);
        lruCache.get(FIRST.getId());
        lruCache.put(THIRD);
        BaseModel actualResult= lruCache.get(SECOND.getId());
        assertThat(actualResult).isEqualTo(null);
    }

}