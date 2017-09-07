package co.id.GoGolf.events;

/**
 * Created by dedepradana on 4/8/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class ErrorEvent {

    private String message;

    public ErrorEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
