package com.anonymiser.dto;

import com.anonymiser.model.PiiData;

public class AnonymiseRequest {
    private PiiData data;
    private PiiTokens tokens;
    private PiiNature nature;

    // Getters and Setters

    public PiiData getData() {
        return data;
    }

    public void setData(PiiData data) {
        this.data = data;
    }

    public PiiTokens getTokens() {
        return tokens;
    }

    public void setTokens(PiiTokens tokens) {
        this.tokens = tokens;
    }

    public PiiNature getNature() {
        return nature;
    }

    public void setNature(PiiNature nature) {
        this.nature = nature;
    }
}