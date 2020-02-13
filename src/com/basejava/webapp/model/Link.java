package com.basejava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Link extends AbstractSection {

    private final static long serialVersionUID = 1L;
    private String name;
    private String url;

    public Link() {
    }

    public Link(String name, String url) {
        this.name = Objects.requireNonNull(name, "Name cn't be null");
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        if (link.url == null) {
            return Objects.equals(name, link.name);
        } else {
            return Objects.equals(name, link.name) && Objects.equals(url, link.url);
        }
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Link{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
