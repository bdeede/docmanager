package com.olabode.intuit.docmanager.repository;



import java.util.List;

import com.olabode.intuit.docmanager.domain.MetaDataTag;

import org.springframework.data.repository.CrudRepository;


public interface MetadataTagRepository extends CrudRepository<MetaDataTag, Long> {
    List<MetaDataTag> findByUserId(Long userId);
    MetaDataTag findByUserIdAndDescription(Long userId, String description);
}
