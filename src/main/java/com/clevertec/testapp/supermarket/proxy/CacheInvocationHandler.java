package com.clevertec.testapp.supermarket.proxy;

import com.clevertec.testapp.supermarket.cache.Cache;
import com.clevertec.testapp.supermarket.entity.BaseModel;
import com.clevertec.testapp.supermarket.factory.CacheFactory;
import com.clevertec.testapp.supermarket.service.CashierService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;

public class CacheInvocationHandler implements InvocationHandler {
    private final CashierService cashierService;
    private Cache cache = new CacheFactory().createCache();
    ;


    public CacheInvocationHandler(CashierService cashierService) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        this.cashierService = cashierService;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class cacheClass = cache.getClass();
        BaseModel baseModel= (BaseModel) cacheClass.getMethod("get",Long.class).invoke(cache, args);
        if ("get".equals(method.getName())){
            if (baseModel!=null){
                return baseModel;
            }else {
                baseModel=(BaseModel)method.invoke(cashierService,args);
                cacheClass.getMethod("put", BaseModel.class).invoke(cache, args);
                return baseModel;
            }
        }
        if ("post".equals(method.getName())){
            cacheClass.getMethod("put", BaseModel.class).invoke(cache, args);
            method.invoke(cashierService,args);
        }
        if ("delete".equals(method.getName())){
            method.invoke(cashierService, args);
        }
        if ("put".equals(method.getName())){
            method.invoke(cashierService, args);
            cacheClass.getMethod("put", BaseModel.class).invoke(cache, args);
        }

        return proxy;
    }
}
