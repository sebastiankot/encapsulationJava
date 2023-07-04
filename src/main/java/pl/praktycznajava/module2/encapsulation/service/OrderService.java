package pl.praktycznajava.module2.encapsulation.service;

import pl.praktycznajava.module2.encapsulation.model.*;
import pl.praktycznajava.module2.encapsulation.repository.OrderRepository;

import java.math.BigDecimal;

public class OrderService {

    OrderRepository orderRepository;
    DeliveryCostCalculator deliveryCostCalculator;

    public void changeAddressTo(String orderId, Address deliveryAddress) {
        Order order = orderRepository.findBy(orderId);
        order.setDeliveryAddress(deliveryAddress);
        BigDecimal deliveryCost = deliveryCostCalculator.calculateDeliveryCost(order);
        order.setDeliveryCost(deliveryCost);
        orderRepository.save(order);
    }

    public void changeDeliveryType(String orderId, DeliveryType deliveryType) {
        Order order = orderRepository.findBy(orderId);
        order.setDeliveryType(deliveryType);
        BigDecimal deliveryCost = deliveryCostCalculator.calculateDeliveryCost(order);
        order.setDeliveryCost(deliveryCost);
        orderRepository.save(order);
    }

    public void addProduct(String orderId, Product product, int quantity) {
        Order order = orderRepository.findBy(orderId);

        OrderItem newItem = OrderItem.of(product, quantity);
        order.getItems().add(newItem);

        BigDecimal itemAmount = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        BigDecimal totalAmount = order.getTotalAmount().add(itemAmount);

        BigDecimal discount = calculateDiscount(totalAmount);
        BigDecimal deliveryCost = deliveryCostCalculator.calculateDeliveryCost(order);

        order.setDeliveryCost(deliveryCost);
        order.setTotalAmount(totalAmount);
        order.setDiscountAmount(discount);

        orderRepository.save(order);
    }

    public void completeOrder(String orderId) {
        Order order = orderRepository.findBy(orderId);
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            if (product.getStockQuantity() < quantity) {
                throw new InsufficientStockException(product, quantity);
            }
            product.setStockQuantity(product.getStockQuantity() - quantity);
        }
        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
    }

    public BigDecimal calculateDiscount(BigDecimal totalAmount) {
        BigDecimal discount = BigDecimal.ZERO;
        if (totalAmount.compareTo(BigDecimal.valueOf(500)) > 0) {
            discount = totalAmount.multiply(BigDecimal.valueOf(0.2));
        } else if (totalAmount.compareTo(BigDecimal.valueOf(50)) > 0) {
            discount = totalAmount.multiply(BigDecimal.valueOf(0.05));
        }
        return discount;
    }

}
