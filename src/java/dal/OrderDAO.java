/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class OrderDAO extends DBContext {

    public long insertOrder(long userId, double totalAmount, String status) {
        String sql = "INSERT INTO [Order] (User_Id, Total_Amount, Status, Created_At) VALUES (?, ?, ?, GETDATE())";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, userId);
            ps.setDouble(2, totalAmount);
            ps.setString(3, status);
            int affected = ps.executeUpdate();
            if (affected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean updateOrderStatus(long orderId, String status) {
        String sql = "UPDATE [Order] SET Status = ? WHERE Order_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setLong(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
