package com.ivymobi.abb.pw.beans;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "collections_files")
public class CollectionFile extends Model {

    @Column(name = "collection")
    public Collection collection;

    @Column(name = "file")
    public File file;

    public static CollectionFile findBy(Collection collection, File file) {
        return new Select()
                .from(CollectionFile.class)
                .where("collection = ?", collection.getId())
                .where("file = ?", file.getId())
                .executeSingle();
    }

    public static List<CollectionFile> findByFile(File file) {
        return new Select()
                .from(CollectionFile.class)
                .where("file = ?", file.getId())
                .execute();
    }

    public static List<CollectionFile> findByCollection(Collection collection) {
        return new Select()
                .from(CollectionFile.class)
                .where("collection = ?", collection.getId())
                .execute();
    }
}
