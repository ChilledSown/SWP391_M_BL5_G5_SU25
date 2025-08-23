package model;

import java.sql.Timestamp;

public class User {

    private Long user_id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String avataUrl;
    private String phone;
    private String address;
    private String email;
    private String passwordHash;
    private String role;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String accountStatus;

    public User() {
    }

    public User(Long user_id, String firstName, String middleName, String lastName, String avataUrl, String phone, String address, String email, String passwordHash, String role, Timestamp createdAt, Timestamp updatedAt, String accountStatus) {
        this.user_id = user_id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.avataUrl = avataUrl;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.accountStatus = accountStatus;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvataUrl() {
        return avataUrl;
    }

    public void setAvataUrl(String avataUrl) {
        this.avataUrl = avataUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Override
    public String toString() {
        return "User{" + "user_id=" + user_id + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName + ", avataUrl=" + avataUrl + ", phone=" + phone + ", address=" + address + ", email=" + email + ", passwordHash=" + passwordHash + ", role=" + role + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", accountStatus=" + accountStatus + '}';
    }

   
    
    
}