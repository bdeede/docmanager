package com.olabode.intuit.docmanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.olabode.intuit.docmanager.domain.DocumentMetaData;
import com.olabode.intuit.docmanager.dto.DocumentMetaDataVO;
import com.olabode.intuit.docmanager.repository.DocumentMetadataRepository;
import com.olabode.intuit.docmanager.repository.MetadataTagRepository;
import com.olabode.intuit.docmanager.service.DocumentMetaDataService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocumentMetaDataServiceImpl implements DocumentMetaDataService {

    @Autowired
    private DocumentMetadataRepository documentMetadataRepository;

    @Autowired
    private MetadataTagRepository metadataTagRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Long> GetTopUsedMetaDataTags() {
        List<?> result = documentMetadataRepository.FindTopMostUsedMetadataTagIds();
        return new ArrayList<>();
    }


    @Override
    public List<DocumentMetaDataVO> GetAllDocumentMetaData(String documentKey) {
        List<DocumentMetaDataVO> documentMetaDataTagList = new ArrayList<>();
        documentMetadataRepository.findByDocumentKey(documentKey).forEach(
                (metaDataTag) -> {
                    documentMetaDataTagList.add(modelMapper.map(metaDataTag, DocumentMetaDataVO.class));
                }
        );
        return documentMetaDataTagList;
    }

    @Override
    public DocumentMetaDataVO GetDocumentMetaData(long id) {
        return modelMapper.map(documentMetadataRepository.findOne(id), DocumentMetaDataVO.class);
    }

    @Override
    @Transactional()
    public DocumentMetaDataVO CreateDocumentMetaData(DocumentMetaDataVO documentMetaDataVO) {
        DocumentMetaData existingDocumentMetaData = documentMetadataRepository.findByDocumentKeyAndMetaDataTagId(documentMetaDataVO.getDocumentKey(), documentMetaDataVO.getMetaDataTagId());
        if (existingDocumentMetaData != null) {
            return modelMapper.map(existingDocumentMetaData, DocumentMetaDataVO.class);
        } else {
            DocumentMetaData documentMetaData = new DocumentMetaData();
            documentMetaData.setMetaDataTag(metadataTagRepository.findOne(documentMetaDataVO.getMetaDataTagId()));
            documentMetaData.setDocumentKey(documentMetaDataVO.getDocumentKey());
            documentMetaData.setValue(documentMetaDataVO.getValue());
            return modelMapper.map(documentMetadataRepository.save(documentMetaData), DocumentMetaDataVO.class);
        }
    }

    @Override
    @Transactional()
    @Modifying
    public void DeleteDocumentMetaData(Long id) {
        documentMetadataRepository.delete(id);
    }
}
