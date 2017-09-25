package com.ritam.splitthebill.splitthebill.settergetter;

/**
 * Created by ritam on 25/04/17.
 */

public class Contacts_SetterGetter {

    String Name,Number,photo_uri;
    boolean checkStatus;

    public Contacts_SetterGetter() {
    }

    public Contacts_SetterGetter(String name, String number) {
        Name = name;
        Number = number;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getPhoto_uri() {
        return photo_uri;
    }

    public void setPhoto_uri(String photo_uri) {
        this.photo_uri = photo_uri;
    }

    public boolean getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(boolean checkStatus) {
        this.checkStatus = checkStatus;
    }
}
