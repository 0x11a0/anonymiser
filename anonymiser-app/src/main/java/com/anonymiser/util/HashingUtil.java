package com.anonymiser.util;

import com.anonymiser.config.AnonymiserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class HashingUtil {

    private static AnonymiserConfig anonymiserConfig;

    @Autowired
    public HashingUtil(AnonymiserConfig anonymiserConfig) {
        HashingUtil.anonymiserConfig = anonymiserConfig;
    }

    // Basic hashing method for general use
    public static String basicHashing(String data) {
        return hash(data);
    }

    // Chunked hashing splits the data and hashes each chunk separately, including splitting by spaces, @, and .
    public static String chunkedHashing(String data) {
        // Split by spaces, @, and . characters
        String[] chunks = data.split("[\\s@.]+");
        StringBuilder hashedData = new StringBuilder();

        for (String chunk : chunks) {
            hashedData.append(hash(chunk)).append(" ");
        }

        return hashedData.toString().trim();
    }

    // Applies numeric affine transformation for phone numbers or similar fields
    public static String applyNumericAffineTransform(String data) { 
    return affineTransform(data, anonymiserConfig.getAffineA(), anonymiserConfig.getAffineB(), anonymiserConfig.getAffineN());
}

    // Applies a combination of alphanumeric hashing and affine transformation
    public static String applyAlphanumericHashingAndAffineTransform(String data) {
        StringBuilder processedData = new StringBuilder();

        // Splits the string into numeric and non-numeric parts
        String[] tokens = data.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
        for (String token : tokens) {
            if (token.matches("\\d+")) { 
                // Applies affine transformation to numeric parts
                processedData.append(affineTransform(token, anonymiserConfig.getAffineA(), anonymiserConfig.getAffineB(), anonymiserConfig.getAffineN()));
            } else {
                // Applies hashing to non-numeric parts
                processedData.append(hash(token));
            }
            processedData.append(" ");
        }

        return processedData.toString();
    }

    // Proportional masking for numeric values
    public static String proportionalMasking(String data) {
        return data.replaceAll("\\d", "X");
    }

    // Segmented hashing and affine transformation for ID numbers, postal codes, etc.
    public static String segmentedHashing(String data) {
        String alphaPart = data.replaceAll("[^A-Za-z]", ""); // Extracts alphabetic part
        String numericPart = data.replaceAll("[^\\d]", "");  // Extracts numeric part
        // Combines hashed alphabetic part with transformed numeric part
        return hash(alphaPart) + " " + affineTransform(numericPart, anonymiserConfig.getAffineA(), anonymiserConfig.getAffineB(), anonymiserConfig.getAffineN());
    }

    // Hashing function using SHA-256
    private static String hash(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(data.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing data", e);
        }
    }

    // Affine transformation function for numeric data
    private static String affineTransform(String data, int a, int b, int n) {
        StringBuilder transformed = new StringBuilder();
        for (char c : data.toCharArray()) {
            if (Character.isDigit(c)) {
                int originalDigit = Character.getNumericValue(c);
                int newDigit = (a * originalDigit + b) % n;
                transformed.append(String.format("%03d", newDigit));
            } else {
                transformed.append(c);
            }
        }
        return transformed.toString();
    }
}