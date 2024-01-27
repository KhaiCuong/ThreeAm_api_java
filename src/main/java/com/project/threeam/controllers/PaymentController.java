package com.project.threeam.controllers;

import com.project.threeam.dtos.PaymentDTO;
import com.project.threeam.response.CustomStatusResponse;
import com.project.threeam.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CustomStatusResponse customStatusResponse;

    @GetMapping("/GetPaymentList")
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        try {
            List<PaymentDTO> Payments = paymentService.getAllPayments();
            if (Payments.isEmpty()) {
                return customStatusResponse.NOTFOUND404("No Payment found");

            }
            return customStatusResponse.OK200("Get List of Payment Successfully", Payments);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }

    @GetMapping("/GetPaymentByOrderId/{orderID}")
    public ResponseEntity<PaymentDTO> getPaymentByOrderID(@PathVariable Long orderID) {
        try {
            PaymentDTO Payments = paymentService.getPaymentsByOrderID(orderID);
            if (Payments == null) {
                return customStatusResponse.NOTFOUND404("No Payment found");
            }
            return customStatusResponse.OK200("Get List of Payment Successfully", Payments);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }


    @PostMapping("/AddPayment")
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO PaymentDTO) {
        try {
            PaymentDTO createdPayment = paymentService.createPayment(PaymentDTO);
            if(createdPayment == null ){
                return customStatusResponse.BADREQUEST400("Have error when create Payment !");
            }
            return customStatusResponse.CREATED201("Payment created", createdPayment);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }

    }
}
