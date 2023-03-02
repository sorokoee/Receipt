package com.clevertec.testapp.supermarket.factory;

import com.clevertec.testapp.supermarket.cache.Cache;
import com.clevertec.testapp.supermarket.reader.impl.CacheInfoReaderImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CacheFactory {
    private final CacheInfoReaderImpl cacheInfoReaderImpl;

    public CacheFactory() {
        cacheInfoReaderImpl =new CacheInfoReaderImpl();
    }

    public Cache createCache() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        String className= cacheInfoReaderImpl.read()[0];
        int capacity=Integer.parseInt(cacheInfoReaderImpl.read()[1]);
        Class cacheClass=Class.
                forName("com.clevertec.testapp.supermarket.cache."+className);
        Constructor<Cache> constructor= cacheClass.getConstructor(int.class);
        Cache cache=constructor.newInstance(capacity);
        return cache;
    }
}
