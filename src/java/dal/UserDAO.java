package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;

public class UserDAO extends DBContext {

    public User login(String email, String password) {
        String sql = "SELECT * FROM Users WHERE Email = ? AND PasswordHash = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUser_id(rs.getLong("UserID"));
                u.setFullName(rs.getString("FullName"));
                u.setAvataUrl(rs.getString("Avata_Url"));
                u.setPhone(rs.getString("Phone"));
                u.setAddress(rs.getString("Address"));
                u.setEmail(rs.getString("Email"));
                u.setPasswordHash(rs.getString("PasswordHash"));
                u.setRole(rs.getString("Role"));
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE Email = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUser_id(rs.getLong("UserID"));
                u.setFullName(rs.getString("FullName"));
                u.setAvataUrl(rs.getString("Avata_Url"));
                u.setPhone(rs.getString("Phone"));
                u.setAddress(rs.getString("Address"));
                u.setEmail(rs.getString("Email"));
                u.setPasswordHash(rs.getString("PasswordHash"));
                u.setRole(rs.getString("Role"));
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public User getUserByPhone(String phone) {
    String sql = "SELECT * FROM Users WHERE Phone = ?";
    try {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, phone);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            User u = new User();
            u.setUser_id(rs.getLong("UserID"));
            u.setFullName(rs.getString("FullName"));
            u.setAvataUrl(rs.getString("Avata_Url"));
            u.setPhone(rs.getString("Phone"));
            u.setAddress(rs.getString("Address"));
            u.setEmail(rs.getString("Email"));
            u.setPasswordHash(rs.getString("PasswordHash"));
            u.setRole(rs.getString("Role"));
            return u;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}


    public boolean insertUser(User user) {
        String sql = "INSERT INTO Users (FullName, Avata_Url, Phone, Address, Email, PasswordHash, Role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getAvataUrl());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getAddress());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getPasswordHash());
            ps.setString(7, user.getRole());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
