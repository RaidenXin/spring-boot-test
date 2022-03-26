package com.raiden.model;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 22:21 2022/1/12
 * @Modified By:
 */
public class Grade {

    private int id;
    private String userName;
    private String course;
    private String score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
