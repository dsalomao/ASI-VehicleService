package com.asc.veiculosmservice.data;

// import com.fasterxml.jackson.annotation.JsonProperty;

public class Vehicle {
    // @JsonProperty("id")
    private Integer id;
    // @JsonProperty("plate")
    private String plate;
    // @JsonProperty("year")
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
