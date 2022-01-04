package com.xtremax.rbtmqv2.service.base;

import com.xtremax.rbtmqv2.dto.Order;
import com.xtremax.rbtmqv2.error.NotFoundException;

public interface OrderService {
  Order findByOrderId(String id) throws NotFoundException;
  void saveOrder(Order order);
}
