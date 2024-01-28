package com.project.threeam.controllers;

import com.project.threeam.entities.ProductEntity;
import com.project.threeam.response.CustomStatusResponse;
import com.project.threeam.dtos.ProductDTO;
import com.project.threeam.services.ProductService;
import com.project.threeam.utils.GetDataErrorUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;




import java.util.List;

@RestController
@RequestMapping("/api/Product")  // Adjust the base path if needed
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomStatusResponse customStatusResponse;

    @Autowired
    private GetDataErrorUtils getDataErrorUtils;


    @GetMapping("/GetProductList")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        try {
            List<ProductDTO> products = productService.getAllProducts();
            if (products.isEmpty()) {
                return customStatusResponse.NOTFOUND404("No Product found");

            }
            return customStatusResponse.OK200("Get List of Product Successfully", products);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }

    @GetMapping("/GetProductListByCategoryId/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getAllProductsBycategory(@PathVariable String categoryId) {
        try {
            List<ProductDTO> products = productService.getProductsByCategoryd(categoryId);
            if (products.isEmpty()) {
                return customStatusResponse.NOTFOUND404("No Product found");
            }
            return customStatusResponse.OK200("Get List of Product Successfully", products);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }

    @GetMapping("/GetProduct/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String productId) {
        try {
            ProductDTO productDTO = productService.getProductById(productId);
            if (productDTO == null) {
                return customStatusResponse.NOTFOUND404("Product not found");
            }
            return customStatusResponse.OK200("Product found", productDTO);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }

    }

    @PostMapping("/AddProduct")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO productDTO, BindingResult rs) {
        try {
            if(rs.hasErrors()){
                var errors = getDataErrorUtils.DataError(rs);
                return customStatusResponse.BADREQUEST400("Provider data is incorrect",errors);
            }
            String productId = productDTO.getProductId();
            ProductDTO productDTOCheck = productService.getProductById(productId);
            if(productDTOCheck != null ) {
                return customStatusResponse.BADREQUEST400("Product Id is Aldredy used");
            }

            ProductDTO createdProduct = productService.createProduct(productDTO);
            if(createdProduct == null ){
                return customStatusResponse.BADREQUEST400("Category ID Not Found");

            }
            return customStatusResponse.CREATED201("Product created", createdProduct);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }

    }

    @PutMapping("/UpdateProduct/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable String productId, @RequestBody @Valid ProductDTO productDTO, BindingResult rs) {
        try {
            if(rs.hasErrors()){
                var errors = getDataErrorUtils.DataError(rs);
                return customStatusResponse.BADREQUEST400("Provider data is incorrect",errors);
            }
            ProductDTO updatedProduct = productService.updateProduct(productId, productDTO);
            if (updatedProduct == null) {
                return customStatusResponse.NOTFOUND404("Product not found");
            }
            return customStatusResponse.OK200("Product updated", updatedProduct);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }

    @PutMapping("/UpdateStatusProduct/{productId}")
    public ResponseEntity<ProductDTO> updateProductStatus(@PathVariable String productId) {
        try {
            Boolean updatedProductStatus = productService.updateProductStatus(productId);
            if (updatedProductStatus == false) {
                return customStatusResponse.NOTFOUND404("Product not found");
            }
            return customStatusResponse.OK200("Product updated");
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }

    @PutMapping("/UpdateProductInStock/{productId}")
    public ResponseEntity<ProductDTO> UpdateProductInStock(@PathVariable String productId, @RequestBody Integer quantity) {
        try {
            Boolean updatedProductInstock = productService.updateProductInStock(productId, quantity);
            if (updatedProductInstock == false) {
                return customStatusResponse.NOTFOUND404("Product not found");
            }
            return customStatusResponse.OK200("Product updated");
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }

    @DeleteMapping("/DeleteProduct/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        try {
            boolean checkProduct =   productService.deleteProduct(productId);
            if (checkProduct) {
                return customStatusResponse.OK200("Feedback deleted");
            } else {
                return customStatusResponse.NOTFOUND404("FeedBack not found");
            }
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }

}