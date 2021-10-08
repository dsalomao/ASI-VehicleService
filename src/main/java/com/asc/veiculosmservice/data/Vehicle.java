package com.asc.veiculosmservice.data;

// Classe de dados para melhor visualização da troca de mensagens reais
public class Vehicle {
    private Integer id;
    private String plate;
    private String year;

    public Vehicle() 
    {
    }

    public Integer getId() {
        return id;
    };

    public String getPlate() {
        return plate;
    }

    public String getYear() {
        return year;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
