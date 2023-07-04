package pl.praktycznajava.module2.encapsulation.service;

import pl.praktycznajava.module2.encapsulation.model.DeliveryType;
import pl.praktycznajava.module2.encapsulation.model.Order;

import java.math.BigDecimal;

public class DeliveryCostCalculator {

    public BigDecimal calculateDeliveryCost(Order order) {
        double totalWeight = order.getItems().stream()
                .mapToDouble(item -> item.getProduct().getWeight() * item.getQuantity())
                .sum();

        double deliveryTypeCost = order.getDeliveryType() == DeliveryType.EXPRESS ? 30 : 15;
        double shippingCost = totalWeight * 0.5 + deliveryTypeCost;
        if (!order.getDeliveryAddress().getCountry().equals("Polska")) {
            shippingCost += 80;
        }
        if (totalWeight > 100) {
            shippingCost *= 1.1;
        }
        return BigDecimal.valueOf(shippingCost);
    }
}
