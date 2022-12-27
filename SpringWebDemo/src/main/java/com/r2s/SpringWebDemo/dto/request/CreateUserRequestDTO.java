package com.r2s.SpringWebDemo.dto.request;

import java.io.Serializable;

public class CreateUserRequestDTO implements Serializable {

    private String firstName;

    private String lastName;

    private String phone;

    public CreateUserRequestDTO() {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
