package com.basejava.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractResumeSection {

    private final List<String> textList;

    public ListSection(List<String> textList) {
        this.textList = textList;
    }

    public List<String> getTextList() {
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
}
