package com.fingerPrint.fingerPrint.dto.request;


import java.util.List;

public class RegisterFingerPrintRequest {
    private String userId;
    private String finger;
    private List<String> FingerprintImageBase64;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFinger() {
        return finger;
    }

    public void setFinger(String finger) {
        this.finger = finger;
    }

    public List<String> getFingerprintImageBase64() {
        return FingerprintImageBase64;
    }

    public void setFingerprintImageBase64(List<String> fingerprintImageBase64) {
        FingerprintImageBase64 = fingerprintImageBase64;
    }
}
