package com.ivymobi.abb.pw.network.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by renguangkai on 2016/7/23.
 */
public class SuccessCaseResponse implements Serializable {

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

    public static class Data implements Serializable {

        private String title;

        private String language;

        private int order;

        private String content;

        private Cover cover;

        public void setTitle(String title){
            this.title = title;
        }
        public String getTitle(){
            return this.title;
        }
        public void setLanguage(String language){
            this.language = language;
        }
        public String getLanguage(){
            return this.language;
        }
        public void setOrder(int order){
            this.order = order;
        }
        public int getOrder(){
            return this.order;
        }
        public void setContent(String content){
            this.content = content;
        }
        public String getContent(){
            return this.content;
        }
        public void setCover(Cover cover){
            this.cover = cover;
        }
        public Cover getCover(){
            return this.cover;
        }

        public static class Cover implements Serializable {
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

    }

    public static class Items implements Serializable {
        private String name;

        private Data data;

        private Schema schema;

        private String objectId;

        private String createdAt;

        private String updatedAt;

        private int weight;

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

    }

}
