package io.rudra.hublimath.tags.service;


import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


/**
 * 10/07/2017
 * @author doug
 */
@Service
@Transactional
public class ShoppingCartService {
	/*
	private static final String CART_ATTRIBUTE_NAME = "shoppingcart";

	@Autowired
	private HttpSession httpSession;
			
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;

	public ShoppingCart checkout(String name,String emailid,ShoppingCart payload){
		//ShoppingCart shoppingCart = getShoppingCartInSession();
		shoppingCart.getProductOrders().stream().forEach(p -> p.setShoppingCart(shoppingCart));
		shoppingCart.setPhoneno(payload.getPhoneno());
		shoppingCart.setAddress1(payload.getAddress1());
		shoppingCart.setAddress2(payload.getAddress2());
		//shoppingCart.setProd_frontnm(payload.getProd_frontnm());
		//shoppingCart.setProd_frontdesign(payload.getProd_frontdesign());
		//shoppingCart.setProd_backnm(payload.getProd_backnm());
		//shoppingCart.setProd_backinfo(payload.getProd_backinfo());
		shoppingCart.setStatus(payload.getStatus());
		shoppingCart.setCreated_date(payload.getCreated_date());
		shoppingCart.setName(name);
		shoppingCart.setEmailid(emailid);
		return shoppingCartRepository.save(shoppingCart);
	}
	
*/
}
