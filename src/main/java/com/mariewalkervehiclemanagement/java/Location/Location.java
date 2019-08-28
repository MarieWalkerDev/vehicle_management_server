package com.mariewalkervehiclemanagement.java.Location;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String address;

//    @OneToMany(mappedBy = "location")
//    private List<Car> cars;

    public Location(long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Location() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
