package com.example.patterns.outbox.service;

import com.example.patterns.errors.CheckoutException;
import com.example.patterns.errors.ErrorType;
import com.example.patterns.outbox.mapper.CheckoutMapper;
import com.example.patterns.outbox.models.dto.CheckoutDto;
import com.example.patterns.outbox.repository.CheckoutRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CheckoutService {

    private final CheckoutMapper checkoutMapper;

    private final CheckoutRepository checkoutRepository;

    CheckoutDto getCheckoutById(String id) {
        try {
            var checkout = checkoutRepository.findById(id).orElseThrow(() -> new CheckoutException(ErrorType.OUTBOX_GET_CHECKOUT_NOT_FOUND_EXCEPTION));
            log.info("Checkout found with checkoutId: {}", id);
            return checkoutMapper.toDto(checkout);
        } catch (Exception e) {
            log.error("Error occurred while getting checkout", e);
            throw new CheckoutException(ErrorType.OUTBOX_GET_CHECKOUT_EXCEPTION, e, id);
        }
    }

}
