package com.bandarovich.pharmacy.entity;

/**
 * The Class PharmacyUser.
 */
public class PharmacyUser extends Pharmacy {
    
    /** The name. */
    private String name;
    
    /** The mail. */
    private String mail;
    
    /** The password. */
    private String password;
    
    /** The position. */
    private PharmacyPosition position;

    /**
     * Instantiates a new pharmacy user.
     *
     * @param position the position
     * @param name the name
     * @param mail the mail
     * @param password the password
     */
    public PharmacyUser(PharmacyPosition position, String name, String mail, String password) {
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.position = position;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the mail.
     *
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * Sets the mail.
     *
     * @param mail the new mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the position.
     *
     * @return the position
     */
    public PharmacyPosition getPosition() {
        return position;
    }

    /**
     * Sets the position.
     *
     * @param position the new position
     */
    public void setPosition(PharmacyPosition position) {
        this.position = position;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
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

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PharmacyUser{" + "name='" + name + '\'' + ", mail='" + mail + '\'' + ", password='" + password + '\'' + ", position=" + position + '}';
    }
}
