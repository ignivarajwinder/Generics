package com.igniva.genererics;

/**
 * Created by ignivaandroid23 on 7/9/17.
 */

public class PojoType {

    String name;
    int count;
    String Location;

    public PojoType(String name, int count, String location) {
        this.name = name;
        this.count = count;
        Location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
