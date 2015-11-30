package com.ivymobi.abb.pw.beans;

import java.util.ArrayList;

public class Catalog {
    private String uuid;
    private String name;
    private String enName;
    private ArrayList<Catalog> children = new ArrayList<>();
    private ArrayList<File> files = new ArrayList<>();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public ArrayList<Catalog> getChildren() {
        return children;
    }

    public boolean hasChildren() {
        return !this.children.isEmpty();
    }

    public void addChildren(Catalog catalog) {
        this.children.add(catalog);
    }

    public void setChildren(ArrayList<Catalog> children) {
        this.children = children;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public boolean hasFiles() {
        return !this.files.isEmpty();
    }

    public void addFile(File file) {
        this.files.add(file);
    }
}
