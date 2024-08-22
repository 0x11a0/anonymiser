package com.anonymiser.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnonymiserConfig {

    @Value("${anonymiser.affine.a}")
    private int affineA;

    @Value("${anonymiser.affine.b}")
    private int affineB;

    @Value("${anonymiser.affine.n}")
    private int affineN;

    public int getAffineA() {
        return affineA;
    }

    public int getAffineB() {
        return affineB;
    }

    public int getAffineN() {
        return affineN;
    }
}