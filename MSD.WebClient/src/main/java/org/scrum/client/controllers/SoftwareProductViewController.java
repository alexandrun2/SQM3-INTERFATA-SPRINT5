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
import org.scrum.client.dto.SoftwareProduct;
import org.scrum.client.services.SoftwareProductRESTServiceConsumer;



// projectsViewController
@Named 
@SessionScoped
public class SoftwareProductViewController implements Serializable{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SoftwareProductViewController.class.getName());
	// Data Model
	
	public void showMe() {
        addMessage("CORELARE CORECTA");
    }
	
	
	
	
	private List<SoftwareProduct> products = new ArrayList<SoftwareProduct>();
	//
	@Inject
	private SoftwareProductRESTServiceConsumer restService; 
	//
	public List<SoftwareProduct> getProducts() {
		return products;
	}
	
	/*public List<SoftwareProduct> getProducts() {
		return  restService.getProducts();
	}*/

	@PostConstruct
	private void init() {
		this.products = restService.getProducts();
		if (this.getProducts().size() >0)
			this.selectedProduct = this.getProducts().get(0);
	}
	//
	private SoftwareProduct selectedProduct;

	public SoftwareProduct getSelectedProduct() {
		return selectedProduct;
	}

	// Data Grid support
	public void setselectedProduct(SoftwareProduct selectedProduct) {
		this.selectedProduct = selectedProduct;
		System.out.println(">>> >>>> selectedProduct: " + this.selectedProduct);
	}
	
	// Filtering Support
	private List<SoftwareProduct> filteredProducts = new ArrayList<SoftwareProduct>();

	public List<SoftwareProduct> getfilteredProducts() {
		return filteredProducts;
	}

	public void setfilteredProducts(List<SoftwareProduct> filteredProducts) {
		logger.info("filteredProducts ::: ");
		if (filteredProducts != null)
			filteredProducts.stream().forEach(p -> System.out.println(p.getProduct_name()));
		else
			logger.info(">>> NULL ....");
		
		this.filteredProducts = filteredProducts;
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
		System.out.println(">>> >>>> onRowSelect ==> selectedProject is: " + this.selectedProduct);
	}
	
	public void saveSelectedProduct(ActionEvent actionEvent) {
        addMessage("Saved selected project => " + this.selectedProduct.getProduct_name());
        this.products = this.restService.addProduct(this.selectedProduct);
    }	
	
	public void addNewProduct(ActionEvent actionEvent) {
		this.selectedProduct = this.restService.createProduct();
		this.products = this.restService.getProducts();
		addMessage("NEW project => " + this.selectedProduct.getProduct_name());
	}
	
	public void deleteProduct(ActionEvent actionEvent) {
		addMessage("Deleted project => " + this.selectedProduct.getProduct_name());
		if (this.selectedProduct != null)
				this.restService.deleteProducts(this.selectedProduct);
		this.products = this.restService.getProducts();
		if (!this.products.isEmpty())
			this.selectedProduct = this.products.get(0);
		
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