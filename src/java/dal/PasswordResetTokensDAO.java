package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.PasswordResetToken;

public class PasswordResetTokensDAO extends DBContext {

     // Thêm OTP mới
    public boolean insertToken(PasswordResetToken token) {
        String sql = "INSERT INTO PasswordResetTokens (UserID, OTPCode, ExpiryTime, IsUsed) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, token.getUserId());
            ps.setString(2, token.getOtpCode());
            ps.setObject(3, token.getExpiryTime()); // LocalDateTime sẽ được convert tự động
            ps.setBoolean(4, token.isIsUsed());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy OTP chưa sử dụng theo userId
    public PasswordResetToken getValidTokenByUserId(int userId) {
        String sql = "SELECT * FROM PasswordResetTokens WHERE UserID = ? AND IsUsed = 0 AND ExpiryTime >= GETDATE()";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PasswordResetToken token = new PasswordResetToken();
                token.setTokenId(rs.getInt("TokenID"));
                token.setUserId(rs.getLong("UserID"));
                token.setOtpCode(rs.getString("OTPCode"));
                token.setExpiryTime(rs.getTimestamp("ExpiryTime").toLocalDateTime());
                token.setIsUsed(rs.getBoolean("IsUsed"));
                return token;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Đánh dấu OTP đã sử dụng
    public boolean markTokenAsUsed(int tokenId) {
        String sql = "UPDATE PasswordResetTokens SET IsUsed = 1 WHERE TokenID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, tokenId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa OTP hết hạn
    public boolean deleteExpiredTokens() {
        String sql = "DELETE FROM PasswordResetTokens WHERE ExpiryTime < GETDATE()";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}


