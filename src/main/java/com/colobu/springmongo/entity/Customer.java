package com.colobu.springmongo.entity;

import static org.springframework.util.Assert.hasText;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Customer {
	private ObjectId id;
	private String firstname, lastname;
	private Address address;

	/**
	 * Creates a new {@link Customer} with the given firstname and lastname.
	 *
	 * @param firstname
	 *            must not be {@literal null} or empty.
	 * @param lastname
	 *            must not be {@literal null} or empty.
	 */
	public Customer(String firstname, String lastname) {
		hasText(firstname, "Firstname must not be null or empty!");
		hasText(lastname, "Lastname must not be null or empty!");
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (obj == null||!obj.getClass().equals(this.getClass()))return false;
		
		//don't use instaceof because if the Customer has subclass it doesn't works
//		if (!(obj instanceof Customer))return false;
		
		Customer other = (Customer) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		
		return true;
	}
	
	
}