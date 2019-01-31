package io.rudra.hublimath.tags.controller;

import io.rudra.hublimath.tags.dao.FileUploaddao;
import io.rudra.hublimath.tags.dao.ShoppingCartRepository;
import io.rudra.hublimath.tags.entities.FileUploadMetaData;
import io.rudra.hublimath.tags.entities.Mail;
import io.rudra.hublimath.tags.entities.ShoppingCart;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.rudra.hublimath.tags.entities.Constants.PRODUCT;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class RestUploadcontroller {
    private final Logger logger = LoggerFactory.getLogger(RestUploadcontroller.class);

    //@Value("${upload.path}")
    //private String UPLOADED_FOLDER;
    @Autowired
    private FileUploaddao fileUploadMetaData;
    @Autowired
    private ShoppingCartRepository ShoppingCartRepository1;
    @Autowired
    private JavaMailSender sender;

    @RequestMapping(value = "/secured/api/fileupload", method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(
            @ApiParam(name = "file", value = "Select the file to Upload", required = true)
            @RequestPart("file") MultipartFile uploadfile,
            @RequestParam("price") BigDecimal prod_price,
            @RequestParam("size") String size,
            @RequestParam("prod_info") String prod_info,
            @RequestParam("category") String category) {

        /** Below data is what we saving into database */
        logger.debug("Single file upload!");
        logger.debug("fileName : " + uploadfile.getOriginalFilename());
        logger.debug("contentType : " + uploadfile.getContentType());
        logger.debug("contentSize : " + uploadfile.getSize());

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
                Path path = Paths.get(PRODUCT + file.getOriginalFilename());
                //Duplicate check :start
                FileUploadMetaData data = fileUploadMetaData.findByFilefullpath(path.toString());
                if (data != null){
                    if(data.getFilefullpath().equals(path.toString()) ){
                        return new ResponseEntity<String>(file.getOriginalFilename() + " : Prodcut is alreayd presnt "  , HttpStatus.OK);
                    }
                }
                //Duplicate check :end
                Files.write(path, bytes);
                saveMetaData(file,prod_price,size,prod_info,category);
            }

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Successfully uploaded - " + uploadfile.getOriginalFilename(),
                new HttpHeaders(), HttpStatus.OK);

    }
    /*
    private void saveUploadedFiles(List<MultipartFile> files,int prod_price,String size) throws IOException {
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            saveMetaData(file,prod_price,size);
        }
    }
    */
    private void saveMetaData(MultipartFile file, BigDecimal prod_price, String size,
                              String prod_info,String category) throws IOException {
        FileUploadMetaData metaData = new FileUploadMetaData();
        Path path = Paths.get(PRODUCT + file.getOriginalFilename());
        int pos2 = file.getOriginalFilename().lastIndexOf(".");
        if (pos2 > 0) {
            metaData.setFilename(file.getOriginalFilename().substring(0, pos2));
        }

        metaData.setFilefullpath(path.toString());
        metaData.setFnameextn(file.getOriginalFilename());
        //metaData.setFilecontentType(file.getContentType());
        //metaData.setFilecontentSize(file.getSize());
        metaData.setProd_price(prod_price);
        metaData.setSize(size);
        metaData.setProd_info(prod_info);
        metaData.setCategory(category);
        fileUploadMetaData.save(metaData);
    }

    @RequestMapping(value="/secured/products/{filename}",method=RequestMethod.DELETE )
    public ResponseEntity<?> deleteProduct(@PathVariable String filename) {
        // Delete entry from DB
        FileUploadMetaData data = fileUploadMetaData.findByFilename(filename);
        fileUploadMetaData.delete(data.getId());
        // Delete file from folder location
        File file = new File(data.getFilefullpath());
        file.delete();
        return new ResponseEntity<String>("Successfully deleted " , HttpStatus.OK);
    }

    @RequestMapping(value = "/showme/all", method = RequestMethod.GET)
    public List<FileUploadMetaData> viewallproducts() throws IOException {
        List<FileUploadMetaData> data= fileUploadMetaData.findAll();
        return data;
    }

    @RequestMapping(value = "/showme/product/{category}", method = RequestMethod.GET)
    public ResponseEntity<?> viewprodBycategory(@PathVariable String category) throws IOException {

        Map<String,Object> map = new HashMap<>();
        ResponseEntity<Map<FileUploadMetaData,Object>> entity=null;

        List<FileUploadMetaData> data= fileUploadMetaData.findByCategory(category);
        if (data == null ){
            return new ResponseEntity<String>("Product not found!!", HttpStatus.OK);
        }
        map.put("msg",data);
        map.put("status","Success !!");
        entity=new ResponseEntity(map,HttpStatus.OK);
        return entity;
    }


    //Number of order - "received" till date
    @GetMapping(value = "/orderdetail/get/{status}")
    public List<ShoppingCart> getorderByStatus(@PathVariable String status)  {
        logger.info("Getting list of orders ");
        List<ShoppingCart> carts = ShoppingCartRepository1.findByStatus(status);
        return carts;
    }

    //update status as "delivered" after product dispatch
    @PutMapping(value = "/secured/orderdetail/put/{id}")
    public ResponseEntity<?> updateasdelivered(@PathVariable long id)  {
        logger.info("Getting list of orders ");
        ShoppingCart cart = ShoppingCartRepository1.findOne(id);

        if (cart == null ){
            return new ResponseEntity<String>("Order not found!!", HttpStatus.OK);
        }
        if(cart.getStatus().equals("received")){
            cart.setStatus("delivered");
            ShoppingCartRepository1.save(cart);
        }
        return new ResponseEntity<String>("Order#"+cart.getId()+":updated as delivered", HttpStatus.OK);
    }


    @GetMapping(value = "/billdetail/get/{id}")
    public ResponseEntity<?> getbilldetail(@PathVariable long id)  {
        logger.info("Bill details");
        Map<String,Object> map = new HashMap<>();
        ResponseEntity<Map<ShoppingCart,Object>> entity=null;

        ShoppingCart cart= ShoppingCartRepository1.findOne(id);
        if (cart == null || cart.getUsername().length() <= 0){
            return new ResponseEntity<String>("Order not found !!", HttpStatus.OK);
        }
        //return cart;
        map.put("msg",cart);
        map.put("status","Bill generated");
        entity=new ResponseEntity(map,HttpStatus.OK);
        return entity;
    }

    @PostMapping("/secured/order/sendMail")
    public String sendMailBill(@RequestBody Mail mail) {
        MimeMessage message = sender.createMimeMessage();
        try {
            //MimeMessageHelper helper = new MimeMessageHelper(message,true);
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(mail.getMailTO());
            helper.setText(mail.getTextinfo());
            helper.setSubject(mail.getSubject());
            helper.setCc("bowbowtags@gmail.com");
            //  FileSystemResource file
            //          = new FileSystemResource(new File("C:/work/D_002.jpg"));
            //  helper.addAttachment(file.getFilename(), file);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error while sending mail";
        }
        sender.send(message);
        return "Mail Sent Success!";
    }

}
