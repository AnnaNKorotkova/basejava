package com.basejava.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListTimeLineSection extends AbstractResumeSection {

    private final List<TimeLineSection> listTimeLine;

    public ListTimeLineSection(List<TimeLineSection> listTimeLine) {
        this.listTimeLine = listTimeLine;
    }

    public List<TimeLineSection> getListTimeLine() {
        return listTimeLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListTimeLineSection that = (ListTimeLineSection) o;
        return Objects.equals(listTimeLine, that.listTimeLine);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + listTimeLine.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return "ListTimeLineSection{" +
                "listTimeLine=" + listTimeLine +
                '}';
    }
}
