package com.example.EVeteranBot.service;

public class User {
    private long chatID;
    private String phoneNumber;
    private String surname;
    private String name;
    private String sureName;
    private String birthDate;
    private String region;
    private String categoryOfUser;

    public User() {
    }

//    public User(long chatID, int phoneNumber, String surname, String name, String fatherName, int age, String region, String categoryOfUser) {
//        this.chatID = chatID;
//        this.phoneNumber = phoneNumber;
//        this.surname = surname;
//        this.name = name;
//        this.fatherName = fatherName;
//        this.age = age;
//        this.region = region;
//        this.categoryOfUser = categoryOfUser;
//    }

    public void setChatID(long chatID) {
        this.chatID = chatID;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFatherName(String fatherName) {
        this.sureName = fatherName;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setCategoryOfUser(String categoryOfUser) {
        this.categoryOfUser = categoryOfUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "chatID=" + chatID +
                ", phoneNumber=" + phoneNumber +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", fatherName='" + sureName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", region='" + region + '\'' +
                ", categoryOfUser='" + categoryOfUser + '\'' +
                '}';
    }

    public long getChatID() {
        return chatID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getSureName() {
        return sureName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getRegion() {
        return region;
    }

    public String getCategoryOfUser() {
        return categoryOfUser;
    }
}
