package com.project.threeam.services;

import com.project.threeam.dtos.OrderDTO;
import com.project.threeam.dtos.OrderDTO;
import com.project.threeam.dtos.ProductDTO;
import com.project.threeam.entities.*;
import com.project.threeam.entities.OrderEntity;
import com.project.threeam.entities.enums.OrderStatusEnum;
import com.project.threeam.repositories.OrderDetailRepository;
import com.project.threeam.repositories.OrderRepository;
import com.project.threeam.repositories.UserRepository;
import org.hibernate.query.Order;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public List<OrderDTO> getAllOrders() {
        List<OrderEntity> orders = orderRepository.findAll();
        List<OrderDTO> OrderDTOs = new ArrayList<>();
        for (OrderEntity order : orders) {
            OrderDTO OrderDTO = convertToDTO(order);
            try {
                OrderDTO.setUserId(order.getUserOrderEntity().getUserId());
                OrderDTOs.add(OrderDTO);
            } catch (Exception e) {
                OrderDTOs.add(OrderDTO);
            }
        }
        return OrderDTOs;
    }

    public List<OrderDTO> getOrderByUserID(Long userID) {
        Optional<UserEntity> userExits = userRepository.findByUserId(userID);

            UserEntity user = userExits.get();
        Optional<List<OrderEntity>> orderOptional = orderRepository.findByUserOrderEntity(user);
        List<OrderEntity> orders = orderOptional.get();
        List<OrderDTO> OrderDTOs = new ArrayList<>();
        for (OrderEntity order : orders) {
            OrderDTO OrderDTO = convertToDTO(order);
            try {
                OrderDTO.setUserId(order.getUserOrderEntity().getUserId());
                OrderDTOs.add(OrderDTO);
            } catch (Exception e) {
                OrderDTOs.add(OrderDTO);
            }
        }
        return OrderDTOs;
    }


    public Boolean checkUserOrderProduct(Long userID, String productId) {
        Optional<UserEntity> userExits = userRepository.findByUserId(userID);
        UserEntity user = userExits.get();
        Optional<List<OrderEntity>> orderOptional = orderRepository.findByUserOrderEntity(user);
        List<OrderEntity> orders = orderOptional.get();
        for (OrderEntity order : orders) {
            Optional<List<OrderDetailEntity>> orderDetailOptional = orderDetailRepository.findByOrderEntity(order);
            List<OrderDetailEntity> orderDetails = orderDetailOptional.get();
            for (OrderDetailEntity orderDetail : orderDetails) {
                if(orderDetail.getProductDetailEntity().getProductId().equals(productId)) {
                    return true;
                }
            }
        }
        return false;
    }




    public OrderDTO getOrderById(Long orderId) {
        Optional<OrderEntity> orderOptional = orderRepository.findByOrderId(orderId);
        if(orderOptional.isPresent()) {
            OrderEntity order = orderOptional.get();
            OrderDTO orderDTO = convertToDTO(order);
            try {
                orderDTO.setUserId(order.getUserOrderEntity().getUserId());
            } catch (Exception e) {
            }
            return orderDTO;
        } else { return null;}

    }

    public Boolean updateStatus(Long orderID, OrderStatusEnum status) {
        Optional<OrderEntity> orderOptional = orderRepository.findByOrderId(orderID);
        OrderEntity orderEntity = orderOptional.get();
        if(orderOptional.isPresent()) {
            orderEntity.setStatus(status);
            OrderEntity savedEntity = orderRepository.save(orderEntity);
            return  true;
        }
        return false;
    }


    public OrderDTO createOrder(OrderDTO orderDTO) {
        try {
            Optional<UserEntity> userExits = userRepository.findByUserId(orderDTO.getUserId());
            if(userExits.isPresent()) {
                UserEntity user = userExits.get();
                OrderEntity orderEntity = modelMapper.map(orderDTO, OrderEntity.class);
                if(orderDTO.getStatus() == null) {
                    orderEntity.setStatus(OrderStatusEnum.Preparing);
                }
                orderEntity.setUserOrderEntity(user);
                OrderEntity savedEntity = orderRepository.save(orderEntity);
                return convertToDTO(savedEntity);
            } else {
                return null;
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return null;

    }

    public OrderDTO updateOrder(Long orderId, OrderDTO orderDTO) {
        Optional<OrderEntity> existingOrderOptional = orderRepository.findByOrderId(orderId);
        if (existingOrderOptional.isPresent()) {
            // Update existing entity directly
            OrderEntity existingOrder = existingOrderOptional.get();

            if (orderDTO.getOrderId() != null) {
                existingOrder.setOrderId(orderDTO.getOrderId());
            }
            if (orderDTO.getAddress() != null) {
                existingOrder.setAddress(orderDTO.getAddress());
            }
            if (orderDTO.getStatus() != null) {
                existingOrder.setStatus(orderDTO.getStatus());
            }
            if (orderDTO.getUsername() != null) {
                existingOrder.setUsername(orderDTO.getUsername());
            }
            if (orderDTO.getStatus() != null) {
                existingOrder.setStatus(orderDTO.getStatus());
            }
            if (orderDTO.getQuantity() != null) {
                existingOrder.setQuantity(orderDTO.getQuantity());
            }
            if (orderDTO.getImage() != null) {
                existingOrder.setImage(orderDTO.getImage());
            }
            if (orderDTO.getTotalPrice() != null) {
                existingOrder.setTotalPrice(orderDTO.getTotalPrice());
            }

            if (orderDTO.getUserId() != null) {
                Optional<UserEntity> userExits = userRepository.findByUserId(orderDTO.getUserId());
                if(userExits.isPresent()) {
                    UserEntity userEntity = userExits.get();
                    existingOrder.setUserOrderEntity(userEntity);
                } else {
                    return null;
                }

            }

            OrderEntity savedEntity = orderRepository.save(existingOrder);  // Save the updated entity

            return convertToDTO(savedEntity);
        } else {
            return null;
        }
    }



    private OrderDTO convertToDTO(OrderEntity orderEntity) {
        return modelMapper.map(orderEntity, OrderDTO.class);
    }
}
