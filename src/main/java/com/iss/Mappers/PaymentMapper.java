package com.iss.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;
import com.iss.Dto.PaymentDto;
import com.iss.models.Payment;

@Mapper
public interface PaymentMapper {
	PaymentMapper Instance=Mappers.getMapper(PaymentMapper.class);
	PaymentDto toDto(Payment payment);
	Payment toEntity(PaymentDto payment);
	List<PaymentDto> toDtoList(List<Payment> courses);
	List<Payment> toEntityList(List<PaymentDto> coursedtos);
	
}
