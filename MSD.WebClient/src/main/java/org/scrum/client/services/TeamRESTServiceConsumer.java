package org.scrum.client.services;

import static org.junit.Assert.assertNotNull;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.scrum.client.dto.Team;





@Named @SessionScoped
public class TeamRESTServiceConsumer  implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(TeamRESTServiceConsumer.class.getName());
	//
	private static String serviceURL = "http://localhost:8080/alexandru/data/teams";
	
	// private List<Project> projects = new ArrayList<Project>();
	
	public List<Team> getTeams() {
		logger.info(">>> REST Consumer: getProjects.");
		List<Team> teams = new ArrayList<Team>();
		teams.addAll(this.getAllTeams());
		return teams;
	}

	@PostConstruct
	public void init() {
		//this.projects.addAll(this.getAllProjects());
	}
	
	// RESTfull CRUD methods
	public Collection<Team> getAllTeams() {
		logger.info("DEBUG: ProjectsRESTServiceConsumer: getAllProjects ...");
		Collection<Team> teams = ClientBuilder.newClient()
				.target(serviceURL)
				.request().get()
				.readEntity(new GenericType<Collection<Team>>(){});
		//projects.stream().forEach(System.out::println);
		return teams;
	}

	public List<Team> addTeam(Team team) {
		logger.info("DEBUG: ProjectsRESTServiceConsumer: addProject(): " + team);
		Client client = ClientBuilder.newClient();
		Collection<Team> teams;
		
		teams = client.target(serviceURL)
				.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(team, MediaType.APPLICATION_JSON))
				.readEntity(new GenericType<Collection<Team>>(){});
		
		// projects.stream().forEach(System.out::println);
		return new ArrayList<>(teams);
	}	
	
	public Collection<Team> deleteTeam(Team team) {
		logger.info("DEBUG: ProjectsRESTServiceConsumer: deleteProject: " + team);
		
		String resourceURL = serviceURL + "/";
		
		Client client = ClientBuilder.newClient();
		client.target(resourceURL + team.getTeam_id()).request().delete();
		
		Collection<Team> teamsAfterDelete = client.target(serviceURL)
				.request().get()
				.readEntity(new GenericType<Collection<Team>>(){});	
		
		teamsAfterDelete.stream().forEach(System.out::println);
		return teamsAfterDelete;
	}
	
	public Team getByID(Integer teamNo) {
		String resourceURL = serviceURL + "/" + teamNo;
		logger.info("DEBUG: ProjectsRESTServiceConsumer: getByID: " + teamNo);
		
		Team team = ClientBuilder.newClient().target(resourceURL)
				.request().accept(MediaType.APPLICATION_JSON)
				.get().readEntity(Team.class);
		
		assertNotNull("REST Data Service failed!", team);
		logger.info(">>>>>> DEBUG: REST Response ..." + team);
		
		return team;
	}	
	
	public Team updateTeam(Team team) {
		String resourceURL = serviceURL + "/" + team.getTeam_id();
		logger.info("DEBUG: ProjectsRESTServiceConsumer: project: " + team);
		Client client = ClientBuilder.newClient();

		// Get project server version
		Team serverTeam = client.target(resourceURL)
				.request().accept(MediaType.APPLICATION_JSON)
				.get().readEntity(Team.class);
		
		logger.info(">>> Server-side Project: " + serverTeam);
		
		team = client.target(resourceURL)
				//.request().accept(MediaType.APPLICATION_XML).header("Content-Type", "application/xml")
				.request().accept(MediaType.APPLICATION_JSON)
				.put(Entity.entity(team, MediaType.APPLICATION_JSON))
				.readEntity(Team.class);
		
		logger.info(">>> Updated Project: " + team);
		return team;
	}
	
	public Team createTeam() {
		String resourceURL = serviceURL + "/new/"; //createNewProject
		logger.info("DEBUG: ProjectsRESTServiceConsumer: CreateProject ");

		Team team = ClientBuilder.newClient().target(resourceURL + 999)
					.request().accept(MediaType.APPLICATION_JSON)
					.post(null).readEntity(Team.class);
		logger.info(">>> Created/posted Project: " + team);
		
		return team;
	}	
}
