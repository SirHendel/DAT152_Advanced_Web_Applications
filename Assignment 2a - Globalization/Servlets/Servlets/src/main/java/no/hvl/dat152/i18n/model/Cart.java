package no.hvl.dat152.i18n.model;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Objects;

public class Cart {
    ArrayList<Product> product;
    double endSum;

    public Cart() {
        this.product = new ArrayList<>();
    }

    public void addToCart(Product product) {
        this.product.add(product);
    }

    public ArrayList<Product> showCart() {
        return product;
    }

    public Integer getAmountByProductID(Long id) {
        int counter = 0;
        for (Product p:product) {
            if(Objects.equals(p.getId(), id)) counter++;
        }
        return counter;
    }

    public void removeFromCart(Product product) {
        ListIterator<Product> iterator = this.product.listIterator();

        while(iterator.hasNext()) {
            Product product1 = iterator.next();
            if (product1.getName().equals(product.getName())) {
                this.product.remove(product);
                break;
            }
        }
    }
    public double getTotalAmount() {
        ListIterator<Product> iterator = this.product.listIterator();

        this.endSum = 0;
        while(iterator.hasNext()) {
            Product product1 = iterator.next();
            this.endSum = this.endSum + (product1.getPriceInEuro());
        }
        return this.endSum;
    }
    
    }

