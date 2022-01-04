package com.xtremax.rbtmqv2.consumer;

import com.xtremax.rbtmqv2.config.MessagingConfig;
import com.xtremax.rbtmqv2.dto.OrderStatus;
import com.xtremax.rbtmqv2.dto.Status;
import com.xtremax.rbtmqv2.error.NotFoundException;
import com.xtremax.rbtmqv2.service.base.OrderStatusService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChefConsumer {
    private static final Logger logger = LoggerFactory.getLogger(ChefConsumer.class);

    private final OrderStatusService orderStatusService;

  @RabbitListener(queues = MessagingConfig.QUEUE_ORDER)
  public void listenerOrder(OrderStatus orderStatus) {
    // new order coming up
    // processing the order
    logger.info("PROCESSING....");
    // fetch data from database
    try {
      OrderStatus currentOrderStatus = orderStatusService.findById(orderStatus.getId());
      currentOrderStatus.setOrderStatus(Status.SERVED);
      orderStatusService.saveOrderStatus(currentOrderStatus);
      logger.info("Order-" + orderStatus.getOrder().getOrderId() + " is " + currentOrderStatus.getOrderStatus().name());
    } catch (NotFoundException e) {
      logger.error("Error occur: " + e.getMessage());
    }
  }
}
