package com.codecool.krk.oni.servlet;

import com.codecool.krk.oni.dao.ShowroomDao;
import com.codecool.krk.oni.exception.DaoException;
import com.codecool.krk.oni.exception.WrongDataException;
import com.codecool.krk.oni.model.Showroom;
import com.codecool.krk.oni.service.ShowroomService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/showrooms/*"})
public class ShowroomServlet extends HttpServlet {

    protected void doGet( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");
        String showCars = request.getParameter("cars");

        response.setContentType("application/json");

        try {
            ShowroomService showroomService = new ShowroomService();
            response.getWriter().write(showroomService.getShowroom(idString, showCars));
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (WrongDataException e) {
            send404(response, String.format("404: %s", e.getMessage()));
            e.printStackTrace();
        } catch (NumberFormatException e) {
            send400(response, "400: Wrong format of salesman id given");
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Showroom showroom = new ShowroomService().createShowroomFromJSONPost(request);
            new ShowroomDao().save(showroom);
            send200(response, "200: Add new salesman to database");
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (WrongDataException e1) {
            send404(response, "Error 404: No complete data to add new showroom");
        }

    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Showroom showroom = new ShowroomService().createShowroomFromJSONPut(request);
            new ShowroomDao().update(showroom);
            send200(response, "200: Showroom was successfully updated");
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (WrongDataException e1) {
            send404(response, "Error 404: No complete data to update showroom");
        } catch (NumberFormatException e2) {
            send404(response, "Error 404: There is no showroom of such id");
        }

    }

    protected void doDelete( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");

        try {

            if (new ShowroomService().isShowroom(idString)) {
                new ShowroomDao().delete(Integer.valueOf(idString));
                send200(response, "200: Showroom was successfully updated");
            } else {
                throw new NumberFormatException();
            }

        } catch (DaoException e) {
            e.printStackTrace();
        } catch (NumberFormatException e2) {
            send404(response, "Error 404: There is no showroom of such id");
        }
    }

    /*private String getAllSalesmenJSON(SalesmanDao salesmanDao) throws DaoException {
        JSONArray array = new JSONArray();
        for (Salesman salesman: salesmanDao.getAllSalesmen()) {
            array.put(salesman.toJSON());
        }
        return array.toString();
    }

    private String getSalesmanJSON(SalesmanDao salesmanDao, Integer id) throws DaoException {
        String content = null;
        Salesman salesman = getSalesman(salesmanDao, id);
        if (salesman != null) {
            content = salesman.toJSON().toString();
        }

        return content;
    }

    private Salesman getSalesman(SalesmanDao salesmanDao, Integer id) throws DaoException{
        return salesmanDao.getSalesman(id);
    }*/

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