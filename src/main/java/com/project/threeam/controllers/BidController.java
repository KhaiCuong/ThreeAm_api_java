package com.project.threeam.controllers;


import com.project.threeam.dtos.BidDTO;
import com.project.threeam.entities.AutionEntity;
import com.project.threeam.entities.BidEntity;
import com.project.threeam.response.CustomStatusResponse;
import com.project.threeam.services.AutionService;
import com.project.threeam.services.BidService;
import com.project.threeam.utils.GetDataErrorUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/bid")
public class BidController {
    @Autowired
    private BidService bidService;

    @Autowired
    private CustomStatusResponse customStatusResponse;

    @Autowired
    private GetDataErrorUtils getDataErrorUtils;
    @GetMapping("/getlist")
    public ResponseEntity<List<BidDTO>> getAllBid() {
        try {
            List<BidDTO> entities = bidService.getAlls();
            if (entities.isEmpty()) {
                return customStatusResponse.NOTFOUND404("No Aution found");
            }
            return customStatusResponse.OK200("Get List of Aution Successfully", entities);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }


    @PostMapping("/addnew")
    public ResponseEntity<BidDTO> createNew(@RequestBody @Valid BidDTO bidDTO, BindingResult rs) {
        try {
            if(rs.hasErrors()){
                var errors = getDataErrorUtils.DataError(rs);
                return customStatusResponse.BADREQUEST400("Provider data is incorrect",errors);
            }

            BidDTO newEntity = bidService.createNew(bidDTO);
            if(newEntity == null ){
                return customStatusResponse.BADREQUEST400("Aution ID Not Found");

            }
            return customStatusResponse.CREATED201("Aution created", newEntity);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }

    }
}
