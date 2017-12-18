package com.codecool.krk.oni.servlets;

import com.codecool.krk.oni.dao.SalesmanDao;
import com.codecool.krk.oni.exceptions.DaoException;
import com.codecool.krk.oni.models.Salesman;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/salesmen/*"})
public class SalesmanServlet extends HttpServlet {

    protected void doGet( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Salesman salesman = null;
        try {
            SalesmanDao salesmanDao = new SalesmanDao();
            salesman = salesmanDao.getSalesman(1);
        } catch (DaoException e) {
            e.printStackTrace();
        }


        response.getWriter().write(salesman.toJSON().toString());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.getWriter().write("salesman");
    }

    protected void doPut( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write("salesman");
    }

    protected void doDelete( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write("salesman");
    }
}