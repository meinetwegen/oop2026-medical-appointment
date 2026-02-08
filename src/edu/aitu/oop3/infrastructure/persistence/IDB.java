package edu.aitu.oop3.infrastructure.persistence;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDB {
    Connection getConnection() throws SQLException;
}
