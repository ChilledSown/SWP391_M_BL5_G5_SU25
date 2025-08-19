/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author sondo
 */
public class Slider {
    private Long slider_id;
    private String title;
    private String image_url;
    private Date created_at;
    private Date updated_at;

    public Slider() {
    }

    public Slider(Long slider_id, String title, String image_url, Date created_at, Date updated_at) {
        this.slider_id = slider_id;
        this.title = title;
        this.image_url = image_url;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Long getSlider_id() {
        return slider_id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage_url() {
        return image_url;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setSlider_id(Long slider_id) {
        this.slider_id = slider_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Slider{" + "slider_id=" + slider_id + ", title=" + title + ", image_url=" + image_url + ", created_at=" + created_at + ", updated_at=" + updated_at + '}';
    }
    
}
