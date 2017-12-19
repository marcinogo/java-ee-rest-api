package com.codecool.krk.oni.model;

import org.json.JSONObject;

public class Car {
    private Integer id;
    private String manufacturer;
    private String model;
    private String color;
    // Change to Integer in db and here
    private String yearOfProduction;
    private Showroom showroom;

    public Car(Integer id, String manufacturer, String model, String color,
               String yearOfProduction, Showroom showroom) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.color = color;
        this.yearOfProduction = yearOfProduction;
        this.showroom = showroom;
    }

    public Car(String manufacturer, String model, String color, String yearOfProduction, Showroom showroom) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.color = color;
        this.yearOfProduction = yearOfProduction;
        this.showroom = showroom;
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

    public Showroom getShowroom() {
        return showroom;
    }

    public void setShowroom(Showroom showroom) {
        this.showroom = showroom;
    }

    public String toString() {
        return String.format("id: %d. manufacturer: %, model: %s, color: %s, year_of_production: %s, showroom_id: %d",
                this.id, this.manufacturer, this.model, this.color, this.yearOfProduction, this.showroom.getId());
    }

    public JSONObject toJSON() {
        JSONObject jsonCar = new JSONObject();
        jsonCar.put("id", this.id);
        jsonCar.put("manufacturer", this.manufacturer);
        jsonCar.put("model", this.model);
        jsonCar.put("color", this.color);
        jsonCar.put("year_of_production", this.yearOfProduction);
        jsonCar.put("showroom_id", this.showroom.getId());
        return jsonCar;
    }
}
