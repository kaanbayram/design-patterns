package com.example.patterns.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order")
@Getter
@Setter
public class Order extends BaseEntity {
    private String userId;
}