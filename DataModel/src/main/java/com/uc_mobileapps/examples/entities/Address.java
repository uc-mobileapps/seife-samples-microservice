package com.uc_mobileapps.examples.entities;


import com.weebmeister.seife.anno.SeifeEmbeddable;
import com.weebmeister.seife.anno.SeifeField;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
@SeifeEmbeddable
public class Address {

    @Column(length = 50)
    @SeifeField
    private String street;

    @Column(length = 10)
    @SeifeField
    private String houseNumber;

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
}