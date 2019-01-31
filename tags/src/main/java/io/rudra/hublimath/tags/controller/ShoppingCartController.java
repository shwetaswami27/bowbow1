package io.rudra.hublimath.tags.controller;


import io.rudra.hublimath.tags.entities.ShoppingCart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping
public class ShoppingCartController {
	private static final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);
	
	@Autowired
	private io.rudra.hublimath.tags.dao.ShoppingCartRepository ShoppingCartRepository;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json", value = "/secured/cart/checkout/")
    public ResponseEntity<?> placeorder(@RequestBody ShoppingCart payload) {
		logger.info("Checkout ");
		Map<String,Object> map = new HashMap<>();
		ResponseEntity<Map<ShoppingCart,Object>> entity=null;
		if (payload.getUsername() == null){
			return new ResponseEntity<String>("Please login !!", HttpStatus.OK);
		}

		ShoppingCart cart = ShoppingCartRepository.save(payload);

		map.put("msg",cart);
		map.put("status","Order confirmed");
		entity=new ResponseEntity(map,HttpStatus.OK);
		return entity;
    }
    
}
