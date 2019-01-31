package io.rudra.hublimath.tags.entities;

import javax.persistence.*;
import java.math.BigDecimal;

/** An entity that stores file meta data into database*/
@Entity
@Table(name = "admin_products")
public class FileUploadMetaData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	String filename;
	String filefullpath;
	String fnameextn;
	BigDecimal prod_price;
	String size;

	String prod_info;
	String category;

	public FileUploadMetaData() {
	}

	public FileUploadMetaData(long id, String filename, String filefullpath, String fnameextn, BigDecimal prod_price, String size, String prod_info, String category) {
		this.id = id;
		this.filename = filename;
		this.filefullpath = filefullpath;
		this.fnameextn = fnameextn;
		this.prod_price = prod_price;
		this.size = size;
		this.prod_info = prod_info;
		this.category = category;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilefullpath() {
		return filefullpath;
	}

	public void setFilefullpath(String filefullpath) {
		this.filefullpath = filefullpath;
	}

	public BigDecimal getProd_price() {
		return prod_price;
	}

	public void setProd_price(BigDecimal prod_price) {
		this.prod_price = prod_price;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getFnameextn() {
		return fnameextn;
	}

	public void setFnameextn(String fnameextn) {
		this.fnameextn = fnameextn;
	}

	public String getProd_info() {
		return prod_info;
	}

	public void setProd_info(String prod_info) {
		this.prod_info = prod_info;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
