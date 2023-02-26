package com.clevertec.testapp.supermarket.service.impl;

import com.clevertec.testapp.supermarket.dao.DAO;
import com.clevertec.testapp.supermarket.entity.Cashier;
import com.clevertec.testapp.supermarket.service.CashierService;
import com.clevertec.testapp.supermarket.validator.EmailValidator;
import com.clevertec.testapp.supermarket.validator.impl.EmailValidatorImpl;

public class CashierServiceImpl implements CashierService {
    private DAO<Cashier> dao;
    EmailValidator emailValidator=new EmailValidatorImpl();

    @Override
    public Cashier get(Long id) {
        return dao.findById(id);
    }

    @Override
    public void post(Cashier cashier) {
        if (emailValidator.isValid(cashier.getEmail())){
        dao.add(cashier);
        }else {
            System.out.println("Check email");
        }
    }

    @Override
    public void delete(Long id) {
        dao.delete(id);
    }

    @Override
    public void put(Cashier cashier) {
        dao.update(cashier);
    }
}
