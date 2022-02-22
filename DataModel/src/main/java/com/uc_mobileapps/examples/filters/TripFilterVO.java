package com.uc_mobileapps.examples.filters;

import com.uc_mobileapps.examples.entities.Address;
import com.uc_mobileapps.examples.entities.Customer;

import java.time.LocalDateTime;

/**
 * Value object with filter options.
 */
public class TripFilterVO {

	/**
	 * Additional global textfilter.
	 */
	private String text;

	public TripFilterVO() {
		source = "";
		destination = "";
		address = new Address();
		address.setStreet("");
		address.setHouseNumber("");
		visitedCustomer = new Customer();
		visitedCustomer.setTitle("");
		visitedCustomer.setFirstName("");
		visitedCustomer.setLastName("");
	}

	/**
	 * Additional (global) filter text if implemented.
	 */
	public TripFilterVO withText(String filterText) {
		text = filterText;
		return this;
	}

	@Override
	public String toString() {
		return "TripFilterVO{" +
				"text='" + text + '\'' +
				", address=" + address +
				", destination='" + destination + '\'' +
				", distanceKm=" + distanceKm +
				", source='" + source + '\'' +
				", tripStart=" + tripStart +
				", visitedCustomer=" + visitedCustomer +
				'}';
	}

	// @seife automatically generated:
	private Address address;
	private String destination;
	private Float distanceKm;
	private String source;
	private LocalDateTime tripStart;
	private Customer visitedCustomer;

	/**
	 * Get filter value of address.
	 * @return address
	 */
	public Address getAddress() {
		return address;
	}
	
	/**
	 * Set filter value.
	 * @param address filter value to set
	 */
	public void setAddress(com.uc_mobileapps.examples.entities.Address address) {
		this.address = address;
	}

	/**
	 * Get filter value of destination.
	 * @return destination
	 */
	public String getDestination() {
		return destination;
	}
	
	/**
	 * Set filter value.
	 * @param destination filter value to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * Get filter value of distanceKm.
	 * @return distanceKm
	 */
	public Float getDistanceKm() {
		return distanceKm;
	}
	
	/**
	 * Set filter value.
	 * @param distanceKm filter value to set
	 */
	public void setDistanceKm(float distanceKm) {
		this.distanceKm = distanceKm;
	}

	/**
	 * Get filter value of source.
	 * @return source
	 */
	public String getSource() {
		return source;
	}
	
	/**
	 * Set filter value.
	 * @param source filter value to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * Get filter value of tripStart.
	 * @return tripStart
	 */
	public LocalDateTime getTripStart() {
		return tripStart;
	}
	
	/**
	 * Set filter value.
	 * @param tripStart filter value to set
	 */
	public void setTripStart(java.time.LocalDateTime tripStart) {
		this.tripStart = tripStart;
	}

	/**
	 * Get filter value of visitedCustomer.
	 * @return visitedCustomer
	 */
	public Customer getVisitedCustomer() {
		return visitedCustomer;
	}
	
	/**
	 * Set filter value.
	 * @param visitedCustomer filter value to set
	 */
	public void setVisitedCustomer(com.uc_mobileapps.examples.entities.Customer visitedCustomer) {
		this.visitedCustomer = visitedCustomer;
	}

	// @seife auto-code end
}
