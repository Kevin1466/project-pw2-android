package com.ivymobi.abb.pw.beans;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "collections")
public class Collection extends Model {

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<File> files() {
        return getMany(File.class, "collection");
    }
}
