package com.radien;

import java.util.List;

public class Permissions {
    private String name;
    private List<String> ids;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "Permissions{" +
                "name='" + name + '\'' +
                ", ids=" + ids +
                '}';
    }
}
