package com.ivymobi.abb.pw.network;

/**
 * Created by renguangkai on 2016/7/23.
 */
public enum CachedFileEnum {
    // name1存储Etag，name2存储是否有内容更新，name3存储是否被查看过
    BUSINESS_ABB_INTRO("BUSINESS_ABB_INTRO", "UPDATE_BUSINESS_ABB_INTRO", "READ_BUSINESS_ABB_INTRO"),
    BUSINESS_ABB_POWER_INTRO("BUSINESS_ABB_POWER_INTRO", "UPDATE_BUSINESS_ABB_POWER_INTRO", "READ_BUSINESS_ABB_POWER_INTRO"),
    BUSINESS_CASE("BUSINESS_CASE", "UPDATE_BUSINESS_CASE", "READ_BUSINESS_CASE"),
    BUSINESS_LOCAL("BUSINESS_LOCAL", "UPDATE_BUSINESS_LOCAL", "READ_BUSINESS_LOCAL"),
    ACTIVITY("ACTIVITY", "UPDATE_ACTIVITY", "READ_ACTIVITY"),
    DOCUMENT_CENTER("DOCUMENT_CENTER", "UPDATE_DOCUMENT_CENTER", "READ_DOCUMENT_CENTER");

    private String nameEtag, nameUpdate, nameRead;

    CachedFileEnum(String nameEtag, String nameUpdate, String nameRead) {
        this.nameEtag = nameEtag;
        this.nameUpdate = nameUpdate;
        this.nameRead = nameRead;
    }

    public String getNameEtag() {
        return nameEtag;
    }

    public void setNameEtag(String nameEtag) {
        this.nameEtag = nameEtag;
    }

    public String getNameUpdate() {
        return nameUpdate;
    }

    public void setNameUpdate(String nameUpdate) {
        this.nameUpdate = nameUpdate;
    }

    public String getNameRead() {
        return nameRead;
    }

    public void setNameRead(String nameRead) {
        this.nameRead = nameRead;
    }
}
