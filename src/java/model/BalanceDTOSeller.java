package model;

import java.sql.Timestamp;

/**
 * Represents a balance transaction for a seller.
 */
public class BalanceDTOSeller {
    private int orderId;
    private Timestamp orderDate;
    private int courseId;
    private String courseName;
    private double amount;
    private String paymentStatus;
    private String paymentMethod;

    public BalanceDTOSeller(int orderId, Timestamp orderDate, int courseId, String courseName, double amount, String paymentStatus, String paymentMethod) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.courseId = courseId;
        this.courseName = courseName;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDescription() {
        return "Course Sale: " + courseName + " (Order #" + orderId + ")";
    }
}