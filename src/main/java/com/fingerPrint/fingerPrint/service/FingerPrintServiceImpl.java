package com.fingerPrint.fingerPrint.service;
import com.fingerPrint.fingerPrint.dto.request.RegisterFingerPrintRequest;
import com.fingerPrint.fingerPrint.dto.response.RegisterFingerPrintResponse;
import com.fingerPrint.fingerPrint.exceptions.FingerAlreadyRegisteredByUserException;
import com.fingerPrint.fingerPrint.exceptions.FingerAlreadyRegisteredException;
import com.fingerPrint.fingerPrint.exceptions.FingerTypeDoesNotExistException;
import com.fingerPrint.fingerPrint.model.Finger;
import com.fingerPrint.fingerPrint.model.FingerPrint;
import com.fingerPrint.fingerPrint.repositories.FingerPrintRepository;
import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
public class FingerPrintServiceImpl implements FingerPrintService {
   private final FingerPrintRepository fingerPrintRepository;




   public FingerPrintServiceImpl(@Autowired FingerPrintRepository fingerPrintRepository) {
       this.fingerPrintRepository = fingerPrintRepository;
   }

    @Override
    public RegisterFingerPrintResponse register(RegisterFingerPrintRequest request) {
        Finger fingerEnum;
        try {
            fingerEnum = Finger.valueOf(request.getFinger().toUpperCase());
        } catch (FingerTypeDoesNotExistException e) {
            throw new FingerTypeDoesNotExistException("Invalid finger type: " + request.getFinger());
        }

        fingerPrintRepository.findByUserIdAndFinger(request.getUserId(), fingerEnum)
                .ifPresent( _ -> {
                    throw new FingerAlreadyRegisteredByUserException("This finger is already registered for this user.");
                });

        List<byte[]> imageBytesList = request.getFingerprintImageBase64().stream()
                .filter(Objects::nonNull)
                .map(imgBase64 -> Base64.getDecoder().decode(imgBase64))
                .toList();
        if (imageBytesList.isEmpty())throw new IllegalArgumentException("No valid fingerprint images provided.");

        List<FingerprintTemplate> extractedTemplates = imageBytesList.stream()
                .map(bytes -> new FingerprintTemplate(
                        new FingerprintImage()
                                .dpi(500)
                                .decode(bytes)
                ))
                .toList();


        List<FingerPrint> allFingerprints = fingerPrintRepository.findAll();
        for(FingerprintTemplate probeTemplate : extractedTemplates) {
            FingerprintMatcher matcher = new FingerprintMatcher(probeTemplate);

            for(FingerPrint candidate : allFingerprints) {
                for(byte[] stored : candidate.getTemplate()){
                    FingerprintTemplate dbTemplate = new FingerprintTemplate(stored);
                    double score = matcher.match(dbTemplate);

                    if(score > 40){
                        throw new FingerAlreadyRegisteredException( "This fingerprint already exists in the system, linked to: " +
                                candidate.getUserId() + "-" + candidate.getFinger());
                    }

                }

            }

        }

        FingerPrint entity = new FingerPrint();
        entity.setUserId(request.getUserId());
        entity.setFinger(fingerEnum);

        entity.setTemplate(
                extractedTemplates.stream()
                        .map(FingerprintTemplate::toByteArray) // convert SourceAFIS template to byte[]
                        .toList()
        );

        fingerPrintRepository.save(entity);
        return  new RegisterFingerPrintResponse("Fingerprint Registered successfully.");


    }
}
