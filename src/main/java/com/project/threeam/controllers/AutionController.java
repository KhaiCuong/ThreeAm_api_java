package com.project.threeam.controllers;



import com.project.threeam.dtos.AutionDTO;
import com.project.threeam.dtos.OrderDTO;
import com.project.threeam.dtos.ProductDTO;
import com.project.threeam.entities.AutionEntity;
import com.project.threeam.response.CustomStatusResponse;
import com.project.threeam.services.AutionService;
import com.project.threeam.services.FeedbackService;
import com.project.threeam.utils.GetDataErrorUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/aution")
public class AutionController {

    @Autowired
    private AutionService autionService;

    @Autowired
    private CustomStatusResponse customStatusResponse;

    @Autowired
    private GetDataErrorUtils getDataErrorUtils;
//    @GetMapping("/getlist")
//    public ResponseEntity<List<AutionDTO>> getAllAution() {
//        try {
//            List<AutionDTO> entities = autionService.getAlls();
//            if (entities.isEmpty()) {
//                return customStatusResponse.NOTFOUND404("No Aution found");
//            }
//            return customStatusResponse.OK200("Get List of Aution Successfully", entities);
//        } catch (Exception e) {
//            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
//        }
//    }

    @GetMapping("/getlist")
    public ResponseEntity<List<AutionEntity>> getAllAution() {
        try {
            List<AutionEntity> entities = autionService.getAlls();
            if (entities.isEmpty()) {
                return customStatusResponse.NOTFOUND404("No Aution found");
            }
            return customStatusResponse.OK200("Get List of Aution Successfully", entities);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }

    @PostMapping("/addnew")
    public ResponseEntity<AutionDTO> createNew(@RequestBody @Valid AutionDTO autionDTO, BindingResult rs) {
        try {
            if(rs.hasErrors()){
                var errors = getDataErrorUtils.DataError(rs);
                return customStatusResponse.BADREQUEST400("Provider data is incorrect",errors);
            }

            AutionDTO newEntity = autionService.createNew(autionDTO);
            if(newEntity == null ){
                return customStatusResponse.BADREQUEST400("Aution ID Not Found");

            }
            return customStatusResponse.CREATED201("Aution created", newEntity);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }

    }

    @GetMapping("/GetAutionByProductId/{id}")
    public ResponseEntity<AutionDTO> GetAutionByProductId(@PathVariable String id) {
        try {
            AutionDTO autionDTO = autionService.getAutionByProducyID(id);
            if (autionDTO == null) {
                return customStatusResponse.NOTFOUND404("No Aution found");
            }
            return customStatusResponse.OK200("Get Aution Successfully", autionDTO);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }

    @GetMapping("/GetAution/{id}")
    public ResponseEntity<AutionEntity> getAutionById(@PathVariable Long id) {
        try {
            AutionEntity dataDTO = autionService.getAutionById(id);
            if (dataDTO == null) {
                return customStatusResponse.NOTFOUND404("Aution not found");
            }
            return customStatusResponse.OK200("Aution found", dataDTO);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }

    }

    @PutMapping("/UpdateAution/{id}")
    public ResponseEntity<ProductDTO> updateAution(@PathVariable Long id, @RequestBody @Valid AutionDTO autionDTO, BindingResult rs) {
        try {
            if(rs.hasErrors()){
                var errors = getDataErrorUtils.DataError(rs);
                return customStatusResponse.BADREQUEST400("Provider data is incorrect",errors);
            }
            AutionDTO updateData = autionService.updateAution(id, autionDTO);
            if (updateData == null) {
                return customStatusResponse.NOTFOUND404("Product not found");
            }
            return customStatusResponse.OK200("Product updated", updateData);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }
//@PostMapping("/addnew")
//public ResponseEntity<AutionEntity> createNew(@RequestBody @Valid AutionEntity autionEntity, BindingResult rs) {
//    try {
//        if(rs.hasErrors()){
//            var errors = getDataErrorUtils.DataError(rs);
//            return customStatusResponse.BADREQUEST400("Provider data is incorrect",errors);
//        }
//
//        AutionEntity newEntity = autionService.createNew(autionEntity);
//        if(newEntity == null ){
//            return customStatusResponse.BADREQUEST400("Aution ID Not Found");
//
//        }
//        return customStatusResponse.CREATED201("Aution created", newEntity);
//    } catch (Exception e) {
//        return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
//    }
//
//}
}
