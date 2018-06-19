package com.olabode.intuit.docmanager.service;

import java.util.List;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.olabode.intuit.docmanager.dto.DocumentVO;

import org.springframework.web.multipart.MultipartFile;

public interface AwsService {

    DocumentVO createFolder(String folderName);

     void deleteFolder(String FolderName);

     DocumentVO createFile(String folderName, MultipartFile fileToUpload);

     void deleteFile(String fileKey);

    List<DocumentVO> listFolder(String folderName);
    
}
