package com.codecool.krk.oni.model;

import org.json.JSONObject;

public class Showroom {
    private Integer id;
    private String name;
    private String address;

    public Showroom() {
    }

    public Showroom(Integer id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Showroom(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String toString() {
        return String.format("id: %d. name: %, address: %s",
                this.id, this.name, this.address);
    }

    public JSONObject toJSON() {
        JSONObject jsonShowroom = new JSONObject();
        jsonShowroom.put("id", this.id);
        jsonShowroom.put("name", this.name);
        jsonShowroom.put("address", this.address);
        return jsonShowroom;
    }
}
