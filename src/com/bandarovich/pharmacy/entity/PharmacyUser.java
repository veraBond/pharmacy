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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PharmacyUser user = (PharmacyUser) o;

        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (mail != null ? !mail.equals(user.mail) : user.mail != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        return position == user.position;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PharmacyUser{" + "name='" + name + '\'' + ", mail='" + mail + '\'' + ", password='" + password + '\'' + ", position=" + position + '}';
    }
}
