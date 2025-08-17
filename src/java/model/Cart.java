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
public class Cart {
    private Long cart_id;
    private Long user_id;
    private String status;
    private Date created_at;
    private Date updated_at;

    public Cart() {
    }

    public Cart(Long cart_id, Long user_id, String status, Date created_at, Date updated_at) {
        this.cart_id = cart_id;
        this.user_id = user_id;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Long getCart_id() {
        return cart_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public String getStatus() {
        return status;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setCart_id(Long cart_id) {
        this.cart_id = cart_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Cart{" + "cart_id=" + cart_id + ", user_id=" + user_id + ", status=" + status + ", created_at=" + created_at + ", updated_at=" + updated_at + '}';
    }
    
}
