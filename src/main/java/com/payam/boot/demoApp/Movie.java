package com.payam.boot.demoApp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Lob
    @Column(nullable = false)
    @Size(min = 1, max = 200)
    private String name;

    @Lob
    @Column(nullable = false)
    @Size(min = 1, max = 200)
    private String starring;

    @Lob
    @Column(nullable = false)
    @Size(min = 3, max = 20)
    private String language;


    @Size(min = 4, max = 4)
    private String productionYear;

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", starring='" + starring + '\'' +
                ", language='" + language + '\'' +
                ", productionYear='" + productionYear + '\'' +
                '}';
    }

    protected Movie() {
    }

    public Movie(String name, String starring, String language, String productionYear) {
        this.name = name;
        this.starring = starring;
        this.language = language;
        this.productionYear = productionYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStarring() {
        return starring;
    }

    public void setStarring(String starring) {
        this.starring = starring;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(String productionYear) {
        this.productionYear = productionYear;
    }
    public boolean same(Movie amovie) {
        boolean result ;
        result =  name.toUpperCase().equals(amovie.getName().toUpperCase()) &&
                starring.toUpperCase().equals(amovie.getStarring().toUpperCase()) &&
                language.toUpperCase().equals(amovie.getLanguage().toUpperCase()) &&
                productionYear.equals(amovie.getProductionYear());
        return result;
    }

}
