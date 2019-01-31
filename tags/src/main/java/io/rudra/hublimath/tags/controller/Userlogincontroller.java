package io.rudra.hublimath.tags.controller;



import io.rudra.hublimath.tags.dao.Userlogindao;
import io.rudra.hublimath.tags.entities.AESEncryption;
import io.rudra.hublimath.tags.entities.ChangePassword;
import io.rudra.hublimath.tags.entities.UserLogin;
import io.rudra.hublimath.tags.security.JwtGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class Userlogincontroller {

    private static final Logger logger = LoggerFactory.getLogger(Userlogincontroller.class);

    @Autowired
    Userlogindao userlogindao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JavaMailSender sender;

    private JwtGenerator jwtGenerator;
    //private JwtTokenUtil jwtGenerator;
    public Userlogincontroller(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @GetMapping(value = "/tag/LoginCheck/")
    public ResponseEntity<?> loginUser(@RequestParam String emailid,@RequestParam String password)  {
        logger.info("Returning all the Users");
        Map<String,Object> map = new HashMap<>();
        ResponseEntity<Map<UserLogin,Object>> entity=null;

        UserLogin userLogin = userlogindao.findByEmailid(emailid);
        if (userLogin == null || userLogin.getEmailid().length() <= 0){
            return new ResponseEntity<String>("Please register!!", HttpStatus.OK);
        }
        if (userLogin != null ){
            String decryptedString = AESEncryption.decrypt(userLogin.getPwd());
            if( !decryptedString.equals(password) ) {
                return new ResponseEntity<String>("Password is incorrect", HttpStatus.OK);
            }
        }
        userLogin.setPwd("*");
        map.put("msg",userLogin);
        map.put("token",jwtGenerator.generate(userLogin));
        map.put("status","Successfully Logged In");
        entity=new ResponseEntity(map,HttpStatus.OK);
        return entity;
    }

    @PostMapping(value = "/tag/RegisterUser")
    public ResponseEntity<?> signupUser(@RequestBody UserLogin payload)  {
        logger.info("Payload to save " + payload);
        UserLogin login = new UserLogin();
        //PayloadValidator.checkIfalreadyexist(payload);
        UserLogin userLogin = userlogindao.findByEmailid(payload.getEmailid());
        if (userLogin != null){
            if(userLogin.getEmailid().equals(payload.getEmailid()) ){
                return new ResponseEntity<String>("User is already present in our record", HttpStatus.OK);
            }
        }
        else {

            login.setName(payload.getName());
            login.setEmailid(payload.getEmailid());
            login.setRole("NON_ADMIN");
            login.setPwd(AESEncryption.encrypt(payload.getPwd()));
            userlogindao.save(login);
        }
        return new ResponseEntity<String>("Your account is created " , HttpStatus.OK);
    }

    @GetMapping("/forgotpwd/sendMail/{emailid}/")
    public ResponseEntity<?> ForgotPassword(@PathVariable String emailid) {
        //check is it registered mail id with us
        UserLogin userLogin = userlogindao.findByEmailid(emailid);
        if (userLogin == null){
            return new ResponseEntity<String>("Please enter correct mail id!!", HttpStatus.OK);
        }
        MimeMessage message = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(emailid);
            helper.setText("Your password is : "+AESEncryption.decrypt(userLogin.getPwd()));
            helper.setSubject("BowBowTags.com");
        } catch (MessagingException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Error while sending mail", HttpStatus.OK);
        }
        sender.send(message);
        return new ResponseEntity<String>( "Please check your mail : "+userLogin.getEmailid(), HttpStatus.OK);
    }

    @PutMapping(value = "/secured/tag/ChangePassword/")
    public ResponseEntity<?> ChangePassword(@RequestBody ChangePassword payload)  {
        UserLogin userLogin = userlogindao.findByEmailid(payload.getEmailid());
        if (userLogin == null){
            return new ResponseEntity<String>("Please enter correct mail id!!", HttpStatus.OK);
        }

        if(AESEncryption.decrypt(userLogin.getPwd()).equals(payload.getOldpwd())) {
            userLogin.setPwd(AESEncryption.encrypt(payload.getNewpwd()));
            userlogindao.save(userLogin);
        }

        return new ResponseEntity<String>( "Your Password is changed ", HttpStatus.OK);
    }

}
