package com.olabode.intuit.docmanager.service;

import java.util.List;

import com.olabode.intuit.docmanager.domain.MetaDataTag;
import com.olabode.intuit.docmanager.dto.MetaDataTagVO;


public interface MetaDataTagService {
    List<MetaDataTagVO> GetAllUserMetaDataTag(Long userId);
    MetaDataTagVO GetMetaDataTag(long id);
    MetaDataTagVO CreateMetaDataTag(MetaDataTagVO metaDataTag);
    void DeleteMetaDataTag(Long id);
}
