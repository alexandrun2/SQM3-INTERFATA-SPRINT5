package org.scrum.client.dto;

import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@XmlRootElement(name="product") 
@XmlAccessorType(XmlAccessType.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SoftwareProduct implements /*Comparable<SoftwareProduct>,*/ Serializable {
	//@Id /*@GeneratedValue @NotNull*/
	private Integer product_id;
	private String product_name;
	
	@XmlElement
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	
	@XmlElement
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public SoftwareProduct(Integer product_id, String product_name) {
		super();
		this.product_id = product_id;
		this.product_name = product_name;
	}
	public SoftwareProduct() {
		super();
		// TODO Auto-generated constructor stub
	}
	/*
	 * @Override
	public int compareTo(SoftwareProduct o) {
		// TODO Auto-generated method stub
		if (this.equals(o))
			return 0;
		return this.getProduct_name().compareTo(o.getProduct_name());
	}
	*/

	public static String BASE_URL = "http://localhost:8080/alexandru/data/products/";
	@XmlElement(name = "link")
    public AtomLink getLink() throws Exception {
		String restUrl = BASE_URL + this.getProduct_id();
        return new AtomLink(restUrl, "get-product");
    }	
	
	public void setLink(AtomLink link){}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((product_id == null) ? 0 : product_id.hashCode());
		result = prime * result + ((product_name == null) ? 0 : product_name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SoftwareProduct other = (SoftwareProduct) obj;
		if (product_id == null) {
			if (other.product_id != null)
				return false;
		} else if (!product_id.equals(other.product_id))
			return false;
		if (product_name == null) {
			if (other.product_name != null)
				return false;
		} else if (!product_name.equals(other.product_name))
			return false;
		return true;
	}
	
	
	
	
	
	

	
}
