package co.id.GoGolf.models.response.login;

/**
 * Created by dedepradana on 6/7/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class Token {
    private String token;

    private String token_secret;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken_secret() {
        return token_secret;
    }

    public void setToken_secret(String token_secret) {
        this.token_secret = token_secret;
    }

    @Override
    public String toString() {
        return "ClassPojo [token = " + token + ", token_secret = " + token_secret + "]";
    }
}
