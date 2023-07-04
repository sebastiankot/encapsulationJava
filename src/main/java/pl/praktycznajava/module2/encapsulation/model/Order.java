package pl.praktycznajava.module2.encapsulation.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
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

}