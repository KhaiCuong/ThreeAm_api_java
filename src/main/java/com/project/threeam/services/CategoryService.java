package com.project.threeam.services;

import com.project.threeam.dtos.CategoryDTO;
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
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<CategoryDTO> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categories.stream().map(this::convertToDTO).toList();
    }

    public CategoryDTO getCategoryById(String categoryId) {
        Optional<CategoryEntity> category = categoryRepository.findByCategoryId(categoryId);
        return category.map(this::convertToDTO).orElse(null);
    }


    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = modelMapper.map(categoryDTO, CategoryEntity.class);
        CategoryEntity savedEntity = categoryRepository.save(categoryEntity);
        return convertToDTO(savedEntity);
    }

    public CategoryDTO updateCategory(String categoryId, CategoryDTO categoryDTO) {
        Optional<CategoryEntity> existingCategoryOptional = categoryRepository.findByCategoryId(categoryId);
        if (existingCategoryOptional.isPresent()) {
            CategoryEntity existingCategory = existingCategoryOptional.get();

            if (categoryDTO.getCategoryId() != null) {
                existingCategory.setCategoryId(categoryDTO.getCategoryId());
            }
            if (categoryDTO.getCategory_name() != null) {
                existingCategory.setCategory_name(categoryDTO.getCategory_name());
            }

            if (categoryDTO.getStatus() != null) {
                existingCategory.setStatus(categoryDTO.getStatus());
            }
            if (categoryDTO.getDescription() != null) {
                existingCategory.setDescription(categoryDTO.getDescription());
            }

            CategoryEntity savedEntity = categoryRepository.save(existingCategory);

            return convertToDTO(savedEntity);
        } else {
            return null;
        }
    }

    public boolean deleteCategory(String categoryId) {
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findByCategoryId(categoryId);
        if (optionalCategoryEntity.isPresent()) {
            CategoryEntity categoryEntity = optionalCategoryEntity.get();
            categoryRepository.delete(categoryEntity);
            return true;
        } return false;
    }
    private CategoryDTO convertToDTO(CategoryEntity categoryEntity) {
        return modelMapper.map(categoryEntity, CategoryDTO.class);
    }
}
