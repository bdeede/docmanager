package com.olabode.intuit.docmanager.controller;



import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


import com.olabode.intuit.docmanager.domain.DocumentMetaData;
import com.olabode.intuit.docmanager.dto.DocumentMetaDataVO;
import com.olabode.intuit.docmanager.dto.DocumentVO;
import com.olabode.intuit.docmanager.dto.MetaDataTagVO;
import com.olabode.intuit.docmanager.service.AwsService;
import com.olabode.intuit.docmanager.service.DocumentMetaDataService;
import com.olabode.intuit.docmanager.service.MetaDataTagService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/aws")
public class AwsController {
    @Autowired
    private AwsService awsService;

    @Autowired
    private MetaDataTagService metaDataTagService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DocumentMetaDataService documentMetaDataService;

    private  static final long userId  = 1;

    @RequestMapping(value ="/folders/{folderKey}/items", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('STANDARD_USER')")
    public List<DocumentVO>  getFolderItems(@PathVariable("folderKey") String folderKey){
        try {
            folderKey = URLDecoder.decode(folderKey, "UTF-8");
        }
        catch (UnsupportedEncodingException  ex){
            //TO DO: Log this
        }
        return awsService.listFolder(folderKey);
    }

    @RequestMapping(value ="/folders/{folderKey}", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('STANDARD_USER')")
    public DocumentVO createFolder(@PathVariable("folderKey") String folderKey){
        return awsService.createFolder(folderKey);
    }

    @RequestMapping(value ="/folders/{folderKey}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('STANDARD_USER')")
    public String deleteFolder(@PathVariable("folderKey") String folderKey){
        awsService.deleteFolder(folderKey);
        return folderKey;
    }

    @RequestMapping(value ="/folders/{folderKey}/files/upload", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('STANDARD_USER')")
    public DocumentVO createFile(@PathVariable("folderKey") String folderKey, @RequestParam("file") MultipartFile file){
        try {
            folderKey = URLDecoder.decode(folderKey, "UTF-8");
        }
        catch (UnsupportedEncodingException  ex){
            //TO DO: Log this
        }
         return awsService.createFile(folderKey, file);
    }

    @RequestMapping(value ="/folders/{folderKey}/files/uploadMuiltple",  method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('STANDARD_USER')")
    public List<DocumentVO> createFile(@PathVariable("folderKey") String folderKey, @RequestParam("files") MultipartFile[] files){
        List<DocumentVO> documentList = new ArrayList<>();
        try {
            folderKey = URLDecoder.decode(folderKey, "UTF-8");
        }
        catch (UnsupportedEncodingException  ex){
            //TO DO: Log this
        }

        for(MultipartFile file : files){
            documentList.add(awsService.createFile(folderKey, file));
        }
        return documentList;
    }

    @RequestMapping(value ="/folders/{folderKey}/files/{fileKey:.+}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('STANDARD_USER')")
    public String deleteFolder(@PathVariable("folderKey") String folderKey, @PathVariable("fileKey") String fileKey) {
        try {
            fileKey = URLDecoder.decode(fileKey, "UTF-8");
        }
        catch (UnsupportedEncodingException  ex){
            //TO DO: Log this
        }
        awsService.deleteFile(fileKey);
        return folderKey;
    }

    @RequestMapping(value ="/metadatatag", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('STANDARD_USER')")
    public MetaDataTagVO addMetaDataTag(@RequestBody MetaDataTagVO metaDataTag) {

        return metaDataTagService.CreateMetaDataTag(metaDataTag);
    }

    @RequestMapping(value ="/metadatatag/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('STANDARD_USER')")
    public long deleteMetaDataTag(@PathVariable("id") long id) {

         metaDataTagService.DeleteMetaDataTag(id);
         return id;
    }

    @RequestMapping(value ="/metadatatag/", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('STANDARD_USER')")
    public List<MetaDataTagVO> GetAllUserMetaDataTag() {

        return metaDataTagService.GetAllUserMetaDataTag(userId);
    }

    @RequestMapping(value ="/metadatatag/getmostused", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('STANDARD_USER')")
    public List<Long> GetMostUserMetaDataTag() {

        return documentMetaDataService.GetTopUsedMetaDataTags();
    }

    @RequestMapping(value ="/documentmetadata/{docKey}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('STANDARD_USER')")
    public List<DocumentMetaDataVO> GetAllDocumentMetaData(@PathVariable("docKey") String docKey) {
        try {
            docKey = URLDecoder.decode(docKey, "UTF-8");
        }
        catch (UnsupportedEncodingException  ex){
            //TO DO: Log this
        }
        List<DocumentMetaDataVO> listOfDocumentTags = documentMetaDataService.GetAllDocumentMetaData(docKey);
        return listOfDocumentTags;
    }

    @RequestMapping(value ="/documentmetadata", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('STANDARD_USER')")
    public DocumentMetaDataVO addDocumentMetadata(@RequestBody DocumentMetaDataVO documentMetaDataVO) {
        return documentMetaDataService.CreateDocumentMetaData(documentMetaDataVO);
    }

    @RequestMapping(value ="/documentmetadata/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('STANDARD_USER')")
    public long deleteDocumentMetaData(@PathVariable("id") long id) {

         documentMetaDataService.DeleteDocumentMetaData(id);
         return id;
    }
}
