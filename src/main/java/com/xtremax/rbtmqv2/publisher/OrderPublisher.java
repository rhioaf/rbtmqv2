package com.xtremax.rbtmqv2.publisher;

import com.xtremax.rbtmqv2.config.MessagingConfig;
import com.xtremax.rbtmqv2.dto.Order;
import com.xtremax.rbtmqv2.dto.OrderStatus;
import com.xtremax.rbtmqv2.dto.Payment;
import com.xtremax.rbtmqv2.dto.Status;
import com.xtremax.rbtmqv2.error.NotFoundException;
import com.xtremax.rbtmqv2.service.base.OrderService;
import com.xtremax.rbtmqv2.service.base.OrderStatusService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderPublisher {

  private static final Logger logger = LoggerFactory.getLogger(OrderPublisher.class);
  private final RabbitTemplate rabbitTemplate;
  private final OrderService orderService;
  private final OrderStatusService orderStatusService;

  @PostMapping("/{restaurantName}")
  public String foodOrder(@RequestBody Order order, @PathVariable String restaurantName) {
    String orderId = UUID.randomUUID().toString();
    order.setOrderId(orderId);
    orderService.saveOrder(order);

    String orderStatusId = UUID.randomUUID().toString();
    OrderStatus orderStatus = new OrderStatus(orderStatusId, order, Status.PROCESS, "Order placed successfully in " + restaurantName);
    orderStatusService.saveOrderStatus(orderStatus);
    rabbitTemplate.convertAndSend(MessagingConfig.EXCHANGE_NAME, MessagingConfig.ROUTE_ORDER, orderStatus, message -> {
      message.getMessageProperties().setDelay(1000);
      return message;
    });
    logger.info("Order-" + order.getOrderId() + " is PROCESSING");
    return "Order placed!";
  }

  @PostMapping("/pay")
  public String paidOrder(@RequestBody Payment payment) {
    try {
      Order currentOrder = orderService.findByOrderId(payment.getOrderId());
      OrderStatus orderStatus = orderStatusService.findByOrder(currentOrder);
      if(orderStatus.isServed()) {
        Order orderData = orderStatus.getOrder();
        double totalPrice = (double) orderData.getQuantity() * orderData.getPrice();
        double change = 0;
        if(payment.getAmountPayment() < totalPrice) {
          return "Dude really?";
        }
        if(payment.getAmountPayment() > totalPrice) {
          change =  payment.getAmountPayment() - totalPrice;
        }
        rabbitTemplate.convertAndSend(MessagingConfig.EXCHANGE_NAME, MessagingConfig.ROUTE_CASHIER, orderStatus, message -> {
          message.getMessageProperties().setDelay(1000);
          return message;
        });
        if(change != 0) {
          return "Order Paid! - With Change: " + change + " IDR";
        } else {
          return "Order Paid!";
        }
      } else {
        return "Order Not Served Yet!";
      }
    } catch (NotFoundException e) {
      logger.error("Error occur: " + e.getMessage());
      return "Error: " + e.getMessage();
    }
  }
}
