package com.project.threeam.services;

import com.project.threeam.dtos.AutionDTO;
import com.project.threeam.dtos.BidDTO;
import com.project.threeam.dtos.OrderDTO;
import com.project.threeam.entities.*;
import com.project.threeam.repositories.AutionRepository;
import com.project.threeam.repositories.BidRepository;
import com.project.threeam.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public BidDTO getBidDTOById(Long autionID) {
        Optional<BidEntity> entityOptional = bidRepository.findByBidId(autionID);

        if(entityOptional.isPresent()) {
            BidEntity entity = entityOptional.get();
            BidDTO entityDTO = convertToDTO(entity);
            try {
                entityDTO.setAutionId(entity.getAutionBidEntity().getAutionId());
                entityDTO.setUserId(entity.getUserBidEntity().getUserId());

            } catch (Exception e) {
            }
            return entityDTO;
        } else { return null;}

    }

    public BidEntity getBidById(Long autionID) {
        Optional<BidEntity> entityOptional = bidRepository.findByBidId(autionID);
        if(entityOptional.isPresent()) {
            BidEntity entity = entityOptional.get();
            return entity;
        } else { return null;}

    }

    public List<BidDTO> getBidListByAutionID(Long autionId) {
        Optional<AutionEntity> autionExits = autionRepository.findByAutionId(autionId);
        if(autionExits.isPresent()) {
            AutionEntity aution = autionExits.get();
            Optional<List<BidEntity>> bid = bidRepository.findByAutionBidEntity(aution);
            List<BidEntity> bidList = bid.get();
            List<BidDTO> bidDTOList = new ArrayList<>();
            for (BidEntity biditem : bidList) {
                BidDTO bidDto = convertToDTO(biditem);
                try {
                    bidDto.setAutionId(biditem.getAutionBidEntity().getAutionId());
                    bidDTOList.add(bidDto);
                } catch (Exception e) {
                    bidDTOList.add(bidDto);
                }
            }
            return bidDTOList;
        } else {return new ArrayList<>();}

    }



    public BidDTO updateBid(Long id, BidDTO bidDTO) {
        Optional<BidEntity> existingOptional = bidRepository.findByBidId(id);
        if (existingOptional.isPresent()) {
            // Update existing entity directly
            BidEntity existing = existingOptional.get();

            if (bidDTO.getBidId() != null) {
                existing.setBidId(bidDTO.getBidId());
            }
            if (bidDTO.getPidPice() != null) {
                existing.setPidPice(bidDTO.getPidPice());
            }

            if (bidDTO.getAutionId() != null) {
                Optional<AutionEntity> forienkeyExist = autionRepository.findByAutionId(bidDTO.getAutionId());
                if(forienkeyExist.isPresent()) {
                    AutionEntity forienkey = forienkeyExist.get();
                    existing.setAutionBidEntity(forienkey);
                } else {
                    return null;
                }

            }
            BidEntity savedEntity = bidRepository.save(existing);  // Save the updated entity

            return convertToDTO(savedEntity);
        } else {
            return null;
        }
    }


    private BidDTO convertToDTO(BidEntity bidEntity) {
        return modelMapper.map(bidEntity, BidDTO.class);
    }
}
