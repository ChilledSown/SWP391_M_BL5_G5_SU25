
package model;

import java.time.LocalDate;


/**
 * Represents a Payment entity corresponding to the Payment table in the
 * database.
 */
public class Payment {

    private long paymentId;
    private long orderId;
    private String paymentMethod;
    private String paymentStatus;
    private LocalDate paymentDate;
    private double amount;
    private String description; // For transaction display in balance.jsp

    // Constructor
    public Payment(long paymentId, long orderId, String paymentMethod, String paymentStatus, LocalDate paymentDate, double amount, String description) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.description = description;
    }

    // Getters and setters
    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Payment{"
                + "paymentId=" + paymentId
                + ", orderId=" + orderId
                + ", paymentMethod='" + paymentMethod + '\''
                + ", paymentStatus='" + paymentStatus + '\''
                + ", paymentDate=" + paymentDate
                + ", amount=" + amount
                + ", description='" + description + '\''
                + '}';
    }

}
