package no.hvl.dat152.i18n.model;

import jakarta.persistence.*;

@Entity
public class Description {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String langCode;
    private String text;
    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    public Description() {}

    public Description(Long id, String langCode, String text, Product product) {
        this.id = id;
        this.langCode = langCode;
        this.text = text;
        this.product = product;
    }

    public String getLangCode() {
        return langCode;
    }

    public Long getId() {
        return id;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
