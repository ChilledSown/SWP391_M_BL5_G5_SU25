package model;

import java.time.LocalDateTime;

public class PasswordResetToken {

    private int tokenId;
    private Long userId;
    private String otpCode;
    private LocalDateTime expiryTime;
    private boolean isUsed;

    public PasswordResetToken() {
    }

    public PasswordResetToken(int tokenId, Long userId, String otpCode, LocalDateTime expiryTime, boolean isUsed) {
        this.tokenId = tokenId;
        this.userId = userId;
        this.otpCode = otpCode;
        this.expiryTime = expiryTime;
        this.isUsed = isUsed;
    }

    public int getTokenId() {
        return tokenId;
    }

    public void setTokenId(int tokenId) {
        this.tokenId = tokenId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public boolean isIsUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

   
}
