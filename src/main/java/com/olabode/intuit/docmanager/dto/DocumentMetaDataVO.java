package com.olabode.intuit.docmanager.dto;

public class DocumentMetaDataVO {
    private Long id;
    private String documentKey;
    private Long metaDataTagId;
    private String value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDocumentKey() {
        return documentKey;
    }

    public void setDocumentKey(String documentKey) {
        this.documentKey = documentKey;
    }

    public Long getMetaDataTagId() {
        return metaDataTagId;
    }

    public void setMetaDataTagId(Long metaDataTagId) {
        this.metaDataTagId = metaDataTagId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
