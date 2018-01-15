package org.scrum.client.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement(name="team") 
@XmlAccessorType(XmlAccessType.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
//@Entity
public class Team implements Serializable {

	//@Id
	private Integer team_id;
	private String team_name;
	//@OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Employee> employee = new ArrayList<>();
	//@OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	//private Set<Request> requests = new HashSet<Request>();
	
	@XmlElement
	public Integer getTeam_id() {
		return team_id;
	}
	public void setTeam_id(Integer team_id) {
		this.team_id = team_id;
	}
	
	@XmlElement
	public String getTeam_name() {
		return team_name;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	
	
	@XmlElementWrapper(name = "employees") @XmlElement(name = "employee")
	public List<Employee> getEmployee() {
		return employee;
	}
	public void setEmployee(List<Employee> employee) {
		this.employee = employee;
	}
	
	/*
	 * //@XmlElementWrapper(name = "requests") @XmlElement(name = "request")
	public Set<Request> getRequests() {
		return requests;
	}
	public void setRequests(Set<Request> requests) {
		this.requests = requests;
	}
	*/
	public Team(Integer team_id, String team_name) {
		super();
		this.team_id = team_id;
		this.team_name = team_name;
	}
	public Team(Integer team_id, String team_name, List<Employee> employee) {
		super();
		this.team_id = team_id;
		this.team_name = team_name;
		this.employee = employee;
	}
	public Team() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public static String BASE_URL = "http://localhost:8080/alexandru/data/teams/";
	@XmlElement(name = "link")
    public AtomLink getLink() throws Exception {
		String restUrl = BASE_URL + this.getTeam_id();
        return new AtomLink(restUrl, "get-team");
    }	
	
	public void setLink(AtomLink link){}
	
	
	
	
}
