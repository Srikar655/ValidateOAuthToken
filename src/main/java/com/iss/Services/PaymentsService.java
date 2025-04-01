package com.iss.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.iss.Dto.PaymentDto;
import com.iss.Mappers.PaymentMapper;
import com.iss.Repos.PaymentRepository;
import com.iss.models.Payment;

@Service
public class PaymentsService {

    private final PaymentRepository repos;

    public PaymentsService(PaymentRepository repos) {
        this.repos = repos;
    }

    public PaymentDto add(Payment payment) {
        try {
            Payment savedPayment = repos.save(payment);
            return PaymentMapper.Instance.toDto(savedPayment);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error adding the payment", ex);
        }
    }

    public List<PaymentDto> findAll() {
        try {
            List<Payment> paymentList = repos.findAll();
            return PaymentMapper.Instance.toDtoList(paymentList);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching all payments", ex);
        }
    }

    public PaymentDto find(int id) {
        try {
            Optional<Payment> paymentOpt = repos.findById(id);
            if (paymentOpt.isPresent()) {
                return PaymentMapper.Instance.toDto(paymentOpt.get());
            } else {
                throw new RuntimeException("Payment with id " + id + " not found");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching the payment with id " + id, ex);
        }
    }

    public void delete(int id) {
        try {
            if (repos.existsById(id)) {
                repos.deleteById(id);
            } else {
                throw new RuntimeException("Payment with id " + id + " not found, delete failed");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error deleting the payment with id " + id, ex);
        }
    }
}
