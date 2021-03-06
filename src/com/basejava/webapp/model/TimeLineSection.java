package com.basejava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class TimeLineSection extends AbstractSection {

    private final static long serialVersionUID = 1L;
    private List<TimeLine> listTimeLine;

    public TimeLineSection() {
    }

    public TimeLineSection(TimeLine ...timeLines ){
        this(Arrays.asList(timeLines));
    }
    public TimeLineSection(List<TimeLine> listTimeLine) {
        this.listTimeLine = listTimeLine;
    }

    public List<TimeLine> getListTimeLine() {
        return listTimeLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeLineSection that = (TimeLineSection) o;
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
