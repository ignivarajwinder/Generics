package com.example.roomexample.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "user")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @ColumnInfo(name = "interest_1")
    private String interest_1;

    @ColumnInfo(name = "interest_2")
    private String interest_2;

    @ColumnInfo(name = "interest_3")
    private String interest_3;

//    @ColumnInfo(name = "age")
//    private int age;

    @ColumnInfo(name = "contact")
    private String contact;

    public String getInterest_1() {
        return interest_1;
    }

    public void setInterest_1(String interest_1) {
        this.interest_1 = interest_1;
    }

    public String getInterest_2() {
        return interest_2;
    }

    public void setInterest_2(String interest_2) {
        this.interest_2 = interest_2;
    }

    public String getInterest_3() {
        return interest_3;
    }

    public void setInterest_3(String interest_3) {
        this.interest_3 = interest_3;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }

}