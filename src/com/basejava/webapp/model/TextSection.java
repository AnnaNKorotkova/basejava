package com.basejava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class TextSection extends AbstractSection {

    private final static long serialVersionUID = 1L;
    private String textContainer;
    public static final TextSection EMPTY = new TextSection();

    public TextSection() {
    }

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
        int hash = 17;
        hash = hash * 37 + (textContainer == null ? 0 : textContainer.hashCode());
        return hash;
    }

    @Override
    public String toString() {
        return "TextSection{" +
                "textContainer='" + textContainer + '\'' + '\n' +
                '}';
    }
}
