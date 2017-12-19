package com.codecool.krk.oni.model;

import org.json.JSONObject;

public class Car {
    private Integer id;
    private String manufacturer;
    private String model;
    private String color;
    // Change to Integer in db and here
    private String yearOfProduction;
    private Integer showroomId;

    public Car(Integer id, String manufacturer, String model, String color,
               String yearOfProduction, Integer showroomId) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.color = color;
        this.yearOfProduction = yearOfProduction;
        this.showroomId = showroomId;
    }

    public Car(String manufacturer, String model, String color, String yearOfProduction, Integer showroomId) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.color = color;
        this.yearOfProduction = yearOfProduction;
        this.showroomId = showroomId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(String yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public Integer getShowroomId() {
        return showroomId;
    }

    public void setShowroomId(Integer showroomId) {
        this.showroomId = showroomId;
    }

    public String toString() {
        return String.format("id: %d. manufacturer: %, model: %s, color: %s, year_of_production: %s, showroom_id: %d",
                this.id, this.manufacturer, this.model, this.color, this.yearOfProduction, this.showroomId);
    }

    public JSONObject toJSON() {
        JSONObject jsonCar = new JSONObject();
        jsonCar.put("id", this.id);
        jsonCar.put("manufacturer", this.manufacturer);
        jsonCar.put("model", this.model);
        jsonCar.put("color", this.color);
        jsonCar.put("year_of_production", this.yearOfProduction);
        jsonCar.put("showroom_id", this.showroomId);
        return jsonCar;
    }
}
