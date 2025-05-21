package nl.janwillemkeizer.personservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class Address {
    @Column(name = "address_street")
    private String street;
    
    @Column(name = "address_suite")
    private String suite;
    
    @Column(name = "address_city")
    private String city;
    
    @Column(name = "address_zipcode")
    private String zipcode;
    
    @Embedded
    private Geo geo;
    
    public Address() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }
} 