package entities;

import java.util.List;

public class ProductRecord {
    private List<Product> products;

    public ProductRecord(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public Product getProductById(int id) {
        for (var product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        System.out.println("Product not found");
        return null;
    }
}
