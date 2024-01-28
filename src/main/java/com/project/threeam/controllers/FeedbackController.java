package com.project.threeam.controllers;


import com.project.threeam.dtos.FeedbackDTO;
import com.project.threeam.response.CustomStatusResponse;
import com.project.threeam.services.FeedbackService;
import com.project.threeam.utils.GetDataErrorUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private CustomStatusResponse customStatusResponse;

    @Autowired
    private GetDataErrorUtils getDataErrorUtils;

    @GetMapping("/GetFeedbackList")
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbacks() {
        try {
            List<FeedbackDTO> feedbacks = feedbackService.getAllFeedbacks();
            if (feedbacks.isEmpty()) {
                return customStatusResponse.NOTFOUND404("No Feedback found");

            }
            return customStatusResponse.OK200("Get List of Feedback Successfully", feedbacks);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }

    @GetMapping("/GetFeedbackListByProductId/{productID}")
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbackByProductID(@PathVariable String productID) {
        try {
            List<FeedbackDTO> feedbacks = feedbackService.getFeedbacksByProductID(productID);
            if (feedbacks.isEmpty()) {
                return customStatusResponse.NOTFOUND404("No Feedback found");
            }
            return customStatusResponse.OK200("Get List of Feedback Successfully", feedbacks);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }


    @PostMapping("/AddFeedback")
    public ResponseEntity<FeedbackDTO> createFeedback(@RequestBody @Valid FeedbackDTO feedbackDTO , BindingResult rs) {
        try {
            if(rs.hasErrors()){
                var errors = getDataErrorUtils.DataError(rs);
                return customStatusResponse.BADREQUEST400("Provider data is incorrect",errors);
            }

            FeedbackDTO createdFeedback = feedbackService.createFeedback(feedbackDTO);
            if(createdFeedback == null ){
                return customStatusResponse.BADREQUEST400("Have error when create Feedback !");
            }
            return customStatusResponse.CREATED201("Feedback created", createdFeedback);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }

    }
}
