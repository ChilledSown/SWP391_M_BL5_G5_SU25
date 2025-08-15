package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

    public User getUserById(long userId) {
        String sql = "SELECT * FROM Users WHERE UserID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, userId);
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

    public List<User> getUsers(int page, int pageSize) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users ORDER BY UserID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, (page - 1) * pageSize); // Offset starts from 0
            ps.setInt(2, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUser_id(rs.getLong("UserID"));
                u.setFullName(rs.getString("FullName"));
                u.setAvataUrl(rs.getString("Avata_Url"));
                u.setPhone(rs.getString("Phone"));
                u.setAddress(rs.getString("Address"));
                u.setEmail(rs.getString("Email"));
                u.setPasswordHash(rs.getString("PasswordHash"));
                u.setRole(rs.getString("Role"));
                users.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // New method: Get total number of users
    public int getTotalUsers() {
        String sql = "SELECT COUNT(*) FROM Users";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // New method: Update user
    public boolean updateUser(User user) {
        String sql = "UPDATE Users SET FullName = ?, Avata_Url = ?, Phone = ?, Address = ?, Email = ?, PasswordHash = ?, Role = ? WHERE UserID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getAvataUrl());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getAddress());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getPasswordHash());
            ps.setString(7, user.getRole());
            ps.setLong(8, user.getUser_id());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(long userId) {
        try {
            // 1. Xóa Order_Detail (con của Order)
            String sqlOrderDetail = "DELETE FROM Order_Detail WHERE Order_Id IN (SELECT Order_Id FROM [Order] WHERE User_Id = ?)";
            PreparedStatement psOrderDetail = connection.prepareStatement(sqlOrderDetail);
            psOrderDetail.setLong(1, userId);
            psOrderDetail.executeUpdate();

            // 2. Xóa Payment (con của Order)
            String sqlPayment = "DELETE FROM Payment WHERE Order_Id IN (SELECT Order_Id FROM [Order] WHERE User_Id = ?)";
            PreparedStatement psPayment = connection.prepareStatement(sqlPayment);
            psPayment.setLong(1, userId);
            psPayment.executeUpdate();

            // 3. Xóa [Order] (User_Id = userId)
            String sqlOrder = "DELETE FROM [Order] WHERE User_Id = ?";
            PreparedStatement psOrder = connection.prepareStatement(sqlOrder);
            psOrder.setLong(1, userId);
            psOrder.executeUpdate();

            // 4. Xóa Cart (User_Id = userId)
            String sqlCart = "DELETE FROM Cart WHERE User_Id = ?";
            PreparedStatement psCart = connection.prepareStatement(sqlCart);
            psCart.setLong(1, userId);
            psCart.executeUpdate();

            // 5. Xóa Review (User_Id = userId)
            String sqlReview = "DELETE FROM Review WHERE User_Id = ?";
            PreparedStatement psReview = connection.prepareStatement(sqlReview);
            psReview.setLong(1, userId);
            psReview.executeUpdate();

            // 6. Xóa Blog (Created_By = userId)
            String sqlBlog = "DELETE FROM Blog WHERE Created_By = ?";
            PreparedStatement psBlog = connection.prepareStatement(sqlBlog);
            psBlog.setLong(1, userId);
            psBlog.executeUpdate();

            // 7. Xóa PasswordResetTokens (UserID = userId)
            String sqlTokens = "DELETE FROM PasswordResetTokens WHERE UserID = ?";
            PreparedStatement psTokens = connection.prepareStatement(sqlTokens);
            psTokens.setLong(1, userId);
            psTokens.executeUpdate();

            // 8. Xóa Users (UserID = userId)
            String sqlUser = "DELETE FROM Users WHERE UserID = ?";
            PreparedStatement psUser = connection.prepareStatement(sqlUser);
            psUser.setLong(1, userId);
            return psUser.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> getUsersByName(String searchQuery, int page, int pageSize) {
        String sql = "SELECT * FROM Users WHERE LOWER(fullName) LIKE ? ORDER BY UserID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        List<User> users = new ArrayList<>();
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return users; // Trả về danh sách rỗng nếu không có từ khóa
        }
        // Làm sạch searchQuery: thay nhiều khoảng trắng bằng một khoảng trắng và loại bỏ ký tự đặc biệt
        searchQuery = searchQuery.trim().replaceAll("\\s+", " ").replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + searchQuery + "%");
            ps.setInt(2, (page - 1) * pageSize);
            ps.setInt(3, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User u = new User();
                    u.setUser_id(rs.getLong("UserID"));
                    u.setFullName(rs.getString("FullName"));
                    u.setAvataUrl(rs.getString("Avata_Url"));
                    u.setPhone(rs.getString("Phone"));
                    u.setAddress(rs.getString("Address"));
                    u.setEmail(rs.getString("Email"));
                    u.setPasswordHash(rs.getString("PasswordHash"));
                    u.setRole(rs.getString("Role"));
                    users.add(u);
                }
                return users;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public int getTotalUsersByName(String searchQuery) {
        String sql = "SELECT COUNT(*) FROM Users WHERE LOWER(fullName) LIKE ?";
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return 0;
        }
        searchQuery = searchQuery.trim().replaceAll("\\s+", " ").replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + searchQuery + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; 
    }
}
