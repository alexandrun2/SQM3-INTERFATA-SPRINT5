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
import org.scrum.client.dto.Warranty;
import org.scrum.client.services.WarrantyRESTServiceConsumer;

// projectsViewController
@Named @SessionScoped
public class WarrantyViewController implements Serializable{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(WarrantyViewController.class.getName());
	// Data Model
	private List<Warranty> warranties = new ArrayList<Warranty>();
	//
	@Inject
	private WarrantyRESTServiceConsumer restService; 
	//
	public List<Warranty> getWarranties() {
		return warranties;
	}

	@PostConstruct
	private void init() {
		this.warranties = restService.getWarranties();
		if (this.getWarranties().size() >0)
			this.selectedWarranty = this.getWarranties().get(0);
	}
	//
	private Warranty selectedWarranty;

	public Warranty getSelectedWarranty() {
		return selectedWarranty;
	}

	// Data Grid support
	public void setSelectedWarranty(Warranty selectedWarranty) {
		this.selectedWarranty = selectedWarranty;
		System.out.println(">>> >>>> selectedProject: " + this.selectedWarranty);
	}
	
	// Filtering Support
	private List<Warranty> filteredWarranties = new ArrayList<Warranty>();

	public List<Warranty> getFilteredWarranties() {
		return filteredWarranties;
	}

	public void setFilteredWarranties(List<Warranty> filteredWarranties) {
		logger.info("filteredProjects ::: ");
		if (filteredWarranties != null)
			filteredWarranties.stream().forEach(p -> System.out.println(p.getDescription()));
		else
			logger.info(">>> NULL ....");
		
		this.filteredWarranties = filteredWarranties;
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
		System.out.println(">>> >>>> onRowSelect ==> selectedProject is: " + this.selectedWarranty);
	}
	
	public void saveSelectedWarranty(ActionEvent actionEvent) {
        addMessage("Saved selected project => " + this.selectedWarranty.getDescription());
        this.warranties = this.restService.addWarranty(this.selectedWarranty);
    }	
	
	public void addNewWarranty(ActionEvent actionEvent) {
		this.selectedWarranty = this.restService.createWarranty();
		this.warranties = this.restService.getWarranties();
		addMessage("NEW project => " + this.selectedWarranty.getDescription());
	}
	
	public void deleteWarranty(ActionEvent actionEvent) {
		addMessage("Deleted project => " + this.selectedWarranty.getDescription());
		if (this.selectedWarranty != null)
				this.restService.deleteWarranty(this.selectedWarranty);
		this.warranties = this.restService.getWarranties();
		if (!this.warranties.isEmpty())
			this.selectedWarranty = this.warranties.get(0);
		
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