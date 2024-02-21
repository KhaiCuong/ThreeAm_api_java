package com.project.threeam.services;

import com.project.threeam.dtos.AutionDTO;
import com.project.threeam.dtos.BidDTO;
import com.project.threeam.dtos.CategoryDTO;
import com.project.threeam.entities.*;
import com.project.threeam.repositories.AutionRepository;
import com.project.threeam.repositories.BidRepository;
import com.project.threeam.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidService {
    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AutionRepository autionRepository;

    @Autowired
    private UserRepository userRepository;


    public List<BidDTO> getAlls() {
        List<BidEntity> list = bidRepository.findAll();
        return list.stream().map(this::convertToDTO).toList();

    }

    public BidDTO createNew(BidDTO bidDTO) {
        try {
            Optional<AutionEntity> dataExits = autionRepository.findByAutionId(bidDTO.getAutionId());
            Optional<UserEntity> dataUserExits = userRepository.findByUserId(bidDTO.getUserId());
            if(dataExits.isPresent() && dataUserExits.isPresent()) {
                AutionEntity entity = dataExits.get();
                UserEntity user = dataUserExits.get();

                BidEntity newEntity = new BidEntity();
                newEntity.setPidPice(bidDTO.getPidPice());
                newEntity.setUserBidEntity(user);
                newEntity.setAutionBidEntity(entity);

                BidEntity savedEntity = bidRepository.save(newEntity);
                return convertToDTO(savedEntity);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return null;

    }


    private BidDTO convertToDTO(BidEntity bidEntity) {
        return modelMapper.map(bidEntity, BidDTO.class);
    }
}
