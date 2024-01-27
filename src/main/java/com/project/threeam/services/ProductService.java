package com.project.threeam.services;

import com.project.threeam.dtos.ProductDTO;
import com.project.threeam.entities.CategoryEntity;
import com.project.threeam.entities.ProductEntity;
import com.project.threeam.repositories.CategoryRepository;
import com.project.threeam.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ProductDTO> getAllProducts() {
        List<ProductEntity> products = productRepository.findAll();

        List<ProductDTO> productDTOs = new ArrayList<>();
        for (ProductEntity product : products) {
            ProductDTO productDTO = convertToDTO(product);
          try {
              productDTO.setCategory_id(product.getCategoryEntity().getCategoryId());
              productDTOs.add(productDTO);
          } catch (Exception e) {
              productDTOs.add(productDTO);
          }
        }
        return productDTOs;
    }

    public ProductDTO getProductById(String productId) {
        Optional<ProductEntity> productOptional = productRepository.findByProductId(productId);

        if(productOptional.isPresent()) {
            ProductEntity product = productOptional.get();
            ProductDTO productDTO = convertToDTO(product);
            try {
                productDTO.setCategory_id(product.getCategoryEntity().getCategoryId());
            } catch (Exception e) {
            }
            return productDTO;
        } else { return null;}

    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        try {
            Optional<CategoryEntity> categoryExits = categoryRepository.findByCategoryId(productDTO.getCategory_id());
            if(categoryExits.isPresent()) {
                CategoryEntity category = categoryExits.get();
                ProductEntity productEntity = modelMapper.map(productDTO, ProductEntity.class);
                productEntity.setCategoryEntity(category);
                ProductEntity savedEntity = productRepository.save(productEntity);
                return convertToDTO(savedEntity);
            } else {
                return null;
            }

        } catch (Exception e) {
        e.getMessage();
        }
        return null;

    }

    public ProductDTO updateProduct(String productId, ProductDTO productDTO) {
        Optional<ProductEntity> existingProductOptional = productRepository.findByProductId(productId);
        if (existingProductOptional.isPresent()) {
            // Update existing entity directly
            ProductEntity existingProduct = existingProductOptional.get();

            if (productDTO.getProductId() != null) {
                existingProduct.setProductId(productDTO.getProductId());
            }
            if (productDTO.getProduc_name() != null) {
                existingProduct.setProduc_name(productDTO.getProduc_name());
            }
            if (productDTO.getInstock() != null) {
                existingProduct.setInstock(productDTO.getInstock());
            }
            if (productDTO.getPrice() != null) {
                existingProduct.setPrice(productDTO.getPrice());
            }
            if (productDTO.getStatus() != null) {
                existingProduct.setStatus(productDTO.getStatus());
            }
            if (productDTO.getDescription() != null) {
                existingProduct.setDescription(productDTO.getDescription());
            }
            if (productDTO.getImage() != null) {
                existingProduct.setImage(productDTO.getImage());
            }

            if (productDTO.getCategory_id() != null) {
                Optional<CategoryEntity> categoryExits = categoryRepository.findByCategoryId(productDTO.getCategory_id());
                if(categoryExits.isPresent()) {
                    CategoryEntity category = categoryExits.get();
                    existingProduct.setCategoryEntity(category);
                } else {
                    return null;
                }

            }
            ProductEntity savedEntity = productRepository.save(existingProduct);  // Save the updated entity

            return convertToDTO(savedEntity);
        } else {
            return null;
        }
    }

    public Boolean deleteProduct(String productId) {
        Optional<ProductEntity> optionalProductEntity = productRepository.findByProductId(productId);
        if (optionalProductEntity.isPresent()) {
            ProductEntity productEntity = optionalProductEntity.get();
            productRepository.delete(productEntity);
            return true;
        } return false;
    }

    public List<ProductDTO> getProductsByCategoryd(String categoryId) {
        Optional<CategoryEntity> categoryExits = categoryRepository.findByCategoryId(categoryId);
        CategoryEntity category = categoryExits.get();

        Optional<List<ProductEntity>> productsOptional = productRepository.findByCategoryEntity(category);
        List<ProductEntity> products = productsOptional.get();
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (ProductEntity product : products) {
            ProductDTO productDTO = convertToDTO(product);
            try {
                productDTO.setCategory_id(product.getCategoryEntity().getCategoryId());
                productDTOs.add(productDTO);
            } catch (Exception e) {
                productDTOs.add(productDTO);
            }
        }
        return productDTOs;
    }


    public Boolean updateProductInStock(String productId, Integer quantity) {
        Optional<ProductEntity> productOptional = productRepository.findByProductId(productId);

        if(productOptional.isPresent()) {
            ProductEntity product = productOptional.get();
            try {
                Integer qtt = product.getInstock() - quantity;
                product.setInstock(qtt);
                productRepository.save(product);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else { return false;}
    }

    public Boolean updateProductStatus(String productId) {
        Optional<ProductEntity> productOptional = productRepository.findByProductId(productId);

        if(productOptional.isPresent()) {
            ProductEntity product = productOptional.get();
            try {
                Boolean updateStatus = !product.getStatus();
                product.setStatus(updateStatus);
                productRepository.save(product);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else { return false;}
    }



    private ProductDTO convertToDTO(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductDTO.class);
    }


}
