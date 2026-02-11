package auca.ac.rw.restfullApiAssignment.controller.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import auca.ac.rw.restfullApiAssignment.model.product.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private List<Product> products = new ArrayList<>();

    public ProductController() {
        // Requirement: Initialize 10 products
        products.add(new Product(1L, "Laptop", "Electronics", 1200.0, 15));
        products.add(new Product(2L, "Smartphone", "Electronics", 800.0, 25));
        products.add(new Product(3L, "Desk Chair", "Furniture", 150.0, 10));
        products.add(new Product(4L, "Coffee Maker", "Appliances", 60.0, 8));
        products.add(new Product(5L, "Headphones", "Electronics", 120.0, 30));
        products.add(new Product(6L, "Monitor", "Electronics", 300.0, 12));
        products.add(new Product(7L, "Water Bottle", "Lifestyle", 20.0, 50));
        products.add(new Product(8L, "Keyboard", "Electronics", 45.0, 20));
        products.add(new Product(9L, "Backpack", "Lifestyle", 55.0, 18));
        products.add(new Product(10L, "Table Lamp", "Furniture", 35.0, 5));
    }

    @GetMapping("/api/products")
    public List<Product> getAll() { return products; }

    // GET /api/products/filter?minPrice={min}&maxPrice={max}
    @GetMapping("/api/products/filter")
    public List<Product> filterByPrice(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
        return products.stream()
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    // PUT /api/products/{productId}/price - Update only the price
    @PutMapping("/api/products/{productId}/price")
    public ResponseEntity<Product> updatePrice(@PathVariable Long productId, @RequestParam Double newPrice) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                product.setPrice(newPrice);
                return new ResponseEntity<>(product, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}