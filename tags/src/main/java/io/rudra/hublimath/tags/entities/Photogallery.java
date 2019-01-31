package io.rudra.hublimath.tags.entities;

import javax.persistence.*;


@Entity
@Table(name = "photogallery")
public class Photogallery {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	String filename;
	String filefullpath;
	String fnameextn;

	public Photogallery() {
	}

	public Photogallery(long id, String filename, String filefullpath, String fnameextn) {
		this.id = id;
		this.filename = filename;
		this.filefullpath = filefullpath;
		this.fnameextn = fnameextn;
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

	public String getFnameextn() {
		return fnameextn;
	}

	public void setFnameextn(String fnameextn) {
		this.fnameextn = fnameextn;
	}
}
