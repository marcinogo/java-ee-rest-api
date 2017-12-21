package com.codecool.krk.oni.serialization;

import com.codecool.krk.oni.model.Showroom;
import org.json.JSONObject;

public class ShowroomSerialization {

    public JSONObject toJSON(Showroom showroom) {
        JSONObject jsonShowroom = new JSONObject();
        jsonShowroom.put("id", showroom.getId());
        jsonShowroom.put("name", showroom.getName());
        jsonShowroom.put("address", showroom.getAddress());
        return jsonShowroom;
    }

}
