package com.radien;

public class Message<T,K> {
    private T user;
    private K student;

    public T getUser() {
        return user;
    }

    public void setUser(T user) {
        this.user = user;
    }

    public K getStudent() {
        return student;
    }

    public void setStudent(K student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Message{" +
                "user=" + user +
                ", student=" + student +
                '}';
    }
}
