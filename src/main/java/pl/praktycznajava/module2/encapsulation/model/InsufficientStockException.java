package pl.praktycznajava.module2.encapsulation.model;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(Product product, int quantity) {
        super(String.format("Required quantity: %s for the product: %s", quantity, product.getName()));
    }
}
