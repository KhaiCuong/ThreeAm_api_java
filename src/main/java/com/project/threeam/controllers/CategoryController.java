package com.project.threeam.controllers;

import com.project.threeam.dtos.CategoryDTO;
import com.project.threeam.response.CustomStatusResponse;
import com.project.threeam.services.CategoryService;
import com.project.threeam.utils.GetDataErrorUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/Category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CustomStatusResponse customStatusResponse;

    @Autowired
    private GetDataErrorUtils getDataErrorUtils;

    @GetMapping("/GetCategoryList")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        try {
            List<CategoryDTO> categorys = categoryService.getAllCategories();
            if (categorys.isEmpty()) {
                return customStatusResponse.NOTFOUND404("No Category found");

            }
            return customStatusResponse.OK200("Get List of Category Successfully", categorys);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }

    @GetMapping("/GetCategory/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable String categoryId) {
        try {
            CategoryDTO CategoryDTO = categoryService.getCategoryById(categoryId);
            if (CategoryDTO == null) {
                return customStatusResponse.NOTFOUND404("Category not found");
            }
            return customStatusResponse.OK200("Category found", CategoryDTO);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }

    }

    @PostMapping("/AddCategory")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody @Valid CategoryDTO CategoryDTO, BindingResult rs) {
        try {
            if(rs.hasErrors()){
                var errors = getDataErrorUtils.DataError(rs);
                return customStatusResponse.BADREQUEST400("Provider data is incorrect",errors);
            }
            String categoryId = CategoryDTO.getCategoryId();
            CategoryDTO CategoryDTOCheck = categoryService.getCategoryById(categoryId);
            if(CategoryDTOCheck != null ) {
                return customStatusResponse.BADREQUEST400("Category Id is Aldredy used");
            }

            CategoryDTO createdCategory = categoryService.createCategory(CategoryDTO);
            if(createdCategory == null ){
                return customStatusResponse.BADREQUEST400("Category ID Not Found");

            }
            return customStatusResponse.CREATED201("Category created", createdCategory);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }

    }

    @PutMapping("/UpdateCategory/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable @Valid String categoryId, @RequestBody CategoryDTO CategoryDTO, BindingResult rs) {
        try {
            if(rs.hasErrors()){
                var errors = getDataErrorUtils.DataError(rs);
                return customStatusResponse.BADREQUEST400("Provider data is incorrect",errors);
            }
            CategoryDTO updatedCategory = categoryService.updateCategory(categoryId, CategoryDTO);
            if (updatedCategory == null) {
                return customStatusResponse.NOTFOUND404("Category not found");
            }
            return customStatusResponse.OK200("Category updated", updatedCategory);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }

    @DeleteMapping("/DeleteCategory/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String categoryId) {
        try {
            boolean checkCategory =   categoryService.deleteCategory(categoryId);
            if (checkCategory) {
                return customStatusResponse.OK200("Category deleted");
            } else {
                return customStatusResponse.NOTFOUND404("Category not found");
            }
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }
}
