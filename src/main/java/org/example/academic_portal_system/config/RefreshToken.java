package org.example.academic_portal_system.config;

import java.util.Date;

public class RefreshToken {
    private String token;
    private Date expirationDate;

    public RefreshToken(String token, Date expirationDate) {
        this.token = token;
        this.expirationDate = expirationDate;
    }

    // Getter and Setter methods
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
