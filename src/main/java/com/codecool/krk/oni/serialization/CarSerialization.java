package com.codecool.krk.oni.serialization;

import com.codecool.krk.oni.model.Car;
import org.json.JSONObject;

public class CarSerialization {

    public JSONObject toJSON(Car car) {
        JSONObject jsonCar = new JSONObject();
        jsonCar.put("id", car.getId());
        jsonCar.put("manufacturer", car.getManufacturer());
        jsonCar.put("model", car.getModel());
        jsonCar.put("color", car.getColor());
        jsonCar.put("yearOfProduction", car.getYearOfProduction());
        jsonCar.put("showroom_id", car.getShowroom().getId());
        return jsonCar;
    }
}
