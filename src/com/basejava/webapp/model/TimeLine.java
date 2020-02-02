package com.basejava.webapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TimeLine extends AbstractSection {

    private final static long serialVersionUID = 1L;
    private final Link homePage;
    private List<Item> listItem;

    public TimeLine(String name, String url, ArrayList<Item> listItem) {
        this.homePage = new Link(name, url);
        this.listItem = listItem;
    }

    public static class Item implements Serializable{

        private final static long serialVersionUID = 1L;
       // DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/YYYY");
        private final LocalDate startDate;
        private final LocalDate lastDate;
        private final String activity;
        private final String description;



        public Item(LocalDate startDate, LocalDate lastDate, String activity, String description) {
            this.startDate = startDate;
            this.lastDate = lastDate;
            this.activity = activity;
            this.description = description;
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

        if (homePage != null ? !homePage.equals(timeLine.homePage) : timeLine.homePage != null) return false;
        return listItem != null ? listItem.equals(timeLine.listItem) : timeLine.listItem == null;
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