package com.project.threeam.services;

import com.project.threeam.dtos.AutionDTO;
import com.project.threeam.dtos.CategoryDTO;
import com.project.threeam.dtos.OrderDTO;
import com.project.threeam.dtos.ProductDTO;
import com.project.threeam.entities.*;
import com.project.threeam.entities.enums.OrderStatusEnum;
import com.project.threeam.repositories.AutionRepository;
import com.project.threeam.repositories.FeedbackRepository;
import com.project.threeam.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AutionService {

    @Autowired
    private AutionRepository autionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepository productRepository;

//    public List<AutionDTO> getAlls() {
//        List<AutionEntity> list = autionRepository.findAll();
//        return list.stream().map(this::convertToDTO).toList();
//    }

    public List<AutionEntity> getAlls() {
        List<AutionEntity> list = autionRepository.findAll();
        return list;
    }


    public AutionDTO createNew(AutionDTO autionDTO) {
        try {
            Optional<ProductEntity> dataExits = productRepository.findByProductId(autionDTO.getProductId());
            if(dataExits.isPresent()) {
                ProductEntity entity = dataExits.get();
                AutionEntity newEntity = new AutionEntity();
                newEntity.setName(autionDTO.getName());
                newEntity.setStatus(autionDTO.getStatus());
                newEntity.setLastBidPrice(autionDTO.getLastBidPrice());
                newEntity.setImage(autionDTO.getImage());
                newEntity.setStartTime(autionDTO.getStartTime());
                newEntity.setEndTime(autionDTO.getEndTime());
                newEntity.setAutionProductEntity(entity);
                AutionEntity savedEntity = autionRepository.save(newEntity);
                return convertToDTO(savedEntity);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return null;

    }
//    public AutionEntity createNew(AutionEntity autionEntity) {
//        try {
//            AutionEntity savedEntity = autionRepository.save(autionEntity);
//            return savedEntity;
//
//        } catch (Exception e) {
//            e.getMessage();
//        }
//        return null;
//
//    }

    public AutionDTO getAutionDTOById(Long autionID) {
        Optional<AutionEntity> entityOptional = autionRepository.findByAutionId(autionID);

        if(entityOptional.isPresent()) {
            AutionEntity entity = entityOptional.get();
            AutionDTO entityDTO = convertToDTO(entity);
            try {
                entityDTO.setProductId(entity.getAutionProductEntity().getProductId());
            } catch (Exception e) {
            }
            return entityDTO;
        } else { return null;}

    }

    public AutionEntity getAutionById(Long autionID) {
        Optional<AutionEntity> entityOptional = autionRepository.findByAutionId(autionID);

        if(entityOptional.isPresent()) {
            AutionEntity entity = entityOptional.get();
            return entity;
        } else { return null;}

    }

    public AutionDTO updateAution(Long id, AutionDTO autionDTO) {
        Optional<AutionEntity> existingOptional = autionRepository.findByAutionId(id);
        if (existingOptional.isPresent()) {
            // Update existing entity directly
            AutionEntity existing = existingOptional.get();

            if (autionDTO.getAutionId() != null) {
                existing.setAutionId(autionDTO.getAutionId());
            }
            if (autionDTO.getName() != null) {
                existing.setName(autionDTO.getName());
            }
            if (autionDTO.getStatus() != null) {
                existing.setStatus(autionDTO.getStatus());
            }
            if (autionDTO.getLastBidPrice() != null) {
                existing.setLastBidPrice(autionDTO.getLastBidPrice());
            }
            if (autionDTO.getImage() != null) {
                existing.setImage(autionDTO.getImage());
            }
            if (autionDTO.getStartTime() != null) {
                existing.setStartTime(autionDTO.getStartTime());
            }
            if (autionDTO.getEndTime() != null) {
                existing.setEndTime(autionDTO.getEndTime());
            }

            if (autionDTO.getProductId() != null) {
                Optional<ProductEntity> forienkeyExist = productRepository.findByProductId(autionDTO.getProductId());
                if(forienkeyExist.isPresent()) {
                    ProductEntity forienkey = forienkeyExist.get();
                    existing.setAutionProductEntity(forienkey);
                } else {
                    return null;
                }

            }
            AutionEntity savedEntity = autionRepository.save(existing);  // Save the updated entity

            return convertToDTO(savedEntity);
        } else {
            return null;
        }
    }

    public AutionDTO getAutionByProducyID(String productId) {
        Optional<ProductEntity> productExits = productRepository.findByProductId(productId);
        if(productExits.isPresent()) {
            ProductEntity product = productExits.get();
            Optional<AutionEntity> autionOptional = autionRepository.findByAutionProductEntity(product);
            if(autionOptional.isPresent()) {
                AutionEntity autionEntity = autionOptional.get();
                AutionDTO autionDTO = convertToDTO(autionEntity);
                return autionDTO;
            } else {return  null;}
        } else {return  null;}

    }


    private AutionDTO convertToDTO(AutionEntity autionEntity) {
        return modelMapper.map(autionEntity, AutionDTO.class);
    }
}
