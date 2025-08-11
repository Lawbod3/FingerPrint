package com.fingerPrint.fingerPrint.service;

import com.fingerPrint.fingerPrint.dto.request.SaveFingerPrintRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FingerprintServiceTest {

    @Test
    void testThatCanSaveFingerprint() {
        SaveFingerPrintRequest saveFingerPrintRequest = new SaveFingerPrintRequest();
        saveFingerPrintRequest.setHand("right");
        saveFingerPrintRequest.setFingerprintImageBase64("");
        saveFingerPrintRequest.setUserId("1");
    }
}
