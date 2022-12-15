package no.hvl.dat152.i18n.model;

import jakarta.persistence.*;
import no.hvl.dat152.i18n.DAO.DescriptionDAO;

import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double priceInEuro;
    private String imageFile;
    @OneToMany
    @JoinColumn(name = "product_id")
    private final Collection<Description> descriptions = new ArrayList<>();

    public Product() {}

    public Product(Long id, String name, Double priceInEuro, String imageFile) {
        this.id = id;
        this.name = name;
        this.priceInEuro = priceInEuro;
        this.imageFile = imageFile;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPriceInEuro() {
        return priceInEuro;
    }

    public void setPriceInEuro(Double priceInEuro) {
        this.priceInEuro = priceInEuro;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public Collection<Description> getDescriptions() {
        return descriptions;
    }

    public Description getDescriptionByLanguage(String language) {
        for (Description d: DescriptionDAO.getDescriptionsByProductId(this.id)) {
            if(d.getLangCode().equals(language)) return d;
        }
        return null;
    }

    public Double getPriceByLanguage(String language) {
        if(language.equals("cs_CZ")) return priceInEuro*25;
        if(language.equals("en_US")) return priceInEuro*0.98;
        return priceInEuro;
    }
}
