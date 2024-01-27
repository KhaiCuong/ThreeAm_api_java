package com.project.threeam.controllers;


import com.project.threeam.dtos.OrderDetailDTO;
import com.project.threeam.response.CustomStatusResponse;
import com.project.threeam.services.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/OrderDeTail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CustomStatusResponse customStatusResponse;

    @GetMapping("/GetOrderDetailList")
    public ResponseEntity<List<OrderDetailDTO>> getAllOrderDetails() {
        try {
            List<OrderDetailDTO> OrderDetails = orderDetailService.getAllOrderDetails();
            if (OrderDetails.isEmpty()) {
                return customStatusResponse.NOTFOUND404("No Order Detail found");

            }
            return customStatusResponse.OK200("Get List of OrderDetail Successfully", OrderDetails);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }

    @GetMapping("/GetOrderDetailListByOrderId/{orderID}")
    public ResponseEntity<List<OrderDetailDTO>> getAllDetailByOrderID(@PathVariable Long orderID) {
        try {
            List<OrderDetailDTO> OrderDetails = orderDetailService.getOrderDetailByOrderID(orderID);
            if (OrderDetails.isEmpty()) {
                return customStatusResponse.NOTFOUND404("No Orde rDetail found");
            }
            return customStatusResponse.OK200("Get List of Order Detail Successfully", OrderDetails);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }


    @PostMapping("/AddOrderDetail")
    public ResponseEntity<OrderDetailDTO> createOrderDetail(@RequestBody OrderDetailDTO OrderDetailDTO) {
        try {
            OrderDetailDTO createdOrderDetail = orderDetailService.createOrderDetail(OrderDetailDTO);
            if(createdOrderDetail == null ){
                return customStatusResponse.BADREQUEST400("Have error when create Order Detail !");
            }
            return customStatusResponse.CREATED201("Order Detail created", createdOrderDetail);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }

    }
}
