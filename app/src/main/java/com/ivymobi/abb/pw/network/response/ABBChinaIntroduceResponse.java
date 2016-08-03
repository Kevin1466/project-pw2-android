package com.ivymobi.abb.pw.network.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by renguangkai on 2016/7/22.
 */
public class ABBChinaIntroduceResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static class Item implements Serializable {
        private String name;
        private String objectId;
        private String createdAt;
        private String updatedAt;
        private int weight;
        private Schema schema;
        private Data data;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public Schema getSchema() {
            return schema;
        }

        public void setSchema(Schema schema) {
            this.schema = schema;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        private static class Schema implements Serializable {
            private String name;
            private String objectId;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getObjectId() {
                return objectId;
            }

            public void setObjectId(String objectId) {
                this.objectId = objectId;
            }
        }

        public static class Data implements Serializable {
            private String title;
            private String language;
            private String title_sub;
            private String content;
            private Video video;
            private Video more;

            public static class Video implements Serializable {
                private Cover cover;
                private Cover video;

                public Cover getCover() {
                    return cover;
                }

                public void setCover(Cover cover) {
                    this.cover = cover;
                }

                public Cover getVideo() {
                    return video;
                }

                public void setVideo(Cover video) {
                    this.video = video;
                }

                public static class Cover implements Serializable {
                    private String __type;
                    private String className;
                    private String objectId;
                    private String title;
                    private String description;
                    private String size;
                    private String mime_type;
                    private String file;
                    private String thumbnail;
                    private String createdAt;
                    private String updatedAt;

                    public String get__type() {
                        return __type;
                    }

                    public void set__type(String __type) {
                        this.__type = __type;
                    }

                    public String getClassName() {
                        return className;
                    }

                    public void setClassName(String className) {
                        this.className = className;
                    }

                    public String getObjectId() {
                        return objectId;
                    }

                    public void setObjectId(String objectId) {
                        this.objectId = objectId;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public String getDescription() {
                        return description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }

                    public String getSize() {
                        return size;
                    }

                    public void setSize(String size) {
                        this.size = size;
                    }

                    public String getMime_type() {
                        return mime_type;
                    }

                    public void setMime_type(String mime_type) {
                        this.mime_type = mime_type;
                    }

                    public String getFile() {
                        return file;
                    }

                    public void setFile(String file) {
                        this.file = file;
                    }

                    public String getThumbnail() {
                        return thumbnail;
                    }

                    public void setThumbnail(String thumbnail) {
                        this.thumbnail = thumbnail;
                    }

                    public String getCreatedAt() {
                        return createdAt;
                    }

                    public void setCreatedAt(String createdAt) {
                        this.createdAt = createdAt;
                    }

                    public String getUpdatedAt() {
                        return updatedAt;
                    }

                    public void setUpdatedAt(String updatedAt) {
                        this.updatedAt = updatedAt;
                    }
                }
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getLanguage() {
                return language;
            }

            public void setLanguage(String language) {
                this.language = language;
            }

            public String getTitle_sub() {
                return title_sub;
            }

            public void setTitle_sub(String title_sub) {
                this.title_sub = title_sub;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public Video getVideo() {
                return video;
            }

            public void setVideo(Video video) {
                this.video = video;
            }

            public Video getMore() {
                return more;
            }

            public void setMore(Video more) {
                this.more = more;
            }
        }
    }
}
