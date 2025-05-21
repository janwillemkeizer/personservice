package nl.janwillemkeizer.personservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Company {
    @Column(name = "company_name")
    private String name;
    
    @Column(name = "company_catch_phrase")
    private String catchPhrase;
    
    @Column(name = "company_bs")
    private String bs;
    
    public Company() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatchPhrase() {
        return catchPhrase;
    }

    public void setCatchPhrase(String catchPhrase) {
        this.catchPhrase = catchPhrase;
    }

    public String getBs() {
        return bs;
    }

    public void setBs(String bs) {
        this.bs = bs;
    }
} 