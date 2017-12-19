package com.codecool.krk.oni.service;

import com.codecool.krk.oni.dao.SalesmanDao;
import com.codecool.krk.oni.exception.DaoException;
import com.codecool.krk.oni.exception.NoCompleteDataProvideException;
import com.codecool.krk.oni.exception.NoSuchSalesmanException;
import com.codecool.krk.oni.model.Salesman;
import org.json.JSONArray;

public class SalesmanService {
    private SalesmanDao salesmanDao;

    public SalesmanService() throws DaoException {
        this.salesmanDao = new SalesmanDao();
    }

    public String getSalesman(String idString) throws NumberFormatException, NoSuchSalesmanException, DaoException {
        String content;

        if (idString == null) {
            content = getAllSalesmenJSON();
        } else {
            content = getSalesmanJSON(Integer.parseInt(idString));
        }
        return content;
    }

    public void postSalesman(String name, String salary, String birthYear) throws NumberFormatException,
            NoCompleteDataProvideException, DaoException {
        if (name == null || salary == null || birthYear == null) {
            throw new NoCompleteDataProvideException("No all date for new salesman provided");
        }
        Salesman salesman = new Salesman(name, Integer.parseInt(salary), Integer.parseInt(birthYear));
        salesmanDao.save(salesman);
    }

    public void putSalesman(String idString, String name, String salary, String birthYear) throws NumberFormatException,
            NoSuchSalesmanException, NoCompleteDataProvideException, DaoException {
        if (idString == null) {
            throw new NoSuchSalesmanException("Salesman id not specified");
        }

        if (name == null || salary == null || birthYear == null) {
            throw new NoCompleteDataProvideException("No all date for update salesman provided");
        }

        Salesman salesman = getSalesman(Integer.parseInt(idString));

        updateSalesman(salesman, name, salary, birthYear);
        salesmanDao.update(salesman);
    }

    public void deleteSalesman(String idString) throws NumberFormatException, NoSuchSalesmanException, DaoException {
        if (idString == null) {
            throw new NoSuchSalesmanException("Salesman id not specified");
        }
        Integer id = Integer.parseInt(idString);
        getSalesman(id);
        this.salesmanDao.delete(id);
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
        return salesman.toJSON().toString();
    }

    private Salesman getSalesman(Integer id) throws DaoException, NoSuchSalesmanException {
        Salesman salesman = this.salesmanDao.getSalesman(id);

        if (salesman == null) {
            throw new NoSuchSalesmanException(String.format("No salesman with id %d in database", id));
        }
        return salesman;
    }

    private void updateSalesman(Salesman salesman, String name, String salary, String birthYear) throws NumberFormatException {
        salesman.setName(name);
        salesman.setSalary(Integer.parseInt(salary));
        salesman.setBirthYear(Integer.parseInt(birthYear));
    }
}
