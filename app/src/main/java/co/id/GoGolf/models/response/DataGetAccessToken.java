package co.id.GoGolf.models.response;

/**
 * Created by dedepradana on 6/18/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class DataGetAccessToken {
    private String oauth_token;
    private String oauth_token_secret;

    public String getOauth_token() {
        return oauth_token;
    }

    public void setOauth_token(String oauth_token) {
        this.oauth_token = oauth_token;
    }

    public String getOauth_token_secret() {
        return oauth_token_secret;
    }

    public void setOauth_token_secret(String oauth_token_secret) {
        this.oauth_token_secret = oauth_token_secret;
    }
}
