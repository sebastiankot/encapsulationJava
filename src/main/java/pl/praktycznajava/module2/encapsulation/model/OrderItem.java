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
public class OrderItem {

    Product product;
    int quantity;

    public BigDecimal calculateItemAmount() {
        return this.product.getPrice().multiply(BigDecimal.valueOf(this.quantity));
    }

    public double calculateTotalWeight() {
        return this.product.getWeight() * this.quantity;
    }
}
