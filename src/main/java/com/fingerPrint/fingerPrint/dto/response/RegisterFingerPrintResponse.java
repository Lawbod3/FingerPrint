package com.fingerPrint.fingerPrint.dto.response;

import lombok.Getter;
import lombok.Setter;


public class RegisterFingerPrintResponse {
    private boolean success = false;
    private String message;

    public RegisterFingerPrintResponse(String message) {
        this.success = true;
        this.message = message;
    }


}
