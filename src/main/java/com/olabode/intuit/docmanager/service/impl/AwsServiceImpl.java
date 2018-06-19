package com.olabode.intuit.docmanager.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.olabode.intuit.docmanager.dto.DocumentVO;
import com.olabode.intuit.docmanager.repository.DocumentMetadataRepository;
import com.olabode.intuit.docmanager.service.AwsService;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AwsServiceImpl implements AwsService {
    @Autowired
    private DocumentMetadataRepository documentMetadataRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final String FolderEndString = "/";
    private static final String FileExistSuffix = "copy";

    @Value("${aws-bucket-name}")
    private String bucketName;


    @Override
    public DocumentVO createFolder(String folderName) {
        PutObjectResult folderCreateResult = new PutObjectResult();
        try{
            folderCreateResult = getS3Service().putObject(bucketName, formatFolderName(folderName), "");
        }
        catch (Exception ex) {
            //TO DO: Do Something with this exception
        }

        return modelMapper.map(folderCreateResult, DocumentVO.class);
    }

    @Override
    public void deleteFolder(String folderName) {


           try{
                getS3Service().deleteObject(new DeleteObjectRequest(bucketName, formatFolderName(folderName)));
           }
           catch (AmazonClientException ex) {
               //TO DO: Do Something with this exception
           }

    }

    @Override
    public DocumentVO createFile(String folderName, MultipartFile fileToUpload) {
        AmazonS3 s3Client = getS3Service();
        String fileUploadKey = getFileUploadKey(formatFileName(folderName, fileToUpload), s3Client);
        PutObjectResult fileCreateResult = new PutObjectResult();

        try{
            fileCreateResult = s3Client.putObject(new PutObjectRequest(bucketName, fileUploadKey, multipartToFile(fileToUpload)));
        }
        catch (Exception ex) {
            //TO DO: Do Something with this exception
        }

        return modelMapper.map(fileCreateResult, DocumentVO.class);
    }

    @Override
    public void deleteFile(String fileKey) {


        try{
            getS3Service().deleteObject(new DeleteObjectRequest(bucketName, fileKey));
            documentMetadataRepository.deleteByDocumentKey(fileKey);

        }
        catch (AmazonClientException ex) {
            //TO DO: Do Something with this exception
        }

    }

    @Override
    public List<DocumentVO> listFolder(String folderName) {
        List<DocumentVO> documentList = new ArrayList<>();
        getS3Service().
                listObjects(new ListObjectsRequest().withBucketName(bucketName).
                        withPrefix(folderName)).getObjectSummaries()
                .forEach(s3ObjectSummary -> {
                    documentList.add(modelMapper.map(s3ObjectSummary, DocumentVO.class));
                });

        return  documentList;
    }

    private AmazonS3 getS3Service(){
        ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
        try {
            credentialsProvider.getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                            "Please make sure that your credentials file is at the correct " +
                            "location (~/.aws/credentials), and is in valid format.",
                    e);
        }

        return AmazonS3ClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion("us-east-2")
                .build();

    }



    private String formatFolderName(String folderName){
        return (folderName.endsWith(FolderEndString)) ? folderName :  String.format("%s%s",folderName, FolderEndString);
    }

    private String formatFileName(String folderName, String fileName){
        return String.format("%s%s", formatFolderName(folderName), fileName);
    }

    private String formatFileName(String folderName, MultipartFile fileToUpload){
        return formatFileName(folderName, fileToUpload.getOriginalFilename());
    }


    private String getFileUploadKey(String fileUploadName, AmazonS3 s3Client){
        int i = 1;
        while(s3Client.doesObjectExist(bucketName, fileUploadName)){
            String suffix = String.format("%s%d", FileExistSuffix, i);
            fileUploadName = String.format("%s%s", fileUploadName.replace(suffix, ""), suffix);
            getFileUploadKey(fileUploadName, s3Client);
        }

        return fileUploadName;
    }

    public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException
    {
        File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") +
                multipart.getOriginalFilename());
        multipart.transferTo(tmpFile);
        return tmpFile;
    }

}
