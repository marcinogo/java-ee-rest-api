package com.codecool.krk.oni.servlet;

import com.codecool.krk.oni.exception.*;
import com.codecool.krk.oni.service.CarService;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/cars/*"})
public class CarServlet extends HttpServlet {

    protected void doGet( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");

        response.setContentType("application/json");

        try {
            CarService carService = new CarService();
            response.getWriter().write(carService.getObject(idString));
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (NoSuchCarException e) {
            send404(response, String.format("404: %s", e.getMessage()));
            e.printStackTrace();
        } catch (NumberFormatException e) {
            send400(response, "400: Wrong format of car id given");
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        try {
            CarService carService = new CarService();
            carService.postObject(json);
            send200(response, "200: Add new car to database");
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (NoCompleteDataProvideException e) {
            send400(response, String.format("400: %s", e.getMessage()));
            e.printStackTrace();
        } catch (ClassCastException e) {
            send400(response, "400: Wrong format of numeric data for new car provided");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            send400(response, "400: Wrong format of numeric data for new car provided");
            e.printStackTrace();
        } catch (NoSuchShowroomException e) {
            send404(response, String.format("404: %s", e.getMessage()));
            e.printStackTrace();
        }
    }

    protected void doPut( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");
        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        try {
            CarService carService = new CarService();
            carService.putObject(json);
            send200(response, String.format("200: Update car with id %s in database", idString));
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (NoSuchCarException e) {
            send404(response, String.format("404: %s", e.getMessage()));
            e.printStackTrace();
        } catch (NoCompleteDataProvideException e) {
            send400(response, String.format("400: %s", e.getMessage()));
            e.printStackTrace();
        } catch (ClassCastException e) {
            send400(response, "400: Wrong format of numeric data for update car provided");
            e.printStackTrace();
        } catch (NoSuchShowroomException e) {
            send404(response, String.format("404: %s", e.getMessage()));
            e.printStackTrace();
        }
    }

    protected void doDelete( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");

        try {
            CarService carService = new CarService();
            carService.deleteObject(idString);
            send200(response, String.format("200: Delete car with id %s from database", idString));
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (NoSuchCarException e) {
            send404(response, String.format("404: %s", e.getMessage()));
            e.printStackTrace();
        } catch (NumberFormatException e) {
            send400(response, "400: Wrong format of car id given");
            e.printStackTrace();
        }
    }

    private void send200(HttpServletResponse response, String message) throws IOException {
        response.setStatus(200);
        response.setContentType("text/plain");
        response.getWriter().write(message);
    }

    private void send400(HttpServletResponse response, String message) throws IOException {
        response.setStatus(400);
        response.setContentType("text/plain");
        response.getWriter().write(message);
    }

    private void send404(HttpServletResponse response, String message) throws IOException {
        response.setStatus(404);
        response.setContentType("text/plain");
        response.getWriter().write(message);
    }
}