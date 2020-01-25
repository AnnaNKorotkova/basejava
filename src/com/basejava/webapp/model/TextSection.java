package com.basejava.webapp.model;

import java.util.Objects;

public class TextSection extends AbstractResumeSection {

    private final String textContainer;

    public TextSection(String textContainer) {
        this.textContainer = textContainer;
    }

    public String getTextContainer() {
        return textContainer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return Objects.equals(textContainer, that.textContainer);
    }

    @Override
    public int hashCode() {
        int hash = 11;
        hash = hash * 37 + (textContainer == null ? 0 : textContainer.hashCode());
        return hash;
    }

    @Override
    public String toString() {
        return "TextSection{" +
                "textContainer='" + textContainer + '\'' +
                '}';
    }
}
