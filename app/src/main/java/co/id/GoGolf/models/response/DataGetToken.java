package co.id.GoGolf.models.response;

/**
 * Created by dedepradana on 6/5/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class DataGetToken {
    private String oauth_callback_confirmed;

    private String oauth_token;

    private String oauth_token_secret;

    private String oauth_token_ttl;

    public String getOauth_callback_confirmed ()
    {
        return oauth_callback_confirmed;
    }

    public void setOauth_callback_confirmed (String oauth_callback_confirmed)
    {
        this.oauth_callback_confirmed = oauth_callback_confirmed;
    }

    public String getOauth_token ()
    {
        return oauth_token;
    }

    public void setOauth_token (String oauth_token)
    {
        this.oauth_token = oauth_token;
    }

    public String getOauth_token_secret ()
    {
        return oauth_token_secret;
    }

    public void setOauth_token_secret (String oauth_token_secret)
    {
        this.oauth_token_secret = oauth_token_secret;
    }

    public String getOauth_token_ttl ()
    {
        return oauth_token_ttl;
    }

    public void setOauth_token_ttl (String oauth_token_ttl)
    {
        this.oauth_token_ttl = oauth_token_ttl;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [oauth_callback_confirmed = "+oauth_callback_confirmed+", oauth_token = "+oauth_token+", oauth_token_secret = "+oauth_token_secret+", oauth_token_ttl = "+oauth_token_ttl+"]";
    }
}
