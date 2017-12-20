package com.codecool.krk.oni.servlet;

import com.codecool.krk.oni.exception.DaoException;
import com.codecool.krk.oni.exception.NoCompleteDataProvideException;
import com.codecool.krk.oni.exception.NoSuchSalesmanException;
import com.codecool.krk.oni.service.SalesmanService;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/salesmen/*"})
public class SalesmanServlet extends HttpServlet {

    protected void doGet( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");

        response.setContentType("application/json");

        try {
            SalesmanService salesmanService = new SalesmanService();
            response.getWriter().write(salesmanService.getSalesman(idString));
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (NoSuchSalesmanException e) {
            send404(response, String.format("404: %s", e.getMessage()));
            e.printStackTrace();
        } catch (NumberFormatException e) {
            send400(response, "400: Wrong format of salesman id given");
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        try {
            SalesmanService salesmanService = new SalesmanService();
            salesmanService.postSalesman(json);
            send200(response, "200: Add new salesman to database");
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (NoCompleteDataProvideException e) {
            send400(response, String.format("400: %s", e.getMessage()));
            e.printStackTrace();
        } catch (ClassCastException e) {
            send400(response, "400: Wrong format of numeric data for new salesman provided");
            e.printStackTrace();
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");
        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        try {
            SalesmanService salesmanService = new SalesmanService();
            salesmanService.putSalesman(json);
            send200(response, String.format("200: Update salesman with id %s in database", idString));
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (NoSuchSalesmanException e) {
            send404(response, String.format("404: %s", e.getMessage()));
            e.printStackTrace();
        } catch (NoCompleteDataProvideException e) {
            send400(response, String.format("400: %s", e.getMessage()));
            e.printStackTrace();
        } catch (ClassCastException e) {
            send400(response, "400: Wrong format of numeric data for update salesman provided");
            e.printStackTrace();
        }
    }

    protected void doDelete( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");

        try {
            SalesmanService salesmanService = new SalesmanService();
            salesmanService.deleteSalesman(idString);
            send200(response, String.format("200: Delete salesman with id %s from database", idString));
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (NoSuchSalesmanException e) {
            send404(response, String.format("404: %s", e.getMessage()));
            e.printStackTrace();
        } catch (NumberFormatException e) {
            send400(response, "400: Wrong format of salesman id given");
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