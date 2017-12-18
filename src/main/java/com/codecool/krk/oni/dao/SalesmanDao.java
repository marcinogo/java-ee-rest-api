package com.codecool.krk.oni.dao;

import com.codecool.krk.oni.exceptions.DaoException;
import com.codecool.krk.oni.models.Salesman;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class SalesmanDao {

    private final Connection connection;
    private PreparedStatement stmt = null;

    public SalesmanDao() throws DaoException {

        this.connection = DatabaseConnection.getConnection();
    }

    public Salesman getSalesman(Integer id) throws DaoException {

        Salesman salesman = null;
        String sqlQuery = "SELECT * FROM salesmen WHERE id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                salesman = createSalesman(result);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return salesman;
    }

    private Salesman createSalesman(ResultSet result) throws SQLException, DaoException {
        String name = result.getString("name");
        Integer salary = result.getInt("salary");
        Integer birthYear = result.getInt("birth_year");
        Integer id = result.getInt("id");

        return new Salesman(id, name, salary, birthYear);
    }

    public ArrayList<Salesman> getAllSalesmen() throws DaoException {

        ArrayList<Salesman> foundSalesmen = new ArrayList<>();
        String sqlQuery = "SELECT * FROM salesmen";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Salesman salesman = createSalesman(result);

                foundSalesmen.add(salesman);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return foundSalesmen;
    }

    public void save(Salesman salesman) throws DaoException {

        String name = salesman.getName();
        Integer salary = salesman.getSalary();
        Integer birthYear = salesman.getBirthYear();

        String sqlQuery = "INSERT INTO salesmen "
                + "(name, salary, birth_year) "
                + "VALUES (?, ?, ?);";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, name);
            stmt.setInt(2, salary);
            stmt.setInt(3, birthYear);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

    }

    public void update(Salesman salesman) throws DaoException {

        Integer id = salesman.getId();
        String name = salesman.getName();
        Integer salary = salesman.getSalary();
        Integer birthYear = salesman.getBirthYear();

        String sqlQuery = "UPDATE salesmen "
                + "SET name = ?, salary = ?, birth_year = ? "
                + "WHERE id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, name);
            stmt.setInt(2, salary);
            stmt.setInt(3, birthYear);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

    }

    public void delete(Integer id) throws DaoException {

        String sqlQuery = "DELETE FROM salesmen "
                + "WHERE id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

    }

}
