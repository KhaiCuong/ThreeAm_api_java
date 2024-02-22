package com.project.threeam.services;

import com.project.threeam.dtos.AutionDTO;
import com.project.threeam.dtos.CategoryDTO;
import com.project.threeam.dtos.OrderDTO;
import com.project.threeam.entities.*;
import com.project.threeam.entities.enums.OrderStatusEnum;
import com.project.threeam.repositories.AutionRepository;
import com.project.threeam.repositories.FeedbackRepository;
import com.project.threeam.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    private AutionDTO convertToDTO(AutionEntity autionEntity) {
        return modelMapper.map(autionEntity, AutionDTO.class);
    }
}
