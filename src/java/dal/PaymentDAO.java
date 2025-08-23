package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentDAO extends DBContext {

    public long insertPayment(long orderId, double amount, String paymentMethod, String paymentStatus) {
        String sql = "INSERT INTO Payment (Order_Id, Amount, Payment_Method, Payment_Status, Payment_Date) VALUES (?, ?, ?, ?, GETDATE())";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, orderId);
            ps.setDouble(2, amount);
            ps.setString(3, paymentMethod);
            ps.setString(4, paymentStatus);
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
}


