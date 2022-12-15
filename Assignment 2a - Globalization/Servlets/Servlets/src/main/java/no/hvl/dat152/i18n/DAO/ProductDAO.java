package no.hvl.dat152.i18n.DAO;

import no.hvl.dat152.i18n.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public static List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product(1L, "Razer Gaming Keyboard", 56.23, "Razer-keyboard.jpeg");
        Product product2 = new Product(2L, "HyperX Gaming Keyboard", 61.15, "HyperX-keyboard.jpeg");
        products.add(product1);
        products.add(product2);

        return products;
    }

    public static Product getProductByID(Long ID) {
        switch (ID.intValue()) {
            case 1:
                return new Product(1L, "Razer Gaming Keyboard", 56.23, "Razer-keyboard.jpeg");
            case 2:
                return new Product(2L, "HyperX Gaming Keyboard", 61.15, "HyperX-keyboard.jpeg");
            default:
                return null;
        }
    }
}
