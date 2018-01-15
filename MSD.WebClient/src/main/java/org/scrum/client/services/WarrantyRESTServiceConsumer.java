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

import org.scrum.client.dto.Warranty;




@Named @SessionScoped
public class WarrantyRESTServiceConsumer  implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(WarrantyRESTServiceConsumer.class.getName());
	//
	private static String serviceURL = "http://localhost:8080/alexandru/data/warranties";
	
	// private List<Project> projects = new ArrayList<Project>();
	
	public List<Warranty> getWarranties() {
		logger.info(">>> REST Consumer: getProjects.");
		List<Warranty> warranties = new ArrayList<Warranty>();
		warranties.addAll(this.getAllWarranties());
		return warranties;
	}

	@PostConstruct
	public void init() {
		//this.projects.addAll(this.getAllProjects());
	}
	
	// RESTfull CRUD methods
	public Collection<Warranty> getAllWarranties() {
		logger.info("DEBUG: ProjectsRESTServiceConsumer: getAllProjects ...");
		Collection<Warranty> warranties = ClientBuilder.newClient()
				.target(serviceURL)
				.request().get()
				.readEntity(new GenericType<Collection<Warranty>>(){});
		//projects.stream().forEach(System.out::println);
		return warranties;
	}

	public List<Warranty> addWarranty(Warranty warranty) {
		logger.info("DEBUG: ProjectsRESTServiceConsumer: addProject(): " + warranty);
		Client client = ClientBuilder.newClient();
		Collection<Warranty> warranties;
		
		warranties = client.target(serviceURL)
				.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(warranty, MediaType.APPLICATION_JSON))
				.readEntity(new GenericType<Collection<Warranty>>(){});
		
		// projects.stream().forEach(System.out::println);
		return new ArrayList<>(warranties);
	}	
	
	public Collection<Warranty> deleteWarranty(Warranty warranty) {
		logger.info("DEBUG: ProjectsRESTServiceConsumer: deleteProject: " + warranty);
		
		String resourceURL = serviceURL + "/";
		
		Client client = ClientBuilder.newClient();
		client.target(resourceURL + warranty.getWarranty_id()).request().delete();
		
		Collection<Warranty> warrantiesAfterDelete = client.target(serviceURL)
				.request().get()
				.readEntity(new GenericType<Collection<Warranty>>(){});	
		
		warrantiesAfterDelete.stream().forEach(System.out::println);
		return warrantiesAfterDelete;
	}
	
	public Warranty getByID(Integer warrantyNo) {
		String resourceURL = serviceURL + "/" + warrantyNo;
		logger.info("DEBUG: ProjectsRESTServiceConsumer: getByID: " + warrantyNo);
		
		Warranty warranty = ClientBuilder.newClient().target(resourceURL)
				.request().accept(MediaType.APPLICATION_JSON)
				.get().readEntity(Warranty.class);
		
		assertNotNull("REST Data Service failed!", warranty);
		logger.info(">>>>>> DEBUG: REST Response ..." + warranty);
		
		return warranty;
	}	
	
	public Warranty updateWarranty(Warranty warranty) {
		String resourceURL = serviceURL + "/" + warranty.getWarranty_id();
		logger.info("DEBUG: ProjectsRESTServiceConsumer: project: " + warranty);
		Client client = ClientBuilder.newClient();

		// Get project server version
		Warranty serverWarranty = client.target(resourceURL)
				.request().accept(MediaType.APPLICATION_JSON)
				.get().readEntity(Warranty.class);
		
		logger.info(">>> Server-side Project: " + serverWarranty);
		
		warranty = client.target(resourceURL)
				//.request().accept(MediaType.APPLICATION_XML).header("Content-Type", "application/xml")
				.request().accept(MediaType.APPLICATION_JSON)
				.put(Entity.entity(warranty, MediaType.APPLICATION_JSON))
				.readEntity(Warranty.class);
		
		logger.info(">>> Updated Project: " + warranty);
		return warranty;
	}
	
	public Warranty createWarranty() {
		String resourceURL = serviceURL + "/new/"; //createNewProject
		logger.info("DEBUG: ProjectsRESTServiceConsumer: CreateProject ");

		Warranty warranty = ClientBuilder.newClient().target(resourceURL + 999)
					.request().accept(MediaType.APPLICATION_JSON)
					.post(null).readEntity(Warranty.class);
		logger.info(">>> Created/posted Project: " + warranty);
		
		return warranty;
	}	
}
