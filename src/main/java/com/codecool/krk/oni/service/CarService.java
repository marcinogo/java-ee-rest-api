package com.codecool.krk.oni.service;

import com.codecool.krk.oni.dao.CarDao;
import com.codecool.krk.oni.exception.DaoException;
import com.codecool.krk.oni.exception.NoCompleteDataProvideException;
import com.codecool.krk.oni.exception.NoSuchSalesmanException;
import com.codecool.krk.oni.model.Car;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class CarService implements Service {
    private CarDao carDao;
    private ObjectMapper objectMapper = new ObjectMapper();

    public CarService() throws DaoException {
        this.carDao = new CarDao();
    }

    public String getObject(String idString) throws NumberFormatException, NoSuchSalesmanException,
            DaoException, JsonProcessingException {
        String content;

        if (idString == null) {
            content = getAllCarsJSON();
        } else {
            content = getCarJSON(Integer.parseInt(idString));
        }
        return content;
    }

    public void postObject(String json) throws ClassCastException,
            NoCompleteDataProvideException, DaoException , IOException {
    }

    public void putObject(String json) throws ClassCastException,
            NoSuchSalesmanException, NoCompleteDataProvideException, DaoException, IOException {
    }

    public void deleteObject(String idString) throws NumberFormatException, NoSuchSalesmanException, DaoException {
    }

    private String getAllCarsJSON() throws DaoException, JsonProcessingException {

        return this.objectMapper.writeValueAsString(this.carDao.getAllCars());
    }

    private String getCarJSON(Integer id) throws DaoException, NoSuchSalesmanException, JsonProcessingException {
        Car car = getCar(id);
        return this.objectMapper.writeValueAsString(car);
    }

    private Car getCar(Integer id) throws DaoException, NoSuchSalesmanException {
        Car car = this.carDao.getCar(id);

        if (car == null) {
            throw new NoSuchSalesmanException(String.format("No car with id %d in database", id));
        }
        return car;
    }
}
