package com.project.threeam.controllers;

import com.project.threeam.dtos.ProductDTO;
import com.project.threeam.entities.ProductImageEntity;
import com.project.threeam.repositories.ProductImageRepository;
import com.project.threeam.response.CustomStatusResponse;
import com.project.threeam.services.ProductImageService;
import com.project.threeam.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/ProductImage")
public class ProductImageController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private CustomStatusResponse customStatusResponse;

    @GetMapping("/GetProductImagesByProductId/{productId}")
    public ResponseEntity<List<ProductImageEntity>> getProductById(@PathVariable String productId) {
        try {
            List<String> productImage = productImageService.GetProductImageByProductId(productId);
            if (productImage == null) {
                return customStatusResponse.NOTFOUND404("Product not found");
            }
            return customStatusResponse.OK200("Product found", productImage);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }

    @PostMapping("/AddProductImages")
    public ResponseEntity<ProductImageEntity> createProduct(@RequestParam("files") MultipartFile[] files,String productId) {
        try {
            List<String> productImageWithProductId = productImageService.GetProductImageByProductId(productId);

            if(productImageWithProductId != null ) {
                Boolean imageDeleted = productImageService.deleteMultiImage(productId);
                if(imageDeleted == false ){
                    return customStatusResponse.BADREQUEST400("Add Product image failed!");
                }
            }

            Set<String> productImageAdded = productImageService.uploadMultiImage(files,productId);
            if(productImageAdded == null ){
                return customStatusResponse.BADREQUEST400("Add Product image failed!");
            }
            return customStatusResponse.CREATED201("Product Image Added", productImageAdded);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }

    }

    @PostMapping("/UpdateProductImagesByProductId/{productId}")
    public ResponseEntity<ProductImageEntity> UpdateProduct(@RequestParam("files") MultipartFile[] files,@PathVariable String productId) {
        try {
            List<String> productImageWithProductId = productImageService.GetProductImageByProductId(productId);

            if(productImageWithProductId != null ) {
                Boolean imageDeleted = productImageService.deleteMultiImage(productId);
                if(imageDeleted == false ){
                    return customStatusResponse.BADREQUEST400("Add Product image failed!");
                }
            }

            Set<String> productImageAdded = productImageService.uploadMultiImage(files,productId);
            if(productImageAdded == null ){
                return customStatusResponse.BADREQUEST400("Add Product image failed!");
            }
            return customStatusResponse.CREATED201("Product Image Added", productImageAdded);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }
}
