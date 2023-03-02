package com.clevertec.testapp.supermarket.cache.impl;

import com.clevertec.testapp.supermarket.entity.BaseModel;
import com.clevertec.testapp.supermarket.entity.Cashier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LFUCacheImplTest {
    private final Cashier FIRST = new Cashier(1L, "Ivanov", 'm', 200.0, "ivanov@gmail.com");
    private final Cashier SECOND = new Cashier(2L, "Petrova", 'f', 200.0, "petrova@gmail.com");
    private final Cashier THIRD = new Cashier(11L, "Sidorin", 'm', 2000.0, "sidorin@gmail.com");
    private LFUCacheImpl lfuCache;
    @BeforeEach
    void setUp(){
        lfuCache=new LFUCacheImpl(2);
    }

    @Test
    void checkPutObjectInCacheOrNot(){
        lfuCache.put(FIRST);
        BaseModel actualResult= lfuCache.get(FIRST.getId());
         assertThat(actualResult).isEqualTo(FIRST);
    }
    @Test
    void checkDeletedElementAfterCacheOverflow(){
        lfuCache.put(FIRST);
        lfuCache.put(SECOND);
        lfuCache.put(THIRD);
        BaseModel actualResult= lfuCache.get(FIRST.getId());
        assertThat(actualResult).isEqualTo(null);
    }
    @Test
    void checkQueuForDeletionIfGetElementBefore(){
        lfuCache.put(FIRST);
        lfuCache.put(SECOND);
        lfuCache.get(FIRST.getId());
        lfuCache.put(THIRD);
        BaseModel actualResult= lfuCache.get(SECOND.getId());
        assertThat(actualResult).isEqualTo(null);
    }

}