package com.king.mvvmframe.example.bean;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class Bean {

    private String id;

    private String name;

    public Bean() {
    }

    public Bean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
