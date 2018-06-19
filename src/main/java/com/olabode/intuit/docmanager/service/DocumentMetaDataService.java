package com.olabode.intuit.docmanager.service;

import java.util.List;

import com.olabode.intuit.docmanager.domain.DocumentMetaData;
import com.olabode.intuit.docmanager.dto.DocumentMetaDataVO;


public interface DocumentMetaDataService {
    List<DocumentMetaDataVO> GetAllDocumentMetaData(String documentKey);
    List<Long> GetTopUsedMetaDataTags();
    DocumentMetaDataVO GetDocumentMetaData(long id);
    DocumentMetaDataVO CreateDocumentMetaData(DocumentMetaDataVO documentMetaData);
    void DeleteDocumentMetaData(Long id);

}
