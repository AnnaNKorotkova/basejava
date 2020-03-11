package com.basejava.webapp.model;

import com.basejava.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class TimeLine extends AbstractSection {

    private final static long serialVersionUID = 1L;
    private Link homePage;
    private List<Item> listItem;
    public static final TimeLine EMPTY = new TimeLine("", "", Item.EMPTY);

    public TimeLine() {
    }

    public TimeLine(String name, String url, Item... items) {
        this.homePage = new Link(name, url);
        this.listItem = Arrays.asList(items);
    }

    public TimeLine(String name, String url, List<Item> listItem) {
        this.homePage = new Link(name, url);
        this.listItem = listItem;
    }

    public TimeLine(Link homePage, List<Item> listItem) {
        this.homePage = homePage;
        this.listItem = listItem;
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Item implements Serializable {

        private final static long serialVersionUID = 1L;
        public static final Item EMPTY = new Item();

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate lastDate;
        private String activity;
        private String description;

        public Item() {
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getLastDate() {
            return lastDate;
        }

        public String getActivity() {
            return activity;
        }

        public String getDescription() {
            return description;
        }

        public Item(LocalDate startDate, LocalDate lastDate, String activity, String description) {
            this.startDate = startDate;
            this.lastDate = lastDate;
            this.activity = activity;
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Item item = (Item) o;
            return !Objects.equals(startDate, item.startDate) ||
                    !Objects.equals(lastDate, item.lastDate) ||
                    !Objects.equals(activity, item.activity) ||
                    item.description == null || description.equals(item.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, lastDate, activity, description);
        }

        @Override
        public String toString() {
            return "Item{" +
                    "startDate=" + startDate + '\n' +
                    ", lastDate=" + lastDate + '\n' +
                    ", activity='" + activity + '\'' + '\n' +
                    ", description='" + description + '\'' + '\n' +
                    '}';
        }
    }

    public Link getHomePage() {
        return homePage;
    }


    public List<Item> getListItem() {
        return listItem;
    }

    public void setListItem(List<Item> listItem) {
        this.listItem = listItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeLine timeLine = (TimeLine) o;

        if (!Objects.equals(homePage, timeLine.homePage)) return false;
        return Objects.equals(listItem, timeLine.listItem);
    }

    @Override
    public int hashCode() {
        int result = homePage != null ? homePage.hashCode() : 0;
        result = 31 * result + (listItem != null ? listItem.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TimeLine{" +
                "homePage=" + homePage + '\n' +
                ", listItem=" + listItem + '\n' +
                '}';
    }
}