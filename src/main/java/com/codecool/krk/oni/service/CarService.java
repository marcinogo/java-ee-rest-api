package com.codecool.krk.oni.service;

import com.codecool.krk.oni.dao.CarDao;
import com.codecool.krk.oni.dao.ShowroomDao;
import com.codecool.krk.oni.exception.*;
import com.codecool.krk.oni.model.Car;
import com.codecool.krk.oni.model.Showroom;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class CarService {
    private CarDao carDao;
    private ObjectMapper objectMapper = new ObjectMapper();

    public CarService() throws DaoException {
        this.carDao = new CarDao();
    }

    public String getObject(String idString) throws NumberFormatException, NoSuchCarException,
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
            NoCompleteDataProvideException, NoSuchShowroomException, DaoException , IOException {
        Map<String, Object> jsonCarMap = mapJson(json);

        if (!jsonCarMap.containsKey("showroom")) {
            throw new NoCompleteDataProvideException("No showroom data for new car provided");
        }

        Map<String, Object> showroomJson = (Map<String, Object>) jsonCarMap.get("showroom");

        if (!jsonCarMap.containsKey("manufacturer") || !jsonCarMap.containsKey("model") ||
                !jsonCarMap.containsKey("color") || !jsonCarMap.containsKey("yearOfProduction")) {
            throw new NoCompleteDataProvideException("No all data for new car provided");
        }

        Car car = new Car((String) jsonCarMap.get("manufacturer"), (String) jsonCarMap.get("model"),
                (String) jsonCarMap.get("color"), (Integer) jsonCarMap.get("yearOfProduction"), getShowroom(showroomJson));

        carDao.save(car);
    }

    public void putObject(String json) throws ClassCastException,
            NoSuchCarException, NoSuchShowroomException, NoCompleteDataProvideException, DaoException, IOException {
        Map<String, Object> jsonCarMap = mapJson(json);

        if (!jsonCarMap.containsKey("manufacturer") || !jsonCarMap.containsKey("model") ||
                !jsonCarMap.containsKey("color") || !jsonCarMap.containsKey("yearOfProduction")) {
            throw new NoCompleteDataProvideException("No all data for car update provided");
        }

        if (!jsonCarMap.containsKey("id")) {
            throw new NoSuchCarException("Car id not specified");
        }

        if (!jsonCarMap.containsKey("showroom")) {
            throw new NoCompleteDataProvideException("No showroom data for car update provided");
        }

        Map<String, Object> showroomJson = (Map<String, Object>) jsonCarMap.get("showroom");

        Showroom showroom = getShowroom(showroomJson);
        Car car = getCar((Integer) jsonCarMap.get("id"));

        updateCar(car, jsonCarMap, showroom);
        carDao.update(car);
    }

    public void deleteObject(String idString) throws NumberFormatException, NoSuchCarException, DaoException {
        if (idString == null) {
            throw new NoSuchCarException("Car id not specified");
        }
        Integer id = Integer.parseInt(idString);
        getCar(id);
        this.carDao.delete(id);
    }

    private String getAllCarsJSON() throws DaoException, JsonProcessingException {
        return this.objectMapper.writeValueAsString(this.carDao.getAllCars());
    }

    private String getCarJSON(Integer id) throws DaoException, NoSuchCarException, JsonProcessingException {
        Car car = getCar(id);
        return this.objectMapper.writeValueAsString(car);
    }

    private Car getCar(Integer id) throws DaoException, NoSuchCarException {
        Car car = this.carDao.getCar(id);

        if (car == null) {
            throw new NoSuchCarException(String.format("No car with id %d in database", id));
        }
        return car;
    }

    private Showroom getShowroom(Map<String, Object> showroomJson) throws DaoException, NoSuchShowroomException {
        ShowroomDao showroomDao = new ShowroomDao();

        Integer id = (Integer) showroomJson.get("id");
        Showroom showroom = showroomDao.getShowroom(id);

        if (showroom == null) {
            throw new NoSuchShowroomException(String.format("No showroom with id %d in database", id));
        }

        String name = (String)showroomJson.get("name");
        String address = (String)showroomJson.get("address");
        if (!showroom.getName().equals(name) || !showroom.getAddress().equals(address)) {
            throw new NoSuchShowroomException("Not all data or wrong data for showroom provided");
        }
        return showroom;
    }

    private void updateCar(Car car, Map<String, Object> jsonCarMap, Showroom showroom) {
        car.setManufacturer((String) jsonCarMap.get("manufacturer"));
        car.setModel((String) jsonCarMap.get("model"));
        car.setColor((String) jsonCarMap.get("color"));
        car.setYearOfProduction((Integer) jsonCarMap.get("yearOfProduction"));
        car.setShowroom(showroom);
    }

    private Map<String, Object> mapJson(String json) {
        Map<String, Object> jsonMap = new LinkedHashMap<>();

        try {
            jsonMap = objectMapper.readValue(json,
                    new TypeReference<Map<String,Object>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonMap;
    }
}
