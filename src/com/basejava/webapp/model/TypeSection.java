package com.basejava.webapp.model;

public enum TypeSection {

    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    TypeSection(String title) {
        this.title = title;
    }

    private final String title;

    public String getName() {
        return title;
    }
 }
