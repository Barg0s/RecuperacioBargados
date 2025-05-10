package com.recuperacio;

public class Sessio {
    private static Sessio instance;
    private String username;

    private Sessio() {}

    public static Sessio getInstance() {
        if (instance == null) {
            instance = new Sessio();
        }
        return instance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

