package com.iss.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.iss.Dto.PaymentDto;
import com.iss.Mappers.PaymentMapper;
import com.iss.Repos.PaymentRepository;
import com.iss.models.Payment;

@Service
public class PaymentsService {
	private PaymentRepository repos;
	public PaymentsService(PaymentRepository repos)
	{
		this.repos=repos;
	}
	public PaymentDto add(Payment payment)
	{
		try
		{
			return PaymentMapper.Instance.toDto(repos.save(payment));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public List<PaymentDto> findAll()
	{
		try
		{
			return PaymentMapper.Instance.toDtoList(repos.findAll());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public PaymentDto find(int id)
	{
		try
		{
			return PaymentMapper.Instance.toDto(repos.findById(id).get());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public void delete(int id)
	{
		try
		{
			repos.deleteById(id);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
