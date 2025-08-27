package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sondo
 */
public class OrderDetail {
    private Long order_detail_id;
    private double price;
    private Long order_id;
    private Long course_id;

    public OrderDetail() {
    }

    public OrderDetail(Long order_detail_id, double price, Long order_id, Long course_id) {
        this.order_detail_id = order_detail_id;
        this.price = price;
        this.order_id = order_id;
        this.course_id = course_id;
    }

    public Long getOrder_detail_id() {
        return order_detail_id;
    }

    public double getPrice() {
        return price;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public Long getCourse_id() {
        return course_id;
    }

    public void setOrder_detail_id(Long order_detail_id) {
        this.order_detail_id = order_detail_id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
    }
    
}
