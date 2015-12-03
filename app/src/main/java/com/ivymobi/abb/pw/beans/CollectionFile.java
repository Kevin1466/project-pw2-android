package com.ivymobi.abb.pw.beans;


import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "collections_files")
public class CollectionFile {

    @Column(name = "collection")
    public Collection collection;

    @Column(name = "file")
    public File file;
}
