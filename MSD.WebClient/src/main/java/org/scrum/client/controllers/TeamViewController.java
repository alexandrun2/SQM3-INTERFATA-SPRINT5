package org.scrum.client.controllers;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.scrum.client.dto.Team;
import org.scrum.client.services.TeamRESTServiceConsumer;

// projectsViewController
@Named @SessionScoped
public class TeamViewController implements Serializable{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(TeamViewController.class.getName());
	// Data Model
	private List<Team> teams = new ArrayList<Team>();
	//
	@Inject
	private TeamRESTServiceConsumer restService; 
	//
	public List<Team> getTeams() {
		return teams;
	}

	@PostConstruct
	private void init() {
		this.teams = restService.getTeams();
		if (this.getTeams().size() >0)
			this.selectedTeam = this.getTeams().get(0);
	}
	//
	private Team selectedTeam;

	public Team getSelectedTeam() {
		return selectedTeam;
	}

	// Data Grid support
	public void setSelectedTeam(Team selectedTeam) {
		this.selectedTeam = selectedTeam;
		System.out.println(">>> >>>> selectedProject: " + this.selectedTeam);
	}
	
	// Filtering Support
	private List<Team> filteredTeams = new ArrayList<Team>();

	public List<Team> getFilteredTeams() {
		return filteredTeams;
	}

	public void setFilteredTeams(List<Team> filteredTeams) {
		logger.info("filteredProjects ::: ");
		if (filteredTeams != null)
			filteredTeams.stream().forEach(p -> System.out.println(p.getTeam_name()));
		else
			logger.info(">>> NULL ....");
		
		this.filteredTeams = filteredTeams;
	}
	
	public boolean filterByDate(Object value, Object filter, Locale locale) {

        if( filter == null ) {
            return true;
        }

        if( value == null ) {
            return false;
        }

       Date dateValue = (Date) value;
       Date dateFilter = (Date) filter;
       
       SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
       return dateFormat.format(dateFilter).equals(dateFormat.format(dateValue));
    }	
	
	public void onRowSelect(SelectEvent event) {
		System.out.println(">>> >>>> onRowSelect ==> selectedProject is: " + this.selectedTeam);
	}
	
	public void saveSelectedTeam(ActionEvent actionEvent) {
        addMessage("Saved selected project => " + this.selectedTeam.getTeam_name());
        this.teams = this.restService.addTeam(this.selectedTeam);
    }	
	
	public void addNewTeam(ActionEvent actionEvent) {
		this.selectedTeam = this.restService.createTeam();
		this.teams = this.restService.getTeams();
		addMessage("NEW project => " + this.selectedTeam.getTeam_name());
	}
	
	public void deleteTeam(ActionEvent actionEvent) {
		addMessage("Deleted project => " + this.selectedTeam.getTeam_name());
		if (this.selectedTeam != null)
				this.restService.deleteTeam(this.selectedTeam);
		this.teams = this.restService.getTeams();
		if (!this.teams.isEmpty())
			this.selectedTeam = this.teams.get(0);
		
	}
	//
	public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }	
	// Data Table Editable
	public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        addMessage("Cell Changed: " + "Old: " + oldValue + ", New:" + newValue);
    }
}
// https://api.jqueryui.com/theming/icons/
// http://www.logicbig.com/tutorials/java-ee-tutorial/jsf/custom-component-basic/
/*
	<f:facet name="first">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
	</f:facet>
*/