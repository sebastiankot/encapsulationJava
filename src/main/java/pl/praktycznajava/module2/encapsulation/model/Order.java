package pl.praktycznajava.module2.encapsulation.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "of")
public class Order {

    List<OrderItem> items;
    DeliveryType deliveryType;
    OrderStatus status;
    Address deliveryAddress;
    BigDecimal totalAmount;
    BigDecimal discountAmount;
    BigDecimal deliveryCost;

    public void completeOrder() {
        for (OrderItem item: this.items) {
            Product product = item.getProduct();
            product.getFromStock(item.getQuantity());
        }

        this.status = OrderStatus.COMPLETED;
    }

    public void changeAddressTo(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        this.deliveryCost = calculateDeliveryCost();
    }

    public BigDecimal calculateDeliveryCost() {
        double shippingCost = calculateShippingCost();
        if(!this.deliveryAddress.getCountry().equals("Polska")) {
            shippingCost += 80;
        }
        if(totalWeight() > 100) {
            shippingCost *= 1.1;
        }
        return BigDecimal.valueOf(shippingCost);
    }

    public void addNewItem(OrderItem newItem) {
        this.items.add(newItem);
        this.deliveryCost = calculateDeliveryCost();
        this.totalAmount.add(newItem.calculateItemAmount());
        this.discountAmount = calculateDiscount();
    }

    public void changeDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
        this.deliveryCost = calculateDeliveryCost();
    }

    private double totalWeight() {
        return this.items.stream()
                .mapToDouble(OrderItem::calculateTotalWeight)
                .sum();
    }

    private double deliveryTypeCost() {
        return this.deliveryType == DeliveryType.EXPRESS ? 30 : 15;
    }

    private double calculateShippingCost() {
        return totalWeight() * 0.5 + deliveryTypeCost();
    }

    private BigDecimal calculateDiscount() {
        BigDecimal discount = BigDecimal.ZERO;
        if (this.totalAmount.compareTo(BigDecimal.valueOf(500)) > 0) {
            discount = this.totalAmount.multiply(BigDecimal.valueOf(0.2));
        } else if (this.totalAmount.compareTo(BigDecimal.valueOf(50)) > 0) {
            discount = this.totalAmount.multiply(BigDecimal.valueOf(0.05));
        }
        return discount;
    }
}