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
public class Order {

    private Long order_id;
    private Double total_amount;
    private String status;
    private Date created_at;
    private Long user_id;

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public String getStatus() {
        return status;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Long getUser_id() {
        return user_id;
    }

    public Order(Long order_id, Double total_amount, String status, Date created_at, Long user_id) {
        this.order_id = order_id;
        this.total_amount = total_amount;
        this.status = status;
        this.created_at = created_at;
        this.user_id = user_id;
    }

    public Order() {
    }

}
