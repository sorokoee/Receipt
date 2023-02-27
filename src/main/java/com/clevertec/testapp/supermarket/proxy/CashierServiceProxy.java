package com.clevertec.testapp.supermarket.proxy;

import com.clevertec.testapp.supermarket.entity.Cashier;
import com.clevertec.testapp.supermarket.service.CashierService;
import com.clevertec.testapp.supermarket.service.impl.CashierServiceImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

public class CashierServiceProxy implements CashierService {
    private static CashierService cashierService;
    static {
        cashierService=new CashierServiceImpl();
        ClassLoader cashierServiceClassLoader=cashierService.getClass().getClassLoader();
        Class<?> [] cachierServiceInterfaces=cashierService.getClass().getInterfaces();
        try {
            cashierService=(CashierService) Proxy.newProxyInstance(cashierServiceClassLoader,
                    cachierServiceInterfaces,new CacheInvocationHandler(cashierService));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Cashier get(Long id) {
        return cashierService.get(id);
    }

    @Override
    public void post(Cashier cashier) {
        cashierService.post(cashier);
    }

    @Override
    public void delete(Long id) {
        cashierService.delete(id);
    }

    @Override
    public void put(Cashier cashier) {
        cashierService.put(cashier);
    }
}
