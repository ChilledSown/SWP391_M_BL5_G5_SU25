package dal;

import model.BalanceDTOSeller;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BalanceDAO extends DBContext {
    // Calculate total balance for a seller
    public double getBalance(int createdBy) {
        String sql = "SELECT SUM(p.Amount) as TotalBalance " +
                     "FROM [Order] o " +
                     "JOIN Payment p ON o.Order_Id = p.Order_Id " +
                     "JOIN Order_Detail od ON o.Order_Id = od.Order_Id " +
                     "JOIN Course c ON od.Course_Id = c.Course_Id " +
                     "WHERE c.Created_By = ? AND o.Status = 'completed' AND p.Payment_Status = 'completed'";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, createdBy);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getDouble("TotalBalance");
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(BalanceDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return 0.0;
    }

    // Fetch transaction history for a seller
    public List<BalanceDTOSeller> getTransactions(int createdBy) {
        List<BalanceDTOSeller> transactions = new ArrayList<>();
        String sql = "SELECT o.Order_Id, o.Created_At AS OrderDate, c.Course_Id, c.Title AS CourseName, p.Amount, p.Payment_Status, p.Payment_Method " +
                     "FROM [Order] o " +
                     "JOIN Payment p ON o.Order_Id = p.Order_Id " +
                     "JOIN Order_Detail od ON o.Order_Id = od.Order_Id " +
                     "JOIN Course c ON od.Course_Id = c.Course_Id " +
                     "WHERE c.Created_By = ? AND o.Status = 'completed' AND p.Payment_Status = 'completed' " +
                     "ORDER BY o.Created_At DESC";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, createdBy);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                transactions.add(new BalanceDTOSeller(
                    rs.getInt("Order_Id"),
                    rs.getTimestamp("OrderDate"),
                    rs.getInt("Course_Id"),
                    rs.getString("CourseName"),
                    rs.getDouble("Amount"),
                    rs.getString("Payment_Status"),
                    rs.getString("Payment_Method")
                ));
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(BalanceDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return transactions;
    }

    // Fetch a single transaction by orderId and seller's createdBy
    public BalanceDTOSeller getTransactionByOrderId(long orderId, int createdBy) {
        String sql = "SELECT o.Order_Id, o.Created_At AS OrderDate, c.Course_Id, c.Title AS CourseName, p.Amount, p.Payment_Status, p.Payment_Method " +
                     "FROM [Order] o " +
                     "JOIN Payment p ON o.Order_Id = p.Order_Id " +
                     "JOIN Order_Detail od ON o.Order_Id = od.Order_Id " +
                     "JOIN Course c ON od.Course_Id = c.Course_Id " +
                     "WHERE o.Order_Id = ? AND c.Created_By = ? AND o.Status = 'completed' AND p.Payment_Status = 'completed'";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setLong(1, orderId);
            stm.setInt(2, createdBy);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return new BalanceDTOSeller(
                    rs.getInt("Order_Id"),
                    rs.getTimestamp("OrderDate"),
                    rs.getInt("Course_Id"),
                    rs.getString("CourseName"),
                    rs.getDouble("Amount"),
                    rs.getString("Payment_Status"),
                    rs.getString("Payment_Method")
                );
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(BalanceDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return null;
    }

    // Fetch buyer UserID for an order
    public long getBuyerIdForOrder(long orderId) {
        String sql = "SELECT User_Id FROM [Order] WHERE Order_Id = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setLong(1, orderId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getLong("User_Id");
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(BalanceDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return -1;
    }
}