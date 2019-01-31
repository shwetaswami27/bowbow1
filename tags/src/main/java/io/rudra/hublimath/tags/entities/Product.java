package io.rudra.hublimath.tags.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "products")
public class Product {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
	private long id;
    
    @Column(name = "name")
	private String name;
	
    @Column(name = "unit_price")
	private BigDecimal price;

    @Column(name = "size")
    private String size;
    
    @Column(name= "filefullpath")
    private String filefullpath;

	String prod_frontnm;
	String prod_frontdesign;
	String prod_backnm;
	String prod_backinfo;
	private Integer quantity;
	String additional_info;
	String category;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name="cartid")
	private ShoppingCart cartss;
	public ShoppingCart getCartss() {
		return cartss;
	}
	public void setCartss(ShoppingCart cartss) {
		this.cartss = cartss;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getFilefullpath() {
		return filefullpath;
	}

	public void setFilefullpath(String filefullpath) {
		this.filefullpath = filefullpath;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getProd_frontnm() {
		return prod_frontnm;
	}

	public void setProd_frontnm(String prod_frontnm) {
		this.prod_frontnm = prod_frontnm;
	}

	public String getProd_frontdesign() {
		return prod_frontdesign;
	}

	public void setProd_frontdesign(String prod_frontdesign) {
		this.prod_frontdesign = prod_frontdesign;
	}

	public String getProd_backnm() {
		return prod_backnm;
	}

	public void setProd_backnm(String prod_backnm) {
		this.prod_backnm = prod_backnm;
	}

	public String getProd_backinfo() {
		return prod_backinfo;
	}

	public void setProd_backinfo(String prod_backinfo) {
		this.prod_backinfo = prod_backinfo;
	}

	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getAdditional_info() {
		return additional_info;
	}

	public void setAdditional_info(String additional_info) {
		this.additional_info = additional_info;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
