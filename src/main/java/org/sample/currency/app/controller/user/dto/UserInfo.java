package org.sample.currency.app.controller.user.dto;

/**
 * JSON-serializable DTO containing user data
 *
 * Created by Mohamed Mekkawy.
 */
public class UserInfo {

    private String userName;

    public UserInfo(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
