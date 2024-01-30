package com.project.threeam.controllers;


import com.project.threeam.dtos.OrderDTO;
import com.project.threeam.dtos.ProductDTO;
import com.project.threeam.entities.enums.OrderStatusEnum;
import com.project.threeam.response.CustomStatusResponse;
import com.project.threeam.services.CategoryService;
import com.project.threeam.services.OrderService;
import com.project.threeam.utils.GetDataErrorUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomStatusResponse customStatusResponse;

    @Autowired
    private GetDataErrorUtils getDataErrorUtils;

    @GetMapping("/GetOrderList")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        try {
            List<OrderDTO> orders = orderService.getAllOrders();
            if (orders.isEmpty()) {
                return customStatusResponse.NOTFOUND404("No Order found");

            }
            return customStatusResponse.OK200("Get List of Order Successfully", orders);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }

    @GetMapping("/GetOrderListByUserId/{userId}")
    public ResponseEntity<List<OrderDTO>> getAllProductsBycategory(@PathVariable Long userId) {
        try {
            List<OrderDTO> orders = orderService.getOrderByUserID(userId);
            if (orders.isEmpty()) {
                return customStatusResponse.NOTFOUND404("No Order found");
            }
            return customStatusResponse.OK200("Get List of Order Successfully", orders);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }


    @PostMapping("/AddOrder")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody @Valid OrderDTO orderDTO, BindingResult rs) {
        try {
            if(rs.hasErrors()){
                var errors = getDataErrorUtils.DataError(rs);
                return customStatusResponse.BADREQUEST400("Provider data is incorrect",errors);
            }
            Long userID = orderDTO.getUser_id();

            OrderDTO createdOrder = orderService.createOrder(orderDTO);
            if(createdOrder == null ){
                return customStatusResponse.BADREQUEST400("Have error when create order !");
            }
            createdOrder.setUser_id(userID);
            return customStatusResponse.CREATED201("Order created", createdOrder);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }

    }

    @PutMapping("/UpdateStatusOrder/{orderID}")
    public ResponseEntity<OrderDTO> updateProductStatus(@PathVariable Long orderID,@RequestBody OrderStatusEnum status) {
        try {
            Boolean updatedProductStatus = orderService.updateStatus(orderID,status);
            if (updatedProductStatus == false) {
                return customStatusResponse.NOTFOUND404("Order not found");
            }
            return customStatusResponse.OK200("Order Status updated");
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }
}
