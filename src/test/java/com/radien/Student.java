package com.radien;

public class Student<T> {
    private String name;
    private T code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getCode() {
        return code;
    }

    public void setCode(T id) {
        this.code = id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", id=" + code +
                '}';
    }
}
