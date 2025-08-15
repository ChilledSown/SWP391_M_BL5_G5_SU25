package model;

public class Topic {
    private Long topic_id;
    private String name;
    private String thumbnail_Url;
    private String description;

    public Topic() {
    }

    public Topic(Long topic_id, String name, String thumbnail_Url, String description) {
        this.topic_id = topic_id;
        this.name = name;
        this.thumbnail_Url = thumbnail_Url;
        this.description = description;
    }

    public Long getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(Long topic_id) {
        this.topic_id = topic_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail_Url() {
        return thumbnail_Url;
    }

    public void setThumbnail_Url(String thumbnail_Url) {
        this.thumbnail_Url = thumbnail_Url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Topic{" + "topic_id=" + topic_id + ", name=" + name + ", thumbnail_Url=" + thumbnail_Url + ", description=" + description + '}';
    }
    
}