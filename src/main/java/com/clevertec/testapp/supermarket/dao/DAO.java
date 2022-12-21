package com.clevertec.testapp.supermarket.dao;

import java.util.List;

// Интерфейс для доступа к данным (API для запросов)
// Используется паттерн DAO - Data Access Object, чтобы можно было подключать любой источник данных, а API не менялось
// Минимальный функционал CRUD - create read update delete
public interface DAO<T> {

    boolean add(T obj);

    boolean update(T obj);

    boolean delete(Long id) throws IllegalArgumentException ;

    T findById(Long id) throws IllegalArgumentException; // поиск по ID

    List<T> findAll(); // поиск всех записей

    List<T> find(String text); // поиск по включению текста

}
