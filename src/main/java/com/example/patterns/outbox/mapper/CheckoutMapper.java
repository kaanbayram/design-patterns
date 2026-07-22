package com.example.patterns.outbox.mapper;

import com.example.patterns.outbox.models.dto.CheckoutDto;
import com.example.patterns.outbox.models.entity.Checkout;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CheckoutMapper {

    @Mapping(source = "createdAt", target = "completedAt")
    CheckoutDto toDto(Checkout checkout);
}
