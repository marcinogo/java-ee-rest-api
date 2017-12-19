package com.codecool.krk.oni.model;

import org.json.JSONObject;

public class Salesman {
    private Integer id;
    private String name;
    private Integer salary;
    private Integer birthYear;


    public Salesman(Integer id, String name, Integer salary, Integer birthYear) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.birthYear = birthYear;
    }

    public Salesman(String name, Integer salary, Integer birthYear) {
        this.name = name;
        this.salary = salary;
        this.birthYear = birthYear;
    }

    public String toString() {
        return String.format("id: %d. name: %s, salary: %d, birth_year",
                this.id, this.name, this.salary, this.birthYear);
    }

    /*public void save() throws DaoException {
        mentorDao.save(this);
    }

    public void update() throws DaoException {
        mentorDao.update(this);
    }*/

    public JSONObject toJSON() {
        JSONObject jsonSalesman = new JSONObject();
        jsonSalesman.put("id", this.id);
        jsonSalesman.put("name", this.name);
        jsonSalesman.put("salary", this.salary);
        jsonSalesman.put("birth_year", this.birthYear);
        return jsonSalesman;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }
}
