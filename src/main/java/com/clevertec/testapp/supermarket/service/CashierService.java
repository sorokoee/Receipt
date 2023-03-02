package com.clevertec.testapp.supermarket.service;

import com.clevertec.testapp.supermarket.entity.Cashier;

public interface CashierService {
    Cashier get(Long id);
    void post(Cashier cashier);
    void delete(Long id);
    void put(Cashier cashier);


}
