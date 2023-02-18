package com.radien;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 11:39 2022/3/22
 * @Modified By:
 */
public class UserTest {
    private String test1;
    private transient String test2;

    public String getTest1() {
        return test1;
    }

    public void setTest1(String test1) {
        this.test1 = test1;
    }

    public String getTest2() {
        return test2;
    }

    public void setTest2(String test2) {
        this.test2 = test2;
    }

    @Override
    public String toString() {
        return "UserTest{" +
                "test1='" + test1 + '\'' +
                ", test2='" + test2 + '\'' +
                '}';
    }
}
