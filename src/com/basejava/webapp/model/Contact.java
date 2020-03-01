package com.basejava.webapp.model;

public enum Contact {

    PHONE("Тел."),
    SKYPE("Skype"),
    EMAIL("E-mail"),
    SOCIAL_NETS("Соц. сети");

    Contact(String title) {
        this.title = title;
    }

    private final String title;

    public String getName() {
        return title;
    }
}
