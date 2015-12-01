package com.ivymobi.abb.pw.beans;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Files")
public class File extends Model {
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "title")
    private String title;

    @Column(name = "enTitle")
    private String enTitle;

    @Column(name = "size")
    private Integer size;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "cover")
    private String cover;

    @Column(name = "tag")
    private String tag;

    @Column(name = "localPath")
    private String localPath;

    @Column(name = "collection")
    private Collection collection;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEnTitle() {
        return enTitle;
    }

    public void setEnTitle(String enTitle) {
        this.enTitle = enTitle;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getLocalPath() {
        return localPath;
    }

    public boolean isDownload() {
        return localPath != null;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public Collection getCollectio() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public static List<File> getAll(Collection collection) {
        return new Select()
                .from(File.class)
                .where("collection = ?", collection.getId())
                .orderBy("Id ASC")
                .execute();
    }

    public static File findByUuid(String uuid) {
        return new Select()
                .from(File.class)
                .where("uuid = ?", uuid)
                .executeSingle();
    }
}
