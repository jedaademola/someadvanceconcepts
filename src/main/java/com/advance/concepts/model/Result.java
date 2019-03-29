package com.advance.concepts.model;

public class Result {


    private String user;
    private String desc;
    private String subject;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return String.format(
                "Todo [ user=%s, desc=%s, subject=%s]",
                user, desc, subject);
    }

}
