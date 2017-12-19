package com.codecool.krk.oni.service;

import com.codecool.krk.oni.dao.SalesmanDao;
import com.codecool.krk.oni.exception.DaoException;
import com.codecool.krk.oni.exception.NoSuchSalesmanException;
import com.codecool.krk.oni.model.Salesman;
import org.json.JSONArray;

public class SalesmanService {
    private SalesmanDao salesmanDao;

    public SalesmanService() throws DaoException {
        this.salesmanDao = new SalesmanDao();
    }

    public String getSalesman(String idString) throws NumberFormatException, NoSuchSalesmanException, DaoException {
        String content = null;

        if (idString == null) {
            content = getAllSalesmenJSON();
        } else {
            Integer id = Integer.parseInt(idString);
            content = getSalesmanJSON(id);
        }
        return content;
    }

    private String getAllSalesmenJSON() throws DaoException {
        JSONArray array = new JSONArray();
        for (Salesman salesman: this.salesmanDao.getAllSalesmen()) {
            array.put(salesman.toJSON());
        }
        return array.toString();
    }

    private String getSalesmanJSON(Integer id) throws DaoException, NoSuchSalesmanException {
        Salesman salesman = getSalesman(id);
        String content = salesman.toJSON().toString();
        return content;
    }

    private Salesman getSalesman(Integer id) throws DaoException, NoSuchSalesmanException {
        Salesman salesman = this.salesmanDao.getSalesman(id);

        if (salesman == null) {
            throw new NoSuchSalesmanException(String.format("No salesman with id %d in database", id));
        }
        return salesman;
    }
}
