package nl.janwillemkeizer.personservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Geo {
    @Column(name = "geo_lat")
    private String lat;
    
    @Column(name = "geo_lng")
    private String lng;
    
    public Geo() {
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
} 