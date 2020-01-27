package com.basejava.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class TimeLine extends AbstractSection {

    private final Link homePage;
    private final LocalDate startDate;
    private final LocalDate lastDate;
    private final String activity;
    private final String description;

    public TimeLine(String name,String url, LocalDate startDate, LocalDate lastDate, String activity, String description) {
        this.homePage = new Link(name, url);
        this.startDate = Objects.requireNonNull(startDate, "The start date can't be null");
        this.lastDate =  Objects.requireNonNull(lastDate, "The start date can't be null");;
        this.activity = Objects.requireNonNull(activity, "The start date can't be null");
        this.description = description;
    }

    public Link getHomePage() {
        return homePage;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeLine timeLine = (TimeLine) o;

        if (!homePage.equals(timeLine.homePage)) return false;
        if (!startDate.equals(timeLine.startDate)) return false;
        if (!lastDate.equals(timeLine.lastDate)) return false;
        if (!activity.equals(timeLine.activity)) return false;
        return description != null ? description.equals(timeLine.description) : timeLine.description == null;
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + lastDate.hashCode();
        result = 31 * result + activity.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        if (description == null) {
            return  '\n' + "TimeLineSection{" + '\n' +
                    "organisation='" + homePage.getName() + '\'' + '\n' +
                    ", startDate='" + startDate + '\'' + '\n' +
                    ", lastDate='" + lastDate + '\'' + '\n' +
                    ", activity='" + activity + '\'' + '\n' +
                    '}';
        } else {
            return  '\n' + "TimeLineSection{" +  '\n' +
                    "organisation='" + homePage.getName() + '\'' + '\n' +
                    ", startDate='" + startDate + '\'' + '\n' +
                    ", lastDate='" + lastDate + '\'' + '\n' +
                    ", activity='" + activity + '\'' + '\n' +
                    ", description='" + description + '\'' + '\n' +
                    '}';
        }
    }
}
