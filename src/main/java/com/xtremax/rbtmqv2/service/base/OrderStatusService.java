package com.xtremax.rbtmqv2.service.base;

import com.xtremax.rbtmqv2.dto.Order;
import com.xtremax.rbtmqv2.dto.OrderStatus;
import com.xtremax.rbtmqv2.error.NotFoundException;

public interface OrderStatusService {
  OrderStatus findById(String id) throws NotFoundException;
  OrderStatus findByOrder(Order order);
  void saveOrderStatus(OrderStatus orderStatus);
}
