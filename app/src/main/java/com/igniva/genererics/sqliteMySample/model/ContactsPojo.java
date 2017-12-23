package com.igniva.genererics.sqliteMySample.model;

import java.io.Serializable;

/**
 * Created by Biri Infotech on 12/17/2017.
 */

public class ContactsPojo implements Serializable{

    String name;
    String contact;
    String interest1;
    String interest2;
    String interest3;
    String createdAt;
    String id;
    String contact_id;

    public ContactsPojo(){}
    public ContactsPojo(String name, String contact, String interest1, String interest2, String interest3) {
        this.name = name;
        this.contact = contact;
        this.interest1 = interest1;
        this.interest2 = interest2;
        this.interest3 = interest3;

    }

    public String getContact_id() {
        return contact_id;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getInterest1() {
        return interest1;
    }

    public void setInterest1(String interest1) {
        this.interest1 = interest1;
    }

    public String getInterest2() {
        return interest2;
    }

    public void setInterest2(String interest2) {
        this.interest2 = interest2;
    }

    public String getInterest3() {
        return interest3;
    }

    public void setInterest3(String interest3) {
        this.interest3 = interest3;
    }


}
