package com.project.threeam.services;


import com.project.threeam.dtos.FeedbackDTO;
import com.project.threeam.dtos.OrderDetailDTO;
import com.project.threeam.entities.FeedbackEntity;
import com.project.threeam.entities.OrderDetailEntity;
import com.project.threeam.entities.OrderEntity;
import com.project.threeam.entities.ProductEntity;
import com.project.threeam.repositories.FeedbackRepository;
import com.project.threeam.repositories.OrderDetailRepository;
import com.project.threeam.repositories.OrderRepository;
import com.project.threeam.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<OrderDetailDTO> getAllOrderDetails() {
        List<OrderDetailEntity> orderDetails = orderDetailRepository.findAll();
        List<OrderDetailDTO> orderDetailDTOs = new ArrayList<>();
        for (OrderDetailEntity orderDetail : orderDetails) {
            OrderDetailDTO orderDetailDTO = convertToDTO(orderDetail);
            try {
                orderDetailDTO.setOrder_id(orderDetail.getOrderEntity().getOrderId());
                orderDetailDTO.setProduct_id(orderDetail.getProductDetailEntity().getProductId());
                orderDetailDTOs.add(orderDetailDTO);
            } catch (Exception e) {
                orderDetailDTOs.add(orderDetailDTO);
            }
        }
        return orderDetailDTOs;
    }

    public List<OrderDetailDTO> getOrderDetailByOrderID(Long orderID) {
        Optional<OrderEntity> orderExits = orderRepository.findByOrderId(orderID);
        OrderEntity order = orderExits.get();

        Optional<List<OrderDetailEntity>> orderDetailOptional = orderDetailRepository.findByOrderEntity(order);
        List<OrderDetailEntity> orderDetails = orderDetailOptional.get();
        List<OrderDetailDTO> orderDetailDTOs = new ArrayList<>();
        for (OrderDetailEntity orderDetail : orderDetails) {
            OrderDetailDTO orderDetailDTO = convertToDTO(orderDetail);
            try {
                orderDetailDTO.setOrder_id(orderDetail.getOrderEntity().getOrderId());
                orderDetailDTO.setProduct_id(orderDetail.getProductDetailEntity().getProductId());
                orderDetailDTOs.add(orderDetailDTO);
            } catch (Exception e) {
                orderDetailDTOs.add(orderDetailDTO);
            }
        }
        return orderDetailDTOs;
    }

    public OrderDetailDTO createOrderDetail(OrderDetailDTO orderDetailDTO) {
        try {
            Optional<OrderEntity> orderExits = orderRepository.findByOrderId(orderDetailDTO.getOrder_id());
            Optional<ProductEntity> productExits = productRepository.findByProductId(orderDetailDTO.getProduct_id());

            if(orderExits.isPresent() && productExits.isPresent()) {
                OrderEntity order = orderExits.get();
                ProductEntity product = productExits.get();
                OrderDetailEntity orderDetailEntity = modelMapper.map(orderDetailDTO, OrderDetailEntity.class);
                orderDetailEntity.setOrderEntity(order);
                orderDetailEntity.setProductDetailEntity(product);
                OrderDetailEntity savedEntity = orderDetailRepository.save(orderDetailEntity);
                return convertToDTO(savedEntity);
            } else {
                return null;
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return null;

    }


    private OrderDetailDTO convertToDTO(OrderDetailEntity orderDetailEntity) {
        return modelMapper.map(orderDetailEntity, OrderDetailDTO.class);
    }
}
