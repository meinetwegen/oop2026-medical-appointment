package edu.aitu.oop3.shared;

import java.sql.SQLException;
import java.util.List;

public interface IRepository<T> {
    void add(T entity) throws SQLException;
    T findById(int id) throws SQLException;
    List<T> findAll() throws SQLException;
}