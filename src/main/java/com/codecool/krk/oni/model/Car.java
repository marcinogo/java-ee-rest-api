package com.codecool.krk.oni.model;

public class Car {
    private Integer id;
    private String manufacturer;
    private String model;
    private String color;
    private Integer yearOfProduction;
    private Showroom showroom;

    public Car() {
    }

    public Car(Integer id, String manufacturer, String model, String color,
               Integer yearOfProduction, Showroom showroom) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.color = color;
        this.yearOfProduction = yearOfProduction;
        this.showroom = showroom;
    }

    public Car(String manufacturer, String model, String color, Integer yearOfProduction, Showroom showroom) {
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

    public Integer getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(Integer yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public Showroom getShowroom() {
        return showroom;
    }

    public void setShowroom(Showroom showroom) {
        this.showroom = showroom;
    }

}
