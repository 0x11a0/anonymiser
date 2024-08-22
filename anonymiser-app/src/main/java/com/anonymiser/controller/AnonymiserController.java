package com.anonymiser.controller;

import com.anonymiser.model.PiiRequest;
import com.anonymiser.service.DataMaskingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AnonymiserController {

    @Autowired
    private DataMaskingService dataMaskingService;

    @PostMapping("/anonymise")
    public PiiRequest anonymizeData(@RequestBody PiiRequest piiRequest) {
        // Mask the data within the PiiRequest object
        PiiRequest maskedRequest = new PiiRequest();
        maskedRequest.setData(dataMaskingService.maskData(piiRequest.getData()));
        maskedRequest.setTokens(piiRequest.getTokens());
        maskedRequest.setNature(piiRequest.getNature());
        return maskedRequest;
    }
}