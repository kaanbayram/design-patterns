package com.example.patterns.outbox.models.event;

import com.example.patterns.outbox.models.dto.CheckoutDto;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Getter
@Setter
@Builder
public class CheckoutEvent extends CheckoutDto {
}
