package com.olabode.intuit.docmanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.olabode.intuit.docmanager.domain.MetaDataTag;
import com.olabode.intuit.docmanager.domain.security.User;
import com.olabode.intuit.docmanager.dto.MetaDataTagVO;
import com.olabode.intuit.docmanager.repository.MetadataTagRepository;
import com.olabode.intuit.docmanager.repository.UserRepository;
import com.olabode.intuit.docmanager.service.MetaDataTagService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MetaDataTagServiceImpl implements MetaDataTagService {

    @Autowired
    private MetadataTagRepository metadataTagRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<MetaDataTagVO> GetAllUserMetaDataTag(Long userId) {
        List<MetaDataTagVO> metaDataTagList = new ArrayList<>();
        metadataTagRepository.findByUserId(userId).forEach(
                (metaDataTag) -> {
                    metaDataTagList.add(modelMapper.map(metaDataTag, MetaDataTagVO.class));
                }
        );

        return metaDataTagList;
    }

    @Override
    public MetaDataTagVO GetMetaDataTag(long id) {
        return modelMapper.map(metadataTagRepository.findOne(id), MetaDataTagVO.class);
    }



    @Override
    @Transactional()
    public MetaDataTagVO CreateMetaDataTag(MetaDataTagVO metaDataTagVO) {
        MetaDataTag existingMetaData = metadataTagRepository.findByUserIdAndDescription(metaDataTagVO.getUserId(), metaDataTagVO.getDescription());
        if (existingMetaData != null) {
            return modelMapper.map(existingMetaData, MetaDataTagVO.class);
        } else {
            MetaDataTag metaDataTag = new MetaDataTag();
            User user = userRepository.findOne(metaDataTagVO.getUserId());
            metaDataTag.setUser(user);
            metaDataTag.setDescription(metaDataTagVO.getDescription());
            return modelMapper.map(metadataTagRepository.save(metaDataTag), MetaDataTagVO.class);
        }
    }

    @Override
    @Transactional()
    public void DeleteMetaDataTag(Long id) {
        metadataTagRepository.delete(id);
    }
}
