package com.example.patterns.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product extends BaseEntity {
    private String name;

    private BigInteger price;

    private Integer quantity;
}
