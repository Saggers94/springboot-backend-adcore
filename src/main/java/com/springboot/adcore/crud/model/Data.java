package com.springboot.adcore.crud.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "data")
public class Data {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long data_id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name="description",length=2147483647, columnDefinition="text")
	private String description;
	
	@Column(name="parent")
	private int parent;
	
	
	@Column(name="read_only")
	private boolean read_only;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created",nullable=false)
	private Date created;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated",nullable=false)
	private Date updated;	
	
	@PrePersist
	protected void onCreate() {
		updated = created = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		updated = new Date();
	}
	
	public Data(){
		
	}
	
	public Data(String name, String description,int parent,boolean read_only, Date created, Date updated) {
		super();
		this.name = name;
		this.description = description;
		this.parent = parent;
		this.read_only = read_only;
		this.created = created;
		this.updated = updated;
	}
	
	public long getData_id() {
		return data_id;
	}

	public void setData_id(long data_id) {
		this.data_id = data_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public boolean getRead_only() {
		return read_only;
	}

	public void setRead_only(boolean read_only) {
		this.read_only = read_only;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
}
