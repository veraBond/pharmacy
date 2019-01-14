package com.bandarovich.pharmacy.entity;

public class PharmacyUser extends Pharmacy {
    private String name;
    private String mail;
    private String password;
    private PharmacyPosition position;

    public PharmacyUser(PharmacyPosition position, String name, String mail, String password) {
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PharmacyPosition getPosition() {
        return position;
    }

    public void setPosition(PharmacyPosition position) {
        this.position = position;
    }

}
