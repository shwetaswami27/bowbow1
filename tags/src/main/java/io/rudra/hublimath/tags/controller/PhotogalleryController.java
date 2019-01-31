package io.rudra.hublimath.tags.controller;

import io.rudra.hublimath.tags.dao.Photogallerydao;
import io.rudra.hublimath.tags.entities.Photogallery;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static io.rudra.hublimath.tags.entities.Constants.PRODUCT;
import static io.rudra.hublimath.tags.entities.Constants.UPLOADED_inGALLERY;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class PhotogalleryController {
    private final Logger logger = LoggerFactory.getLogger(PhotogalleryController.class);

    @Autowired
    private Photogallerydao photogallerydao;

    @RequestMapping(value = "/secured/api/PhotoGallery", method = RequestMethod.POST)
    public ResponseEntity<?> uploadinPhotoGallery(
            @ApiParam(name = "file", value = "Select the file to Upload", required = true)
            @RequestPart("file") MultipartFile uploadfile) {

        logger.debug("Single file upload!");
        logger.debug("fileName : " + uploadfile.getOriginalFilename());
        //logger.debug("contentType : " + uploadfile.getContentType());
        //logger.debug("contentSize : " + uploadfile.getSize());

        if (uploadfile.isEmpty()) {
            return new ResponseEntity<String>("please select a file!", HttpStatus.OK);
        }

        try {

            //saveUploadedFiles(Arrays.asList(uploadfile),prod_price,size);
            List<MultipartFile> files = Arrays.asList(uploadfile);
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    continue;
                }
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_inGALLERY + file.getOriginalFilename());
                Files.write(path, bytes);
                saveMetaData(file);
            }

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Successfully uploaded in PhotoGallery - " + uploadfile.getOriginalFilename(),
                new HttpHeaders(), HttpStatus.OK);

    }

    private void saveMetaData(MultipartFile file) throws IOException {
        Photogallery metaData = new Photogallery();
        Path path = Paths.get(PRODUCT + file.getOriginalFilename());
        int pos2 = file.getOriginalFilename().lastIndexOf(".");
        if (pos2 > 0) {
            metaData.setFilename(file.getOriginalFilename().substring(0, pos2));
        }
        metaData.setFilefullpath(path.toString());
        metaData.setFnameextn(file.getOriginalFilename());
        photogallerydao.save(metaData);
    }

    @RequestMapping(value="/secured/api/PhotoGallery/{filename}",method=RequestMethod.DELETE )
    public ResponseEntity<?> deleteIMGPhotoGallery(@PathVariable String filename) {
        // Delete entry from DB
        Photogallery data = photogallerydao.findByFilename(filename);
        photogallerydao.delete(data.getId());
        // Delete file from folder location
        File file = new File(UPLOADED_inGALLERY+filename+".jpg");
        file.delete();
        return new ResponseEntity<String>("Successfully deleted from PhotoGallery" , HttpStatus.OK);
    }

    @RequestMapping(value = "/showme/PhotoGallery/all", method = RequestMethod.GET)
    public List<Photogallery> viewPhotoGallery() throws IOException {
        List<Photogallery> data= photogallerydao.findAll();
        return data;
    }

}
