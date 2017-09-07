package co.id.GoGolf.models.response.login;

/**
 * Created by dedepradana on 6/7/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class DataLogin {

    private String access_token;

    private User user;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public User getUser ()
    {
        return user;
    }

    public void setUser (User user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [user = "+user+"]";
    }
}
