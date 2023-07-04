package pl.praktycznajava.module2.encapsulation.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "of")
public class Product {

    String name;
    BigDecimal price;
    int stockQuantity;
    double weight;

    public void getFromStock(int quantity) {
        if (this.stockQuantity < quantity)
            throw new InsufficientStockException(this, quantity);

        this.stockQuantity -= quantity;
    }
}
