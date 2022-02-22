package com.uc_mobileapps.examples.entities;


import com.weebmeister.seife.anno.SeifeClass;
import com.weebmeister.seife.anno.SeifeField;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@SeifeClass(generatorOptions = "filter.entityFilterVO=com.uc_mobileapps.examples.filters.TripFilterVO")
public class Trip {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotBlank
    @SeifeField
    private String source;

    @NotEmpty
    @SeifeField
    private String destination;

    @Size(min = 0, max = 5)
    @SeifeField(mandatory = true)
    private float distanceKm;

    /**
     * The new and the old java date and time api is supported.
     */
    @SeifeField
    private LocalDateTime tripStart = LocalDateTime.now(Clock.systemUTC());

    /**
     * Embedded address, this can also be a regular database reference
     */
    @SeifeField
    @Embedded
    private Address address;

    @SeifeField
    @ManyToOne
    @JoinColumn(foreignKey=@ForeignKey(name="fkCustomer"), nullable=true)
    private Customer visitedCustomer;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Trip trip = (Trip) o;

        return new EqualsBuilder().append(distanceKm, trip.distanceKm).append(id, trip.id).append(source, trip.source).append(destination, trip.destination).append(tripStart, trip.tripStart).append(address, trip.address).append(visitedCustomer, trip.visitedCustomer).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(source).append(destination).append(distanceKm).append(tripStart).append(address).append(visitedCustomer).toHashCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public float getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(float distanceKm) {
        this.distanceKm = distanceKm;
    }

    public LocalDateTime getTripStart() {
        return tripStart;
    }

    public void setTripStart(LocalDateTime tripStart) {
        this.tripStart = tripStart;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Customer getVisitedCustomer() {
        return visitedCustomer;
    }

    public void setVisitedCustomer(Customer visitedCustomer) {
        this.visitedCustomer = visitedCustomer;
    }

}