package com.fingerPrint.fingerPrint.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveFingerPrintRequest {
    private String userId;
    private String hand;
    private String FingerprintImageBase64;
}
