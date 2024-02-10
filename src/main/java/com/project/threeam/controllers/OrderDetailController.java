package com.project.threeam.controllers;


import com.project.threeam.dtos.OrderDetailDTO;
import com.project.threeam.response.CustomStatusResponse;
import com.project.threeam.services.OrderDetailService;
import com.project.threeam.utils.GetDataErrorUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/OrderDeTail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CustomStatusResponse customStatusResponse;

    @Autowired
    private GetDataErrorUtils getDataErrorUtils;

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


    @GetMapping("/GetDetailListByOrder/{orderID}")
    public ResponseEntity<List<OrderDetailDTO>> getAllDetailByOrderID(@PathVariable Long orderID) {
        try {
            List<OrderDetailDTO> OrderDetails = orderDetailService.getOrderDetailByOrderID(orderID);
            if (OrderDetails.isEmpty()) {
                return customStatusResponse.NOTFOUND404("No Order Detail found");
            }
            return customStatusResponse.OK200("Get List of Order Detail Successfully", OrderDetails);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping("/AddOrderDetail")
    public ResponseEntity<OrderDetailDTO> createOrderDetail(@RequestBody @Valid OrderDetailDTO OrderDetailDTO, BindingResult rs) {
        try {
            if(rs.hasErrors()){
                var errors = getDataErrorUtils.DataError(rs);
                return customStatusResponse.BADREQUEST400("Provider data is incorrect",errors);
            }

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
