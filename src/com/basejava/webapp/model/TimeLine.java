package com.basejava.webapp.model;

import java.util.Objects;

public class TimeLine extends AbstractSection {

    private final String organisation;
    private final String startDate;
    private final String lastDate;
    private final String activity;
    private final String description;

    public TimeLine(String organisation, String startDate, String lastDate, String activity, String description) {
        this.organisation = Objects.requireNonNull(organisation, "You should fill organisation name");
        this.startDate = Objects.requireNonNull(startDate, "The start date can't be null");
        this.lastDate = lastDate;
        this.activity = activity;
        this.description = description;
    }

    public String getOrganisation() {
        return organisation;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getLastDate() {
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
        TimeLine that = (TimeLine) o;
        return Objects.equals(organisation, that.organisation) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(lastDate, that.lastDate) &&
                Objects.equals(activity, that.activity) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + organisation.hashCode();
        hash = hash * 31 + startDate.hashCode();
        hash = hash * 31 + (lastDate == null ? 0 : lastDate.hashCode());
        hash = hash * 31 + activity.hashCode();
        hash = hash * 31 + description == null ? 0 : description.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        if (description == null) {
            return  '\n' + "TimeLineSection{" + '\n' +
                    "organisation='" + organisation + '\'' + '\n' +
                    ", startDate='" + startDate + '\'' + '\n' +
                    ", lastDate='" + lastDate + '\'' + '\n' +
                    ", activity='" + activity + '\'' + '\n' +
                    '}';
        } else {
            return  '\n' + "TimeLineSection{" +  '\n' +
                    "organisation='" + organisation + '\'' + '\n' +
                    ", startDate='" + startDate + '\'' + '\n' +
                    ", lastDate='" + lastDate + '\'' + '\n' +
                    ", activity='" + activity + '\'' + '\n' +
                    ", description='" + description + '\'' + '\n' +
                    '}';
        }
    }
}
