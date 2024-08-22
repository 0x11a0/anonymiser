package com.anonymiser.service;

import com.anonymiser.model.PiiData;
import com.anonymiser.util.HashingUtil;
import org.springframework.stereotype.Service;

@Service
public class DataMaskingServiceImpl implements DataMaskingService {

    @Override
    public PiiData maskData(PiiData piiData) {
        // Mask name using chunked hashing
        piiData.setName(HashingUtil.chunkedHashing(piiData.getName()));
        
        // Mask email using chunked hashing (assuming email chunking is similar to name)
        piiData.setEmail(HashingUtil.chunkedHashing(piiData.getEmail()));
        
        // Mask phone number using numeric affine transformation
        piiData.setPhone(HashingUtil.applyNumericAffineTransform(piiData.getPhone()));
        
        // Mask street address using alphanumeric hashing and affine transformation
        piiData.setAddressStreet(HashingUtil.applyAlphanumericHashingAndAffineTransform(piiData.getAddressStreet()));
        
        // Mask postal code using segmented hashing and affine transformation
        piiData.setAddressPostal(HashingUtil.segmentedHashing(piiData.getAddressPostal()));
        
        // Mask country using basic hashing
        piiData.setCountry(HashingUtil.basicHashing(piiData.getCountry()));
        
        // Mask ID number using segmented hashing and affine transformation
        piiData.setIdNumber(HashingUtil.segmentedHashing(piiData.getIdNumber()));
        
        return piiData;
    }
}