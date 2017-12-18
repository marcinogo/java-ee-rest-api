package com.codecool.krk.oni.dao;

import com.codecool.krk.oni.exceptions.DaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection = null;

    static Connection getConnection() throws DaoException {

        if (connection == null) {

            try {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/showroom",
                        "codecooler", "password");
            } catch (SQLException e) {
                throw new DaoException("DatabaseConnection class caused a problem!");
            }

        }

        return connection;
    }

    public static void closeConnection() throws DaoException {

        try {
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("DatabaseConnection class caused a problem!");
        }

    }

}
