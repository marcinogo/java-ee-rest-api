package com.codecool.krk.oni.servlet;

import com.codecool.krk.oni.dao.SalesmanDao;
import com.codecool.krk.oni.exception.DaoException;
import com.codecool.krk.oni.model.Salesman;
import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/salesmen/*"})
public class SalesmanServlet extends HttpServlet {

    protected void doGet( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");

        response.setContentType("application/json");

        try {
            SalesmanDao salesmanDao = new SalesmanDao();
            String content = null;
            if (idString == null) {
                content = getAllSalemen(salesmanDao);
            } else {
                content = getSaleman(salesmanDao, Integer.valueOf(idString));
            }

            if (content != null) {
                response.getWriter().write(content);
            } else {
                send404(response);
            }

        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Salesman salesman = new Salesman("Maciej", 20000, 1984);
        try {
            SalesmanDao salesmanDao = new SalesmanDao();
            salesmanDao.save(salesman);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        response.getWriter().write("salesman");
    }

    protected void doPut( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Salesman salesman = new Salesman(1,"John Smith", 20000, 1984);
        try {
            SalesmanDao salesmanDao = new SalesmanDao();
            salesmanDao.update(salesman);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        response.getWriter().write("salesman");
    }

    protected void doDelete( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            SalesmanDao salesmanDao = new SalesmanDao();
            salesmanDao.delete(5);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        response.getWriter().write("salesman");
    }

    private String getAllSalemen(SalesmanDao salesmanDao) throws DaoException {
        JSONArray array = new JSONArray();
        for (Salesman salesman: salesmanDao.getAllSalesmen()) {
            array.put(salesman.toJSON());
        }
        return array.toString();
    }

    private String getSaleman(SalesmanDao salesmanDao, Integer id) throws DaoException {
        String content = null;
        Salesman salesman = salesmanDao.getSalesman(id);
        if (salesman != null) {
            content = salesman.toJSON().toString();
        }

        return content;
    }

    private void send404(HttpServletResponse response) throws IOException {
        response.setStatus(404);
        response.setContentType("text/plain");
        response.getWriter().write("Error 404: There is no salesman of such id");
    }
}