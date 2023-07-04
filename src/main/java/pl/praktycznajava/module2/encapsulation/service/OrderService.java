package pl.praktycznajava.module2.encapsulation.service;

import pl.praktycznajava.module2.encapsulation.model.*;
import pl.praktycznajava.module2.encapsulation.repository.OrderRepository;

import java.math.BigDecimal;

public class OrderService {

    OrderRepository orderRepository;

    public void changeAddressTo(String orderId, Address deliveryAddress) {
        Order order = orderRepository.findBy(orderId);
        order.changeAddressTo(deliveryAddress);
        orderRepository.save(order);
    }

    public void changeDeliveryType(String orderId, DeliveryType deliveryType) {
        Order order = orderRepository.findBy(orderId);
        order.changeDeliveryType(deliveryType);
        orderRepository.save(order);
    }

    public void addProduct(String orderId, Product product, int quantity) {
        Order order = orderRepository.findBy(orderId);

        OrderItem newItem = OrderItem.of(product, quantity);
        order.addNewItem(newItem);

        orderRepository.save(order);
    }

    public void completeOrder(String orderId) {
        Order order = orderRepository.findBy(orderId);
        order.completeOrder();
        orderRepository.save(order);
    }
}
