package com.app.launcher.hash.data.model;

public class NewsSource {

private String name;
private String sourceName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public String toString() {
        return "NewsSource{" +
                "name='" + name + '\'' +
                ", sourceName='" + sourceName + '\'' +
                '}';
    }
}