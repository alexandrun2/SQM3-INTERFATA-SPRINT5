package org.scrum.client.dto;

import java.io.Serializable;
import java.util.Date;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement(name="issue") 
@XmlAccessorType(XmlAccessType.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
//@Entity
public class WarrantyIssue  implements /*Comparable<WarrantyIssue>,*/ Serializable {
//@Id /*@GeneratedValue @NotNull*/
private Integer issue_id;
private String issue_details;
//@Temporal(value = TemporalType.DATE)
private Date issue_date;
private String status;
//@ManyToOne
private Warranty warranty;
//@ManyToOne
private Employee employee;


@XmlElement
public Integer getIssue_id() {
	return issue_id;
}
public void setIssue_id(Integer issue_id) {
	this.issue_id = issue_id;
}

@XmlElement
public String getIssue_details() {
	return issue_details;
}
public void setIssue_details(String issue_details) {
	this.issue_details = issue_details;
}

@XmlElement
public Date getIssue_date() {
	return issue_date;
}
public void setIssue_date(Date issue_date) {
	this.issue_date = issue_date;
}

@XmlElement
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}

//@XmlElement
public Warranty getWarranty() {
	return warranty;
}
public void setWarranty(Warranty warranty) {
	this.warranty = warranty;
}

//@XmlElement
public Employee getEmployee() {
	return employee;
}
public void setEmployee(Employee employee) {
	this.employee = employee;
}

public WarrantyIssue(Integer issue_id, String issue_details, Date issue_date, String status, Employee employee) {
	super();
	this.issue_id = issue_id;
	this.issue_details = issue_details;
	this.issue_date = issue_date;
	this.status = status;
	this.employee = employee;
}

public WarrantyIssue() {
	super();
	// TODO Auto-generated constructor stub
}
public WarrantyIssue(Integer issue_id, String issue_details, Date issue_date, String status, Warranty warranty) {
	super();
	this.issue_id = issue_id;
	this.issue_details = issue_details;
	this.issue_date = issue_date;
	this.status = status;
	this.warranty = warranty;
}
/*
@Override
public int compareTo(WarrantyIssue o) {
	// TODO Auto-generated method stub
	if (this.equals(o))
		return 0;
	return this.getIssue_id().compareTo(o.getIssue_id());
}
*/






}
