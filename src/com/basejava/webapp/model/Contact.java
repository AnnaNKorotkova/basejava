package com.basejava.webapp.model;

public enum Contact {

    PHONE("Тел."),
    SKYPE("Skype") {
        @Override
        public String toHtml0(String value) {
            return "<a href='skype:" + value + "'>" + value + "</a>";
        }
    },
    EMAIL("E-mail") {
        @Override
        public String toHtml0(String value) {
            return "<a href='mailto:" + value + "'>" + value + "</a>";
        }
    },
    SOCIAL_NETS("Соц. сети");

    Contact(String title) {
        this.title = title;
    }

    private final String title;

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return value == null ? "" : toHtml0(value);
    }
}
