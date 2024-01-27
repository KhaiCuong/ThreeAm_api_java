package com.project.threeam.services;

import com.project.threeam.dtos.CategoryDTO;
import com.project.threeam.dtos.FeedbackDTO;
import com.project.threeam.dtos.ProductDTO;
import com.project.threeam.entities.CategoryEntity;
import com.project.threeam.entities.FeedbackEntity;
import com.project.threeam.entities.ProductEntity;
import com.project.threeam.entities.UserEntity;
import com.project.threeam.repositories.CategoryRepository;
import com.project.threeam.repositories.FeedbackRepository;
import com.project.threeam.repositories.ProductRepository;
import com.project.threeam.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<FeedbackDTO> getAllFeedbacks() {
        List<FeedbackEntity> feedbacks = feedbackRepository.findAll();
        List<FeedbackDTO> feedbackDTOs = new ArrayList<>();
        for (FeedbackEntity feedback : feedbacks) {
            FeedbackDTO feedbackDTO = convertToDTO(feedback);
            try {
                feedbackDTO.setProduct_id(feedback.getProductFeedbackEntity().getProductId());
                feedbackDTO.setUser_id(feedback.getUserFeedbackEntity().getUserId());

                feedbackDTOs.add(feedbackDTO);
            } catch (Exception e) {
                feedbackDTOs.add(feedbackDTO);
            }
        }
        return feedbackDTOs;
    }

    public List<FeedbackDTO> getFeedbacksByProductID(String productID) {
        Optional<ProductEntity> productExits = productRepository.findByProductId(productID);
        ProductEntity product = productExits.get();

        Optional<List<FeedbackEntity>> feedbacksOptional = feedbackRepository.findByProductFeedbackEntity(product);
        List<FeedbackEntity> feedbacks = feedbacksOptional.get();
        List<FeedbackDTO> productDTOs = new ArrayList<>();
        for (FeedbackEntity feedback : feedbacks) {
            FeedbackDTO feedbackDTO = convertToDTO(feedback);
            try {
                feedbackDTO.setProduct_id(feedback.getProductFeedbackEntity().getProductId());
                feedbackDTO.setUser_id(feedback.getUserFeedbackEntity().getUserId());
                productDTOs.add(feedbackDTO);
            } catch (Exception e) {
                productDTOs.add(feedbackDTO);
            }
        }
        return productDTOs;
    }

    public FeedbackDTO createFeedback(FeedbackDTO feedbackDTO) {
        try {
            Optional<ProductEntity> productExits = productRepository.findByProductId(feedbackDTO.getProduct_id());
            Optional<UserEntity> userExits = userRepository.findByUserId(feedbackDTO.getUser_id());

            if(productExits.isPresent()) {
                ProductEntity product = productExits.get();
                UserEntity user = userExits.get();

                FeedbackEntity feedbackEntity = modelMapper.map(feedbackDTO, FeedbackEntity.class);
                feedbackEntity.setProductFeedbackEntity(product);
                feedbackEntity.setUserFeedbackEntity(user);

                FeedbackEntity savedEntity = feedbackRepository.save(feedbackEntity);
                return convertToDTO(savedEntity);
            } else {
                return null;
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return null;

    }
    private FeedbackDTO convertToDTO(FeedbackEntity feedbackEntity) {
        return modelMapper.map(feedbackEntity, FeedbackDTO.class);
    }
}
