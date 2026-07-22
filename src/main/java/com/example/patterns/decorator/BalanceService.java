package com.example.patterns.decorator;

import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class BalanceService {
    Boolean balanceCheck(BigInteger price) {
        //getting clients balance and compare etc...
        return true;
    }
}
