package eetac.dsa.control1.entity;

import eetac.dsa.control1.EetakemonManagerDB;

/**
 * Created by Home on 22/04/2017.
 */
public class Uobject extends EetakemonManagerDB {
    private String name;
    private String description;

    public Uobject() {
    }

    public Uobject(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Uobject [name= " + name + ", description= " + description + "]";
    }

    @Override
    public String getPrimaryKey() {
        return "name";
    }

    @Override
    public String getPKMethod() {
        return "getName";
    }
}
