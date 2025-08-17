package com.fingerPrint.fingerPrint.service;

import com.fingerPrint.fingerPrint.dto.request.RegisterFingerPrintRequest;
import com.fingerPrint.fingerPrint.dto.response.RegisterFingerPrintResponse;

public interface FingerPrintService {
    RegisterFingerPrintResponse register(RegisterFingerPrintRequest fingerPrintRequest);
}
