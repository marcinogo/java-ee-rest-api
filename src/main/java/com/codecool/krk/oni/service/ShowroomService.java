package com.codecool.krk.oni.service;

import com.codecool.krk.oni.dao.CarDao;
import com.codecool.krk.oni.dao.ShowroomDao;
import com.codecool.krk.oni.exception.DaoException;
import com.codecool.krk.oni.exception.WrongDataException;
import com.codecool.krk.oni.model.Car;
import com.codecool.krk.oni.model.Showroom;
import org.json.JSONArray;

import javax.servlet.http.HttpServletRequest;

public class ShowroomService {

    public Showroom createShowroomFromJSONPost(HttpServletRequest request) throws WrongDataException {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        Showroom showroom = null;

        if (name == null || name.equals("")) {
            throw new WrongDataException("400: No complete data to add new showroom");
        } else {
            showroom = new Showroom(name, address);
        }

        return showroom;
    }

    public Showroom createShowroomFromJSONPut(HttpServletRequest request) throws WrongDataException, NumberFormatException, DaoException {
        String idString = request.getParameter("id");
        Integer id = Integer.valueOf(idString);
        String name = request.getParameter("name");
        String address = request.getParameter("address");

        Showroom showroom = null;

        if (name.equals("")) {
            throw new WrongDataException("400: No complete data to add new showroom");
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

        if (idString != null && showCars.equals("true")) {
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
            throw new WrongDataException(String.format("No salesman with id %d in database", id));
        }
        return showroom;
    }
}
