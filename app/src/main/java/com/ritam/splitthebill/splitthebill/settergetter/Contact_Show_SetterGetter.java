package com.ritam.splitthebill.splitthebill.settergetter;

/**
 * Created by ritam on 13/09/17.
 */

public class Contact_Show_SetterGetter {

String Email,Number,Name;

    public Contact_Show_SetterGetter(String email, String number, String name) {
        Email = email;
        Number = number;
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }
}
