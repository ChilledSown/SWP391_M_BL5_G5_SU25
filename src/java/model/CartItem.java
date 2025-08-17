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
public class CartItem {
    private Long cart_item_id;
    private Long cart_id;
    private Long course_id;
    private double price;
    private Date created_at;
    private Date updated_at;
    public CartItem() {
    }

    public CartItem(Long cart_item_id, Long cart_id, Long course_id, double price, Date created_at, Date updated_at) {
        this.cart_item_id = cart_item_id;
        this.cart_id = cart_id;
        this.course_id = course_id;
        this.price = price;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Long getCart_item_id() {
        return cart_item_id;
    }

    public Long getCart_id() {
        return cart_id;
    }

    public Long getCourse_id() {
        return course_id;
    }

    public double getPrice() {
        return price;
    }

    public void setCart_item_id(Long cart_item_id) {
        this.cart_item_id = cart_item_id;
    }

    public void setCart_id(Long cart_id) {
        this.cart_id = cart_id;
    }

    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CartItem{" + "cart_item_id=" + cart_item_id + ", cart_id=" + cart_id + ", course_id=" + course_id + ", price=" + price + ", quantity=" + '}';
    }
    
    
}
