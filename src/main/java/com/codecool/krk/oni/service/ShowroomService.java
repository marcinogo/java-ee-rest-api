package com.codecool.krk.oni.service;

import com.codecool.krk.oni.dao.CarDao;
import com.codecool.krk.oni.dao.ShowroomDao;
import com.codecool.krk.oni.exception.DaoException;
import com.codecool.krk.oni.exception.WrongDataException;
import com.codecool.krk.oni.model.Car;
import com.codecool.krk.oni.model.Showroom;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONArray;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ShowroomService {

    public Showroom createShowroomFromJSONPost(HttpServletRequest request) throws WrongDataException, IOException {
        Map<String,String> JsonInput = readJSONToMap(request);

        String name = JsonInput.get("name");
        String address = JsonInput.get("address");
        Showroom showroom = null;

        if (name == null || name.equals("")) {
            throw new WrongDataException("400: No complete data to add new showroom");
        } else {
            showroom = new Showroom(name, address);
        }

        return showroom;
    }

    public Showroom createShowroomFromJSONPut(HttpServletRequest request) throws WrongDataException, NumberFormatException, DaoException, IOException {
        Map<String,String> JsonInput = readJSONToMap(request);

        String idString = JsonInput.get("id");
        Integer id = Integer.valueOf(idString);
        String name = JsonInput.get("name");
        String address = JsonInput.get("address");

        Showroom showroom = null;

        if (name == null || name.equals("")) {
            throw new WrongDataException("400: No complete data to update the showroom");
        } else {
            showroom = new ShowroomDao().getShowroom(id);

            if (name != null) {
                showroom.setName(name);
            }

            showroom.setAddress(address);
        }

        return showroom;
    }

    public boolean isShowroom(String idString) throws DaoException, NumberFormatException {

        Integer id = Integer.valueOf(idString);
        return new ShowroomDao().getShowroom(id) != null;
    }

    public String getShowroom(String idString, String showCars) throws NumberFormatException, WrongDataException, DaoException {
        String content;

        if (idString != null && showCars != null && showCars.equals("true")) {
            content = getAllCarsInShowroom(idString);
        } else if (idString == null) {
            content = getAllShowroomsJSON();
        } else {
            content = getShowroomJSON(Integer.parseInt(idString));
        }
        return content;
    }

    private String getAllCarsInShowroom(String idString) throws DaoException, WrongDataException {
        Showroom showroom = this.getShowroom(Integer.parseInt(idString));
        JSONArray array = new JSONArray();

        for (Car car: new CarDao().getAllCarsByShowroom(showroom)) {
            array.put(car.toJSON());
        }

        return array.toString();
    }

    private String getAllShowroomsJSON() throws DaoException {
        JSONArray array = new JSONArray();
        for (Showroom showroom: new ShowroomDao().getAllShowrooms()) {
            array.put(showroom.toJSON());
        }
        return array.toString();
    }

    private String getShowroomJSON(Integer id) throws DaoException, WrongDataException {
        Showroom showroom = this.getShowroom(id);
        return showroom.toJSON().toString();
    }

    private Showroom getShowroom(Integer id) throws DaoException, WrongDataException {
        Showroom showroom = new ShowroomDao().getShowroom(id);

        if (showroom == null) {
            throw new WrongDataException(String.format("No showroom with id %d in database", id));
        }
        return showroom;
    }

    private Map<String,String> readJSONToMap(HttpServletRequest request) throws IOException {
        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Map<String,String> resultMap = new HashMap<>();
        ObjectMapper mapperObj = new ObjectMapper();

        try {
            resultMap = mapperObj.readValue(json,
                    new TypeReference<HashMap<String,String>>(){});
            System.out.println(resultMap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultMap;
    }
}
