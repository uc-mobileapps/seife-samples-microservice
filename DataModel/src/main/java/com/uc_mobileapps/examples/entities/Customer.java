package com.uc_mobileapps.examples.entities;


import com.weebmeister.seife.anno.SeifeBinding;
import com.weebmeister.seife.anno.SeifeClass;
import com.weebmeister.seife.anno.SeifeField;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@SeifeClass
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @SeifeField
    private String firstName;

    @SeifeField(mandatory = true)
    private String lastName;

    @Size(min = 0, max = 5)
    private String title;

    @SeifeField(dataOptions = SeifeBinding.TYPE_PASSWORD)
    private String password;

    @SeifeField
    @Embedded
    private Address address;

    @SeifeField
    private Boolean premium;

    @SeifeField
    private boolean active = true;

    public Customer() {}

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Boolean getPremium() {
        return premium;
    }

    public void setPremium(Boolean premium) {
        this.premium = premium;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}