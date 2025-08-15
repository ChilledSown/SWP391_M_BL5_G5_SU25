package model;

public class User {

    private Long user_id;
    private String fullName;
    private String avataUrl;
    private String phone;
    private String address;
    private String email;
    private String passwordHash;
    private String role;

    public User() {
    }

    public User(Long user_id, String fullName, String avataUrl, String phone, String address, String email, String passwordHash, String role) {
        this.user_id = user_id;
        this.fullName = fullName;
        this.avataUrl = avataUrl;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }
    
    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    @Override
    public String toString() {
        return "User{" + "user_id=" + user_id + ", fullName=" + fullName + ", avataUrl=" + avataUrl + ", phone=" + phone + ", address=" + address + ", email=" + email + ", passwordHash=" + passwordHash + ", role=" + role + '}';
    }
    
    
}
