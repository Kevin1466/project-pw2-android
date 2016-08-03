package com.ivymobi.abb.pw.network.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by renguangkai on 2016/7/23.
 */
public class DocumentListResponse implements Serializable {
    private String name;

    private Data data;

    private Schema schema;

    private String objectId;

    private String createdAt;

    private String updatedAt;

    private List<Items> items ;

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setData(Data data){
        this.data = data;
    }
    public Data getData(){
        return this.data;
    }
    public void setSchema(Schema schema){
        this.schema = schema;
    }
    public Schema getSchema(){
        return this.schema;
    }
    public void setObjectId(String objectId){
        this.objectId = objectId;
    }
    public String getObjectId(){
        return this.objectId;
    }
    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }
    public String getCreatedAt(){
        return this.createdAt;
    }
    public void setUpdatedAt(String updatedAt){
        this.updatedAt = updatedAt;
    }
    public String getUpdatedAt(){
        return this.updatedAt;
    }
    public void setItems(List<Items> items){
        this.items = items;
    }
    public List<Items> getItems(){
        return this.items;
    }

    private static class Schema implements Serializable {
        private String name;

        private String objectId;

        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setObjectId(String objectId){
            this.objectId = objectId;
        }
        public String getObjectId(){
            return this.objectId;
        }

    }

    private static class Data implements Serializable {
        private String title;

        private String description;

        public void setTitle(String title){
            this.title = title;
        }
        public String getTitle(){
            return this.title;
        }
        public void setDescription(String description){
            this.description = description;
        }
        public String getDescription(){
            return this.description;
        }

    }

    public static class Items implements Serializable {
        private String name;

        private Data data;

        private Schema schema;

        private String objectId;

        private String createdAt;

        private String updatedAt;

        private int weight;

        private List<ItemsInner> items ;

        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setData(Data data){
            this.data = data;
        }
        public Data getData(){
            return this.data;
        }
        public void setSchema(Schema schema){
            this.schema = schema;
        }
        public Schema getSchema(){
            return this.schema;
        }
        public void setObjectId(String objectId){
            this.objectId = objectId;
        }
        public String getObjectId(){
            return this.objectId;
        }
        public void setCreatedAt(String createdAt){
            this.createdAt = createdAt;
        }
        public String getCreatedAt(){
            return this.createdAt;
        }
        public void setUpdatedAt(String updatedAt){
            this.updatedAt = updatedAt;
        }
        public String getUpdatedAt(){
            return this.updatedAt;
        }
        public void setWeight(int weight){
            this.weight = weight;
        }
        public int getWeight(){
            return this.weight;
        }
        public void setItems(List<ItemsInner> items){
            this.items = items;
        }
        public List<ItemsInner> getItems(){
            return this.items;
        }

        private static class Schema implements Serializable {
            private String name;

            private String objectId;

            public void setName(String name){
                this.name = name;
            }
            public String getName(){
                return this.name;
            }
            public void setObjectId(String objectId){
                this.objectId = objectId;
            }
            public String getObjectId(){
                return this.objectId;
            }

        }

        private static class Data implements Serializable {
            private String title;

            private String description;

            public void setTitle(String title){
                this.title = title;
            }
            public String getTitle(){
                return this.title;
            }
            public void setDescription(String description){
                this.description = description;
            }
            public String getDescription(){
                return this.description;
            }

        }

        public static class ItemsInner implements Serializable {
            private String name;

            private Data data;

            private Schema schema;

            private String objectId;

            private String createdAt;

            private String updatedAt;

            private int weight;

            private List<ItemsInnerInner> items ;

            public void setName(String name){
                this.name = name;
            }
            public String getName(){
                return this.name;
            }
            public void setData(Data data){
                this.data = data;
            }
            public Data getData(){
                return this.data;
            }
            public void setSchema(Schema schema){
                this.schema = schema;
            }
            public Schema getSchema(){
                return this.schema;
            }
            public void setObjectId(String objectId){
                this.objectId = objectId;
            }
            public String getObjectId(){
                return this.objectId;
            }
            public void setCreatedAt(String createdAt){
                this.createdAt = createdAt;
            }
            public String getCreatedAt(){
                return this.createdAt;
            }
            public void setUpdatedAt(String updatedAt){
                this.updatedAt = updatedAt;
            }
            public String getUpdatedAt(){
                return this.updatedAt;
            }
            public void setWeight(int weight){
                this.weight = weight;
            }
            public int getWeight(){
                return this.weight;
            }
            public void setItems(List<ItemsInnerInner> items){
                this.items = items;
            }
            public List<ItemsInnerInner> getItems(){
                return this.items;
            }

            private static class Data implements Serializable {
                private String title;
                private String description;

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
            }

            private static class Schema implements Serializable {
                private String name;

                private String objectId;

                public void setName(String name){
                    this.name = name;
                }
                public String getName(){
                    return this.name;
                }
                public void setObjectId(String objectId){
                    this.objectId = objectId;
                }
                public String getObjectId(){
                    return this.objectId;
                }

            }

            public static class ItemsInnerInner implements Serializable {
                private String name;
                private Data data;

                public static class Data implements Serializable {
                    private String title;

                    private String description;

                    private Asset asset;

                    public class Asset implements Serializable {
                        private String __type;

                        private String className;

                        private String objectId;

                        private String title;

                        private String description;

                        private int size;

                        private String mime_type;

                        private String file;

                        private String thumbnail;

                        private String createdAt;

                        private String updatedAt;

                        public void set__type(String __type){
                            this.__type = __type;
                        }
                        public String get__type(){
                            return this.__type;
                        }
                        public void setClassName(String className){
                            this.className = className;
                        }
                        public String getClassName(){
                            return this.className;
                        }
                        public void setObjectId(String objectId){
                            this.objectId = objectId;
                        }
                        public String getObjectId(){
                            return this.objectId;
                        }
                        public void setTitle(String title){
                            this.title = title;
                        }
                        public String getTitle(){
                            return this.title;
                        }
                        public void setDescription(String description){
                            this.description = description;
                        }
                        public String getDescription(){
                            return this.description;
                        }
                        public void setSize(int size){
                            this.size = size;
                        }
                        public int getSize(){
                            return this.size;
                        }
                        public void setMime_type(String mime_type){
                            this.mime_type = mime_type;
                        }
                        public String getMime_type(){
                            return this.mime_type;
                        }
                        public void setFile(String file){
                            this.file = file;
                        }
                        public String getFile(){
                            return this.file;
                        }
                        public void setThumbnail(String thumbnail){
                            this.thumbnail = thumbnail;
                        }
                        public String getThumbnail(){
                            return this.thumbnail;
                        }
                        public void setCreatedAt(String createdAt){
                            this.createdAt = createdAt;
                        }
                        public String getCreatedAt(){
                            return this.createdAt;
                        }
                        public void setUpdatedAt(String updatedAt){
                            this.updatedAt = updatedAt;
                        }
                        public String getUpdatedAt(){
                            return this.updatedAt;
                        }

                    }

                    public void setTitle(String title){
                        this.title = title;
                    }
                    public String getTitle(){
                        return this.title;
                    }
                    public void setDescription(String description){
                        this.description = description;
                    }
                    public String getDescription(){
                        return this.description;
                    }
                    public void setAsset(Asset asset){
                        this.asset = asset;
                    }
                    public Asset getAsset(){
                        return this.asset;
                    }

                }
            }

        }

    }
}
