package model;

public class Topic {
    private Long topic_id;
    private String name;
    private String description;

    public Topic() {
    }

    public Topic(Long topic_id, String name, String description) {
        this.topic_id = topic_id;
        this.name = name;
        this.description = description;
    }

    public Long getTopicId() {
        return topic_id;
    }

    public void setTopicId(Long topic_id) {
        this.topic_id = topic_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Topic{" + "topicId=" + topic_id + ", name=" + name + ", description=" + description + '}';
    }
}