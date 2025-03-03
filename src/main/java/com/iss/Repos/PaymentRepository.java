package com.iss.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iss.models.Payment;
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
