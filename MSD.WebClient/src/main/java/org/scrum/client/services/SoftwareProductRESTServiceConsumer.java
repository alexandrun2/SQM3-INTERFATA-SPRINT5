package org.scrum.client.services;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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

import org.scrum.client.dto.SoftwareProduct;


@Named @SessionScoped
public class SoftwareProductRESTServiceConsumer  implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SoftwareProductRESTServiceConsumer.class.getName());
	//
	private static String serviceURL = "http://localhost:8080/alexandru/data/products";
	
	private List<SoftwareProduct> sProducts = new ArrayList<SoftwareProduct>();
	
	public List<SoftwareProduct> getProducts() {
		logger.info(">>> REST Consumer: getProjects.");
		List<SoftwareProduct> products = new ArrayList<SoftwareProduct>();
		products.addAll(this.getAllProducts());
		return products;
	}
	
	

	@PostConstruct
	public void init() {
		this.sProducts.addAll(this.getAllProducts());
	}
	
	// RESTfull CRUD methods
	public Collection<SoftwareProduct> getAllProducts() {
		logger.info("DEBUG: Junit TESTING: test4_GetProjects ...");
		Collection<SoftwareProduct> products = ClientBuilder.newClient()
				.target(serviceURL)
				.request().get()
				.readEntity(new GenericType<Collection<SoftwareProduct>>(){});

		return products;
	}

	public List<SoftwareProduct> addProduct(SoftwareProduct product) {
		logger.info("DEBUG: ProjectsRESTServiceConsumer: addProject(): " + product);
		Client client = ClientBuilder.newClient();
		Collection<SoftwareProduct> products;
		
		products = client.target(serviceURL)
				.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(product, MediaType.APPLICATION_JSON))
				.readEntity(new GenericType<Collection<SoftwareProduct>>(){});
		

		return new ArrayList<>(products);
	}	
	
	public Collection<SoftwareProduct> deleteProducts(SoftwareProduct product) {
		logger.info("DEBUG: ProjectsRESTServiceConsumer: deleteProject: " + product);
		
		String resourceURL = serviceURL + "/";
		
		Client client = ClientBuilder.newClient();
		client.target(resourceURL + product.getProduct_id()).request().delete();
		
		Collection<SoftwareProduct> productsAfterDelete = client.target(serviceURL)
				.request().get()
				.readEntity(new GenericType<Collection<SoftwareProduct>>(){});	
		
		productsAfterDelete.stream().forEach(System.out::println);
		return productsAfterDelete;
	}
	
	public SoftwareProduct getByID(Integer produsNo) {
		String resourceURL = serviceURL + "/" + produsNo;
		logger.info("DEBUG: ProjectsRESTServiceConsumer: getByID: " + produsNo);
		
		SoftwareProduct product = ClientBuilder.newClient().target(resourceURL)
				.request().accept(MediaType.APPLICATION_JSON)
				.get().readEntity(SoftwareProduct.class);
		
	//	assertNotNull("REST Data Service failed!", project);
		logger.info(">>>>>> DEBUG: REST Response ..." + product);
		
		return product;
	}	
	
	public SoftwareProduct updateProduct(SoftwareProduct product) {
		String resourceURL = serviceURL + "/" + product.getProduct_id();
		logger.info("DEBUG: ProjectsRESTServiceConsumer: project: " + product);
		Client client = ClientBuilder.newClient();

		// Get project server version
		SoftwareProduct serverProduct = client.target(resourceURL)
				.request().accept(MediaType.APPLICATION_JSON)
				.get().readEntity(SoftwareProduct.class);
		
		logger.info(">>> Server-side Project: " + serverProduct);
		
		product = client.target(resourceURL)
				//.request().accept(MediaType.APPLICATION_XML).header("Content-Type", "application/xml")
				.request().accept(MediaType.APPLICATION_JSON)
				.put(Entity.entity(product, MediaType.APPLICATION_JSON))
				.readEntity(SoftwareProduct.class);
		
		logger.info(">>> Updated Product: " + product);
		return product;
	}
	
	public SoftwareProduct createProduct() {
		String resourceURL = serviceURL + "/new/"; //createNewProduct
		logger.info("DEBUG: ProjectsRESTServiceConsumer: CreateProduct ");

		SoftwareProduct product = ClientBuilder.newClient().target(resourceURL + 999)
					.request().accept(MediaType.APPLICATION_JSON)
					.post(null).readEntity(SoftwareProduct.class);
		logger.info(">>> Created/posted Product: " + product);
		
		return product;
	}	
}
