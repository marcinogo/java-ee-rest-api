package com.codecool.krk.oni.service;

import com.codecool.krk.oni.exception.DaoException;
import com.codecool.krk.oni.exception.NoCompleteDataProvideException;
import com.codecool.krk.oni.exception.NoSuchSalesmanException;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface Service {
    String getObject(String idString) throws NumberFormatException, NoSuchSalesmanException,
            DaoException, JsonProcessingException;

    void postObject(String json) throws ClassCastException,
            NoCompleteDataProvideException, DaoException , IOException;

    void putObject(String json) throws ClassCastException,
            NoSuchSalesmanException, NoCompleteDataProvideException, DaoException, IOException;

    void deleteObject(String idString) throws NumberFormatException, NoSuchSalesmanException, DaoException;

}
