package com.basejava.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {

    private final List<TextSection> textList;

    public ListSection(List<TextSection> textList) {
        this.textList = textList;
    }

    public List<TextSection> getTextList() {
        return textList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(textList, that.textList);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + (textList == null ? 0 : textList.hashCode());
        return hash;
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "textList=" + textList + '\n' +
                '}';
    }
}