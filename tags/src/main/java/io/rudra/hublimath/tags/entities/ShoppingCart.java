package io.rudra.hublimath.tags.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
    private long id;
    
    private BigDecimal Totalamount;

	@OneToMany(mappedBy = "cartss",cascade = CascadeType.ALL)
	@JsonManagedReference
    private List<Product> products;

	String Username;
	String Useremailid;
	String phoneno;
	String address1;
	String address2;
	String status;
	LocalDate created_date = LocalDate.now();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getTotalamount() {
		return Totalamount;
	}

	public void setTotalamount(BigDecimal totalamount) {
		Totalamount = totalamount;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getCreated_date() {
		return created_date;
	}

	public void setCreated_date(LocalDate created_date) {
		this.created_date = created_date;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getUseremailid() {
		return Useremailid;
	}

	public void setUseremailid(String useremailid) {
		Useremailid = useremailid;
	}

}
