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
                return new User(
                    rs.getLong("UserID"),
                    rs.getString("FirstName"),
                    rs.getString("MiddleName"),
                    rs.getString("LastName"),
                    rs.getString("Avata_Url"),
                    rs.getString("Phone"),
                    rs.getString("Address"),
                    rs.getString("Email"),
                    rs.getString("PasswordHash"),
                    rs.getString("Role"),
                    rs.getTimestamp("Created_At"),
                    rs.getTimestamp("Updated_At"),
                    rs.getString("Account_Status")
                );
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
                return new User(
                    rs.getLong("UserID"),
                    rs.getString("FirstName"),
                    rs.getString("MiddleName"),
                    rs.getString("LastName"),
                    rs.getString("Avata_Url"),
                    rs.getString("Phone"),
                    rs.getString("Address"),
                    rs.getString("Email"),
                    rs.getString("PasswordHash"),
                    rs.getString("Role"),
                    rs.getTimestamp("Created_At"),
                    rs.getTimestamp("Updated_At"),
                    rs.getString("Account_Status")
                );
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
                return new User(
                    rs.getLong("UserID"),
                    rs.getString("FirstName"),
                    rs.getString("MiddleName"),
                    rs.getString("LastName"),
                    rs.getString("Avata_Url"),
                    rs.getString("Phone"),
                    rs.getString("Address"),
                    rs.getString("Email"),
                    rs.getString("PasswordHash"),
                    rs.getString("Role"),
                    rs.getTimestamp("Created_At"),
                    rs.getTimestamp("Updated_At"),
                    rs.getString("Account_Status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertUser(User user) {
        String sql = "INSERT INTO Users (FirstName, MiddleName, LastName, Avata_Url, Phone, Address, Email, PasswordHash, Role, Created_At, Updated_At, Account_Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), GETDATE(), ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getMiddleName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getAvataUrl());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getAddress());
            ps.setString(7, user.getEmail());
            ps.setString(8, user.getPasswordHash());
            ps.setString(9, user.getRole());
            ps.setString(10, user.getAccountStatus());
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
                return new User(
                    rs.getLong("UserID"),
                    rs.getString("FirstName"),
                    rs.getString("MiddleName"),
                    rs.getString("LastName"),
                    rs.getString("Avata_Url"),
                    rs.getString("Phone"),
                    rs.getString("Address"),
                    rs.getString("Email"),
                    rs.getString("PasswordHash"),
                    rs.getString("Role"),
                    rs.getTimestamp("Created_At"),
                    rs.getTimestamp("Updated_At"),
                    rs.getString("Account_Status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getUsers(int page, int pageSize, String role) {
        List<User> users = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM Users");
        boolean hasWhere = false;
        if (!"all".equalsIgnoreCase(role)) {
            sqlBuilder.append(" WHERE Role = ?");
            hasWhere = true;
        }
        sqlBuilder.append(" ORDER BY UserID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        String sql = sqlBuilder.toString();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            int paramIndex = 1;
            if (hasWhere) {
                ps.setString(paramIndex++, role);
            }
            ps.setInt(paramIndex++, (page - 1) * pageSize);
            ps.setInt(paramIndex, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(new User(
                    rs.getLong("UserID"),
                    rs.getString("FirstName"),
                    rs.getString("MiddleName"),
                    rs.getString("LastName"),
                    rs.getString("Avata_Url"),
                    rs.getString("Phone"),
                    rs.getString("Address"),
                    rs.getString("Email"),
                    rs.getString("PasswordHash"),
                    rs.getString("Role"),
                    rs.getTimestamp("Created_At"),
                    rs.getTimestamp("Updated_At"),
                    rs.getString("Account_Status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public int getTotalUsers(String role) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(*) FROM Users");
        boolean hasWhere = false;
        if (!"all".equalsIgnoreCase(role)) {
            sqlBuilder.append(" WHERE Role = ?");
            hasWhere = true;
        }
        String sql = sqlBuilder.toString();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            if (hasWhere) {
                ps.setString(1, role);
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<User> getUsersByName(String searchQuery, int page, int pageSize, String role) {
        List<User> users = new ArrayList<>();
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return getUsers(page, pageSize, role);
        }
        searchQuery = searchQuery.trim().replaceAll("\\s+", " ");
        String[] keywords = searchQuery.split(" ");
        if (keywords.length == 0) {
            return getUsers(page, pageSize, role);
        }
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM Users WHERE ");
        for (int i = 0; i < keywords.length; i++) {
            if (i > 0) {
                sqlBuilder.append(" AND ");
            }
            sqlBuilder.append("(LOWER(FirstName) LIKE ? OR LOWER(MiddleName) LIKE ? OR LOWER(LastName) LIKE ?)");
        }
        if (!"all".equalsIgnoreCase(role)) {
            sqlBuilder.append(" AND Role = ?");
        }
        sqlBuilder.append(" ORDER BY UserID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        String sql = sqlBuilder.toString();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            int paramIndex = 1;
            for (String keyword : keywords) {
                String pattern = "%" + keyword.toLowerCase() + "%";
                ps.setString(paramIndex++, pattern);
                ps.setString(paramIndex++, pattern);
                ps.setString(paramIndex++, pattern);
            }
            if (!"all".equalsIgnoreCase(role)) {
                ps.setString(paramIndex++, role);
            }
            ps.setInt(paramIndex++, (page - 1) * pageSize);
            ps.setInt(paramIndex, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(new User(
                    rs.getLong("UserID"),
                    rs.getString("FirstName"),
                    rs.getString("MiddleName"),
                    rs.getString("LastName"),
                    rs.getString("Avata_Url"),
                    rs.getString("Phone"),
                    rs.getString("Address"),
                    rs.getString("Email"),
                    rs.getString("PasswordHash"),
                    rs.getString("Role"),
                    rs.getTimestamp("Created_At"),
                    rs.getTimestamp("Updated_At"),
                    rs.getString("Account_Status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public int getTotalUsersByName(String searchQuery, String role) {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return getTotalUsers(role);
        }
        searchQuery = searchQuery.trim().replaceAll("\\s+", " ");
        String[] keywords = searchQuery.split(" ");
        if (keywords.length == 0) {
            return getTotalUsers(role);
        }
        StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(*) FROM Users WHERE ");
        for (int i = 0; i < keywords.length; i++) {
            if (i > 0) {
                sqlBuilder.append(" AND ");
            }
            sqlBuilder.append("(LOWER(FirstName) LIKE ? OR LOWER(MiddleName) LIKE ? OR LOWER(LastName) LIKE ?)");
        }
        if (!"all".equalsIgnoreCase(role)) {
            sqlBuilder.append(" AND Role = ?");
        }
        String sql = sqlBuilder.toString();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            int paramIndex = 1;
            for (String keyword : keywords) {
                String pattern = "%" + keyword.toLowerCase() + "%";
                ps.setString(paramIndex++, pattern);
                ps.setString(paramIndex++, pattern);
                ps.setString(paramIndex++, pattern);
            }
            if (!"all".equalsIgnoreCase(role)) {
                ps.setString(paramIndex, role);
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public User getUserByReview(long reviewId) {
        String sql = "SELECT u.* FROM Users u JOIN Review r ON u.UserID = r.User_Id WHERE r.Review_Id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, reviewId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getLong("UserID"),
                    rs.getString("FirstName"),
                    rs.getString("MiddleName"),
                    rs.getString("LastName"),
                    rs.getString("Avata_Url"),
                    rs.getString("Phone"),
                    rs.getString("Address"),
                    rs.getString("Email"),
                    rs.getString("PasswordHash"),
                    rs.getString("Role"),
                    rs.getTimestamp("Created_At"),
                    rs.getTimestamp("Updated_At"),
                    rs.getString("Account_Status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean updateUser(User user) {
        String sql = "UPDATE Users SET FirstName = ?, MiddleName = ?, LastName = ?, Avata_Url = ?, Phone = ?, Address = ?, Email = ?, PasswordHash = ?, Role = ?, Updated_At = GETDATE(), Account_Status = ? WHERE UserID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getMiddleName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getAvataUrl());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getAddress());
            ps.setString(7, user.getEmail());
            ps.setString(8, user.getPasswordHash());
            ps.setString(9, user.getRole());
            ps.setString(10, user.getAccountStatus());
            ps.setLong(11, user.getUser_id());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(long userId) {
        try {
            // Delete Order_Detail
            String sqlOrderDetail = "DELETE FROM Order_Detail WHERE Order_Id IN (SELECT Order_Id FROM [Order] WHERE User_Id = ?)";
            PreparedStatement psOrderDetail = connection.prepareStatement(sqlOrderDetail);
            psOrderDetail.setLong(1, userId);
            psOrderDetail.executeUpdate();

            // Delete Payment
            String sqlPayment = "DELETE FROM Payment WHERE Order_Id IN (SELECT Order_Id FROM [Order] WHERE User_Id = ?)";
            PreparedStatement psPayment = connection.prepareStatement(sqlPayment);
            psPayment.setLong(1, userId);
            psPayment.executeUpdate();

            // Delete Order
            String sqlOrder = "DELETE FROM [Order] WHERE User_Id = ?";
            PreparedStatement psOrder = connection.prepareStatement(sqlOrder);
            psOrder.setLong(1, userId);
            psOrder.executeUpdate();

            // Delete Cart
            String sqlCart = "DELETE FROM Cart WHERE User_Id = ?";
            PreparedStatement psCart = connection.prepareStatement(sqlCart);
            psCart.setLong(1, userId);
            psCart.executeUpdate();

            // Delete Review
            String sqlReview = "DELETE FROM Review WHERE User_Id = ?";
            PreparedStatement psReview = connection.prepareStatement(sqlReview);
            psReview.setLong(1, userId);
            psReview.executeUpdate();

            // Delete Blog
            String sqlBlog = "DELETE FROM Blog WHERE Created_By = ?";
            PreparedStatement psBlog = connection.prepareStatement(sqlBlog);
            psBlog.setLong(1, userId);
            psBlog.executeUpdate();

            // Delete PasswordResetTokens
            String sqlTokens = "DELETE FROM PasswordResetTokens WHERE UserID = ?";
            PreparedStatement psTokens = connection.prepareStatement(sqlTokens);
            psTokens.setLong(1, userId);
            psTokens.executeUpdate();

            // Delete User
            String sqlUser = "DELETE FROM Users WHERE UserID = ?";
            PreparedStatement psUser = connection.prepareStatement(sqlUser);
            psUser.setLong(1, userId);
            return psUser.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}