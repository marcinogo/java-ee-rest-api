package com.codecool.krk.oni.dao;

import com.codecool.krk.oni.exception.DaoException;
import com.codecool.krk.oni.model.Car;
import com.codecool.krk.oni.model.Showroom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarDao {

    private final Connection connection;
    private PreparedStatement stmt = null;

    public CarDao() throws DaoException {

        this.connection = DatabaseConnection.getConnection();
    }

    public Car getCar(Integer id) throws DaoException {

        Car car = null;
        String sqlQuery = "SELECT * FROM cars WHERE id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                car = createCar(result);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return car;
    }

    private Car createCar(ResultSet result) throws SQLException, DaoException {
        Integer id = result.getInt("id");
        String manufacturer = result.getString("manufacturer");
        String model = result.getString("model");
        String color = result.getString("color");
        String yearOfProduction = result.getString("year");
        Integer showroomId = result.getInt("showroom");
        Showroom showroom = new ShowroomDao().getShowroom(showroomId);

        return new Car(id, manufacturer, model, color, yearOfProduction, showroom);
    }

    public ArrayList<Car> getAllCars() throws DaoException {

        ArrayList<Car> foundCars = new ArrayList<>();
        String sqlQuery = "SELECT * FROM cars";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Car car = createCar(result);

                foundCars.add(car);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return foundCars;
    }

    public ArrayList<Car> getAllCarsByShowroom(Showroom showroom) throws DaoException {

        ArrayList<Car> foundCars = new ArrayList<>();
        String sqlQuery = "SELECT * FROM cars WHERE showroom = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, showroom.getId());
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Car car = createCar(result);

                foundCars.add(car);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return foundCars;
    }

    public void save(Car car) throws DaoException {

        String manufacturer = car.getManufacturer();
        String model = car.getModel();
        String color = car.getColor();
        String yearOfProduction = car.getYearOfProduction();
        Integer showroomId = car.getShowroom().getId();

        String sqlQuery = "INSERT INTO cars "
                + "(manufacturer, model, color, year, showroom) "
                + "VALUES (?, ?, ?, ?, ?);";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, manufacturer);
            stmt.setString(2, model);
            stmt.setString(3, color);
            stmt.setString(4, yearOfProduction);
            stmt.setInt(5, showroomId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

    }

    public void update(Car car) throws DaoException {

        Integer id = car.getId();
        String manufacturer = car.getManufacturer();
        String model = car.getModel();
        String color = car.getColor();
        String yearOfProduction = car.getYearOfProduction();
        Integer showroomId = car.getShowroom().getId();

        String sqlQuery = "UPDATE cars "
                + "SET manufacturer = ?, model = ?, color = ?, year = ?, showroom = ? "
                + "WHERE id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, manufacturer);
            stmt.setString(2, model);
            stmt.setString(3, color);
            stmt.setString(4, yearOfProduction);
            stmt.setInt(5, showroomId);
            stmt.setInt(6, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

    }

    public void delete(Integer id) throws DaoException {

        String sqlQuery = "DELETE FROM cars "
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
