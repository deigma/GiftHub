package com.gifthub.payment.service;

import com.gifthub.payment.dto.PaymentDto;
import com.gifthub.payment.entity.Payment;
import com.gifthub.payment.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Long pay(PaymentDto dto) {
        return paymentRepository.save(dto.toEntity()).getId();
    }

    public PaymentDto get(Long num) {
        return PaymentDto.toDto(paymentRepository.findById(num).orElseThrow());
    }

    public void setPaid(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow();

        payment.setPaid();

        paymentRepository.save(payment);
    }

    public List<PaymentDto> getAll(Pageable pageable) {
        return paymentRepository.findAllByOrderByIdDesc(pageable).get()
                .map(PaymentDto::toDto)
                .collect(Collectors.toList());
    }

    public void setPayCode(Long id, String tid) {
        Payment payment = paymentRepository.findById(id).orElseThrow();

        payment.setTid(tid);

        paymentRepository.save(payment);
    }

    public Long countMyOrders(Long userId) {
        return paymentRepository.countPaymentByUserId(userId);
    }
}
