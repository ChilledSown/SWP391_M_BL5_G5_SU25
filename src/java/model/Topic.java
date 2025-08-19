/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author sondo
 */
public class Topic {
    private Long topic_id;
    private String name;
    private String description;
    private String thumbnail_url;
    
    public Topic() {
    }
    
    public Topic(Long topic_id, String name, String description, String thumbnail_url) {
        this.topic_id = topic_id;
        this.name = name;
        this.description = description;
        this.thumbnail_url = thumbnail_url;
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getThumbnail_url() {
        return thumbnail_url;
    }
    
    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }
    
    @Override
    public String toString() {
        return "Topic{" + "topic_id=" + topic_id + ", name=" + name + ", description=" + description + ", thumbnail_url=" + thumbnail_url + '}';
    }
}