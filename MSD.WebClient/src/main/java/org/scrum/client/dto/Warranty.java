package org.scrum.client.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement(name="warranty") 
@XmlAccessorType(XmlAccessType.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
//@Entity
public class Warranty implements  Serializable{
//@Id 
private Integer warranty_id;
//@Temporal(value = TemporalType.DATE)
private Date date_created;
private Integer Valability;
private String license_key;
private String description;
//@OneToOne
private SoftwareProduct software;
//@ManyToOne
//private Client client;
//@OneToMany(mappedBy = "warranty", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
private List<WarrantyIssue> warranty_issues = new ArrayList<>();

@XmlElement
public Integer getWarranty_id() {
	return warranty_id;
}
public void setWarranty_id(Integer warranty_id) {
	this.warranty_id = warranty_id;
}

@XmlElement
public Date getDate_created() {
	return date_created;
}
public void setDate_created(Date date_created) {
	this.date_created = date_created;
}

@XmlElement
public Integer getValability() {
	return Valability;
}
public void setValability(Integer valability) {
	Valability = valability;
}

@XmlElement
public String getLicense_key() {
	return license_key;
}
public void setLicense_key(String license_key) {
	this.license_key = license_key;
}

@XmlElement
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}

@XmlElement
public SoftwareProduct getSoftware() {
	return software;
}
public void setSoftware(SoftwareProduct software) {
	this.software = software;
}

/*@XmlElement
public Client getClient() {
	return client;
}
public void setClient(Client client) {
	this.client = client;
}*/

@XmlElementWrapper(name = "warrantyissues") @XmlElement(name = "issue")
public List<WarrantyIssue> getWarranty_issues() {
	return warranty_issues;
}
public void setWarranty_issues(List<WarrantyIssue> warranty_issues) {
	this.warranty_issues = warranty_issues;
}

public Warranty(Integer warranty_id, Integer valability, String license_key, String description) {
	super();
	this.warranty_id = warranty_id;
	Valability = valability;
	this.license_key = license_key;
	this.description = description;
}
public Warranty() {
	super();
	// TODO Auto-generated constructor stub
}
public Warranty(Integer warranty_id, Date date_created, Integer valability, String license_key, String description) {
	super();
	this.warranty_id = warranty_id;
	this.date_created = date_created;
	Valability = valability;
	this.license_key = license_key;
	this.description = description;
	
}
public Warranty(Integer warranty_id, Date date_created, Integer valability, String license_key, String description,
		SoftwareProduct software /*, Client client*/) {
	super();
	this.warranty_id = warranty_id;
	this.date_created = date_created;
	Valability = valability;
	this.license_key = license_key;
	this.description = description;
	this.software = software;
	/*this.client = client;*/
}

public static String BASE_URL = "http://localhost:8080/alexandru/data/warranties/";
@XmlElement(name = "link")
public AtomLink getLink() throws Exception {
	String restUrl = BASE_URL + this.getWarranty_id();
    return new AtomLink(restUrl, "get-team");
}	

public void setLink(AtomLink link){}





}
