package com.example.patterns.outbox.mapper;

import com.example.patterns.outbox.models.dto.CheckoutDto;
import com.example.patterns.outbox.models.entity.Checkout;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CheckoutMapper {

    CheckoutDto toDto(Checkout checkout);
}
