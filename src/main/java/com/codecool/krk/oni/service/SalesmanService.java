package com.codecool.krk.oni.service;

import com.codecool.krk.oni.dao.SalesmanDao;
import com.codecool.krk.oni.exception.DaoException;
import com.codecool.krk.oni.exception.NoCompleteDataProvideException;
import com.codecool.krk.oni.exception.NoSuchSalesmanException;
import com.codecool.krk.oni.model.Salesman;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class SalesmanService {
    private SalesmanDao salesmanDao;
    private ObjectMapper objectMapper = new ObjectMapper();

    public SalesmanService() throws DaoException {
        this.salesmanDao = new SalesmanDao();
    }

    public String getObject(String idString) throws NumberFormatException, NoSuchSalesmanException,
            DaoException, JsonProcessingException {
        String content;

        if (idString == null) {
            content = getAllSalesmenJSON();
        } else {
            content = getSalesmanJSON(Integer.parseInt(idString));
        }
        return content;
    }

    public void postObject(String json) throws ClassCastException,
            NoCompleteDataProvideException, DaoException {
        Map<String, Object> jsonMap = mapJson(json);

        if (!jsonMap.containsKey("name") || !jsonMap.containsKey("salary") || !jsonMap.containsKey("birthYear")) {
            throw new NoCompleteDataProvideException("No all date for new salesman provided");
        }
        Salesman salesman = new Salesman((String) jsonMap.get("name"), (Integer) jsonMap.get("salary"),
                (Integer) jsonMap.get("birthYear"));
        salesmanDao.save(salesman);
    }

    public void putObject(String json) throws ClassCastException,
            NoSuchSalesmanException, NoCompleteDataProvideException, DaoException {
        Map<String, Object> jsonMap = mapJson(json);

        if (!jsonMap.containsKey("name") || !jsonMap.containsKey("salary") || !jsonMap.containsKey("birthYear")) {
            throw new NoCompleteDataProvideException("No all date for new salesman provided");
        }

        if (!jsonMap.containsKey("id")) {
            throw new NoSuchSalesmanException("Salesman id not specified");
        }

        Salesman salesman = getSalesman((Integer) jsonMap.get("id"));
        updateSalesman(salesman, jsonMap);
        salesmanDao.update(salesman);
    }

    public void deleteObject(String idString) throws NumberFormatException, NoSuchSalesmanException, DaoException {
        if (idString == null) {
            throw new NoSuchSalesmanException("Salesman id not specified");
        }
        Integer id = Integer.parseInt(idString);
        getSalesman(id);
        this.salesmanDao.delete(id);
    }

    private String getAllSalesmenJSON() throws DaoException, JsonProcessingException {
        return this.objectMapper.writeValueAsString(this.salesmanDao.getAllSalesmen());

    }

    private String getSalesmanJSON(Integer id) throws DaoException, NoSuchSalesmanException, JsonProcessingException {
        Salesman salesman = getSalesman(id);
        return this.objectMapper.writeValueAsString(salesman);
    }

    private Salesman getSalesman(Integer id) throws DaoException, NoSuchSalesmanException {
        Salesman salesman = this.salesmanDao.getSalesman(id);

        if (salesman == null) {
            throw new NoSuchSalesmanException(String.format("No salesman with id %d in database", id));
        }
        return salesman;
    }

    private void updateSalesman(Salesman salesman, Map<String, Object> jsonMap) {
        salesman.setName((String) jsonMap.get("name"));
        salesman.setSalary((Integer) jsonMap.get("salary"));
        salesman.setBirthYear((Integer) jsonMap.get("birthYear"));
    }

    private Map<String, Object> mapJson(String json) {
        Map<String, Object> jsonMap = new LinkedHashMap<>();

        try {
            jsonMap = objectMapper.readValue(json,
                    new TypeReference<Map<String,Object>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonMap;
    }
}
