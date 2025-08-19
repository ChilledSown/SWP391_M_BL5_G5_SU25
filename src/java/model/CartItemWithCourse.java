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
public class CartItemWithCourse {
    private CartItem cartItem;
    private Course course;
    
    public CartItemWithCourse() {
    }
    
    public CartItemWithCourse(CartItem cartItem, Course course) {
        this.cartItem = cartItem;
        this.course = course;
    }
    
    public CartItem getCartItem() {
        return cartItem;
    }
    
    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }
    
    public Course getCourse() {
        return course;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }
    
    // Convenience methods to access cart item properties
    public Long getCart_item_id() {
        return cartItem.getCart_item_id();
    }
    
    public Long getCart_id() {
        return cartItem.getCart_id();
    }
    
    public Long getCourse_id() {
        return cartItem.getCourse_id();
    }
    
    public double getPrice() {
        return cartItem.getPrice();
    }
    
    public Date getCreated_at() {
        return cartItem.getCreated_at();
    }
    
    public Date getUpdated_at() {
        return cartItem.getUpdated_at();
    }
    
    // Convenience methods to access course properties
    public String getCourseTitle() {
        return course.getTitle();
    }
    
    public String getCourseDescription() {
        return course.getDescription();
    }
    
    public String getCourseThumbnail() {
        return course.getThumbnail_url();
    }
}

