package com.project.threeam.services;

import com.project.threeam.dtos.PaymentDTO;
import com.project.threeam.dtos.PaymentDTO;
import com.project.threeam.entities.OrderEntity;
import com.project.threeam.entities.PaymentEntity;
import com.project.threeam.entities.PaymentEntity;
import com.project.threeam.entities.ProductEntity;
import com.project.threeam.repositories.OrderRepository;
import com.project.threeam.repositories.PaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;


    public List<PaymentDTO> getAllPayments() {
        List<PaymentEntity> payments = paymentRepository.findAll();
        List<PaymentDTO> PaymentDTOs = new ArrayList<>();
        for (PaymentEntity payment : payments) {
            PaymentDTO PaymentDTO = convertToDTO(payment);
            try {
                PaymentDTO.setOrderId(payment.getOrderPaymentEntity().getOrderId());
                PaymentDTOs.add(PaymentDTO);
            } catch (Exception e) {
                PaymentDTOs.add(PaymentDTO);
            }
        }
        return PaymentDTOs;
    }

    public PaymentDTO getPaymentsByOrderID(Long orderID) {
        Optional<OrderEntity> orderExits = orderRepository.findByOrderId(orderID);
        OrderEntity order = orderExits.get();
        Optional<PaymentEntity> paymentsOptional = paymentRepository.findByOrderPaymentEntity(order);
        PaymentEntity payment = paymentsOptional.get();
        PaymentDTO paymentDTO = convertToDTO(payment);
        paymentDTO.setOrderId(payment.getOrderPaymentEntity().getOrderId());
        return paymentDTO;
    }

    public PaymentDTO createPayment(PaymentDTO PaymentDTO) {
        try {
            Optional<OrderEntity> orderExits = orderRepository.findByOrderId(PaymentDTO.getOrderId());
            if(orderExits.isPresent()) {
                OrderEntity order = orderExits.get();
                PaymentEntity paymentEntity = modelMapper.map(PaymentDTO, PaymentEntity.class);
                paymentEntity.setOrderPaymentEntity(order);
                PaymentEntity savedEntity = paymentRepository.save(paymentEntity);
                return convertToDTO(savedEntity);
            } else {
                return null;
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return null;

    }
    private PaymentDTO convertToDTO(PaymentEntity paymentEntity) {
        return modelMapper.map(paymentEntity, PaymentDTO.class);
    }
}
