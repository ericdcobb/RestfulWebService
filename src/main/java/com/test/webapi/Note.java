package com.test.webapi;


public class Note {

    private Integer id;
    private String body;

    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) {
        this.id = id;
    }
}
