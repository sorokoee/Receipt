package com.clevertec.testapp.supermarket.dao;

import java.util.List;


public interface DAO<T> {

    boolean add(T obj);

    boolean update(T obj);

    boolean delete(Long id) throws IllegalArgumentException ;

    T findById(Long id) throws IllegalArgumentException;

    List<T> findAll();

    List<T> find(String text);

}
