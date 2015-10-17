package org.sample.currency.app.controller.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * DTO used only for posting new users for creation
 *
 * Created by Mohamed Mekkawy.
 */
public class NewUser {

    private String username;
    private String email;
    private String plainTextPassword;
    @JsonFormat(pattern = "mm/dd/yyyy")
    private Date birthDate;

    public NewUser() {
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlainTextPassword() {
        return plainTextPassword;
    }

    public void setPlainTextPassword(String plainTextPassword) {
        this.plainTextPassword = plainTextPassword;
    }
}
