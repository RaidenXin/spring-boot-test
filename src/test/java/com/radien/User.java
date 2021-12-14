package com.radien;

import java.util.Objects;

public class User<T>{

    private String name;
    private T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                "t='" + t +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User<?> user = (User<?>) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(t, user.t);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, t);
    }

}
