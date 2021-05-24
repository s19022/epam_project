package com.example.InspectionBoard.model.dao;

import java.util.List;

public interface GenericDao<T> extends AutoCloseable{
    int create(T t);
    int update(T t);
    boolean delete(int id);
    T findById(int id);
    List<T> findAll();
}
