package com.anonymiser.service;

import com.anonymiser.model.PiiData;

public interface DataMaskingService {
    PiiData maskData(PiiData piiData);
}