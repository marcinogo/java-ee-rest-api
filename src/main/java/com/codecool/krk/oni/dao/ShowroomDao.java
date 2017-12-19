package com.codecool.krk.oni.dao;

import com.codecool.krk.oni.exception.DaoException;
import com.codecool.krk.oni.model.Showroom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShowroomDao {

    private final Connection connection;
    private PreparedStatement stmt = null;

    public ShowroomDao() throws DaoException {

        this.connection = DatabaseConnection.getConnection();
    }

    public Showroom getShowroom(Integer id) throws DaoException {

        Showroom showroom = null;
        String sqlQuery = "SELECT * FROM showrooms WHERE id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                showroom = createShowroom(result);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return showroom;
    }

    private Showroom createShowroom(ResultSet result) throws SQLException, DaoException {
        String name = result.getString("name");
        String address = result.getString("address");
        Integer id = result.getInt("id");

        return new Showroom(id, name, address);
    }

    public ArrayList<Showroom> getAllSalesmen() throws DaoException {

        ArrayList<Showroom> foundShowrooms = new ArrayList<>();
        String sqlQuery = "SELECT * FROM showrooms";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Showroom showroom = createShowroom(result);

                foundShowrooms.add(showroom);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return foundShowrooms;
    }

    public void save(Showroom showroom) throws DaoException {

        String name = showroom.getName();
        String address = showroom.getAddress();

        String sqlQuery = "INSERT INTO showrooms "
                + "(name, address) "
                + "VALUES (?, ?);";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

    }

    public void update(Showroom showroom) throws DaoException {

        Integer id = showroom.getId();
        String name = showroom.getName();
        String address = showroom.getAddress();

        String sqlQuery = "UPDATE showrooms "
                + "SET name = ?, address = ? "
                + "WHERE id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

    }

    public void delete(Integer id) throws DaoException {

        String sqlQuery = "DELETE FROM showrooms "
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
