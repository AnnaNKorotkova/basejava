package com.basejava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class ListSection extends AbstractSection {

    private final static long serialVersionUID = 1L;
    private List<String> textList;
    public static final ListSection EMPTY = new ListSection(Collections.singletonList(""));

    public ListSection() {
    }

    public ListSection(List<String> textList) {
        this.textList = textList;
    }

    public List<String> getTextList() {
        return textList;
    }

    public void addToList(String str) {
       textList.add(str);
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