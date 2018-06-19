package com.olabode.intuit.docmanager.repository;




import java.util.List;

import com.olabode.intuit.docmanager.domain.DocumentMetaData;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface DocumentMetadataRepository extends CrudRepository<DocumentMetaData, Long> {

    void deleteByDocumentKey(String documentKey);

    List<DocumentMetaData> findByDocumentKey(String documentKey);

    DocumentMetaData findByDocumentKeyAndMetaDataTagId(String documentKey, Long metaDataTagId);

    @Query(value = "SELECT  metadata_tag_id, count(id) as cnt FROM document_metadata group by metadata_tag_id order by cnt desc Limit 10", nativeQuery = true)
    List<?> FindTopMostUsedMetadataTagIds();

}
