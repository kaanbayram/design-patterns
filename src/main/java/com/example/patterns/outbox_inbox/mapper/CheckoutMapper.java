package com.example.patterns.outbox_inbox.mapper;

import com.example.patterns.outbox_inbox.models.dto.CheckoutDto;
import com.example.patterns.outbox_inbox.models.entity.Checkout;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CheckoutMapper {

    @Mapping(source = "createdAt", target = "completedAt")
    CheckoutDto toDto(Checkout checkout);
}
